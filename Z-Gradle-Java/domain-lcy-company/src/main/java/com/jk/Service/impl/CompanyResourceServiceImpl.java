package com.jk.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.Service.CompanyResourceService;
import com.jk.Service.FileUploadService;
import com.jk.dto.CancelUploadDTO;
import com.jk.dto.ResourcePageDTO;
import com.jk.dto.ResourceSaveDTO;

import com.jk.entity.CompanyResource;
import com.jk.enums.IndustryTypeEnum;
import com.jk.enums.MinioBucketEnum;
import com.jk.enums.ProjectTypeEnum;
import com.jk.enums.ResourceTypeEnum;
import com.jk.mapper.CompanyResourceMapper;

import com.jk.vo.AttachCompanyPageVo;
import com.jk.vo.ResourcePageVo;
import com.jk.vo.ResourceSelectTypeVo;
import com.jk.vo.ResourceUploadVo;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


@Service
public class CompanyResourceServiceImpl extends ServiceImpl<CompanyResourceMapper, CompanyResource> implements CompanyResourceService {


    @Resource
    private FileUploadService upLoadBizService;

    @Resource
    private CompanyResourceMapper companyResourceMapper;

    @Override
    public List<AttachCompanyPageVo> attachCompanyList() {
        //TODO 关联企业列表 进行查询
        return null;
    }

    @Override
    public Page<ResourcePageVo> getPageList(ResourcePageDTO dto) {

        Integer pageNum = dto.getPageNum();
        Integer pageSize = dto.getPageSize();

        QueryWrapper<CompanyResource> pageQueryWrapper = getPageQueryWrapper(dto);

        Page<CompanyResource> page = page(new Page<>(pageNum, pageSize), pageQueryWrapper);

        List<ResourcePageVo> voList = page.getRecords().stream()
                .map(this::toPageVo)
                .collect(Collectors.toList());
        Page<ResourcePageVo> resultPage = new Page<>(pageNum, pageSize, page.getTotal());

        resultPage.setRecords(voList);

        return resultPage;
    }



    @Override
    public ResourceUploadVo uploadFile(MultipartFile file, String type,String content) throws IOException {

            ResourceTypeEnum typeEnum = ResourceTypeEnum.getType(type);
            // 根据不同的类型上传到不同的桶
            if (typeEnum.bucketEnum != null) {
                return handleFileUpload(file, typeEnum);
            }
            // 文本类型，直接返回内容 (path 存储 content)
            return new ResourceUploadVo(content);
    }

    @Override
    public List<ResourceUploadVo> batchUploadFile(MultipartFile[] files) throws IOException {
        List<ResourceUploadVo> uploadVos = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            // substring 的范围 : [ )
            String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);

            ResourceTypeEnum typeEnum = ResourceTypeEnum.getTypeBySuffix(suffix);

            if (!ResourceTypeEnum.TEXT.equals(typeEnum)){
                uploadVos.add(handleFileUpload(file, typeEnum)) ;
            }else {
                // 读取文本内容
                StringBuilder contentBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(),
                        StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contentBuilder.append(line).append("\n");
                    }
                }
                // 去掉最后一个多余的换行符
                if (contentBuilder.length() > 0) {
                    contentBuilder.setLength(contentBuilder.length() - 1);
                }
                uploadVos.add(new ResourceUploadVo(contentBuilder.toString()));
            }

        }
        return uploadVos;
    }

    @Override
    public ResourceUploadVo cancelUpload(CancelUploadDTO dto) {


        MinioBucketEnum bucketEnum = ResourceTypeEnum.getType(dto.getType()).bucketEnum;

        upLoadBizService.removeObject(dto.getPath(), bucketEnum.value);

        return new ResourceUploadVo(dto.getPath());
    }

    @Override
    public Long saveOrUpdateResource(ResourceSaveDTO dto) {
        CompanyResource resource = new CompanyResource();
        BeanUtil.copyProperties(dto, resource);

        if (dto.getId() != null && dto.getId() != 0) {
            // 更新逻辑
            LambdaQueryWrapper<CompanyResource> queryWrapper = new LambdaQueryWrapper<CompanyResource>()
                    .eq(CompanyResource::getId, dto.getId())
                    .eq(CompanyResource::getDeleted, 0);

            CompanyResource companyResource = getOne(queryWrapper);
            if (companyResource == null){
                throw new IllegalArgumentException("记录不存在或已被删除，无法更新");
            }

            updateById(resource);
        } else {
            // 新增逻辑
            save(resource);
        }

        return resource.getId();
    }

    @Override
    public Long deleteResource(Long id) {
        companyResourceMapper.deleteResource(id);
        return id;
    }

    @Override
    public Integer batchDeleteResource(List<Long> ids) {
        return companyResourceMapper.deleteBatchResource(ids);
    }

    @Override
    public ResourceSelectTypeVo getSelectType() {

        List<String> fileTypeList = new ArrayList<>();
        for (ResourceTypeEnum value : ResourceTypeEnum.values()) {
            fileTypeList.add(value.name());
        }

        List<String> industryTypeList = new ArrayList<>();
        for (IndustryTypeEnum value : IndustryTypeEnum.values()) {
            industryTypeList.add(value.description);
        }

        List<String> projectTypeList = new ArrayList<>();
        for (ProjectTypeEnum value : ProjectTypeEnum.values()) {
            projectTypeList.add(value.description);
        }

        return new ResourceSelectTypeVo(fileTypeList,industryTypeList,projectTypeList);

    }


    private  ResourcePageVo toPageVo(CompanyResource companyResource) {
        ResourcePageVo vo = new ResourcePageVo();
        BeanUtil.copyProperties(companyResource, vo);
        return vo;
    }

    private QueryWrapper<CompanyResource> getPageQueryWrapper(ResourcePageDTO dto) {
        QueryWrapper<CompanyResource> queryWrapper = new QueryWrapper<CompanyResource>()
                .eq("deleted", 0)
                .orderByDesc("create_time");

        if (!StringUtils.isEmpty(dto.getCompanyName())){
            queryWrapper.like("company_name", dto.getCompanyName());
        }

        return queryWrapper;
    }



    private ResourceUploadVo handleFileUpload(MultipartFile file, ResourceTypeEnum typeEnum) throws IOException {
        int ran2 = (int) (Math.random() * (100 - 1) + 1);

        String fileName = String.valueOf(System.currentTimeMillis()) + ran2;
        String bucketName = typeEnum.bucketEnum.value;
        String type = typeEnum.name();

        final String originalFilename = file.getOriginalFilename();


        String toUploadName = fileName +
                originalFilename.substring(originalFilename.lastIndexOf("."));


        final String uploadFileName = upLoadBizService.uploadFile(
                toUploadName, type, file.getInputStream(), bucketName);


        return new ResourceUploadVo(uploadFileName);
    }

    public static void main(String[] args) {
        Arrays.stream(ResourceTypeEnum.values()).forEach(e -> {
            System.out.println(e.name() +
                    " -> " + Arrays.toString(e.suffixes) +
                    " -> " + e.bucketEnum);
        });
    }
}
