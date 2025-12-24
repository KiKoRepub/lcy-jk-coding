package com.jk.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.Service.CompanyResourceService;

import com.jk.dto.CancelUploadDTO;
import com.jk.dto.ResourcePageDTO;
import com.jk.dto.ResourceSaveDTO;

import com.jk.entity.ResultBean;
import com.jk.vo.AttachCompanyPageVo;
import com.jk.vo.ResourcePageVo;
import com.jk.vo.ResourceSelectTypeVo;
import com.jk.vo.ResourceUploadVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private CompanyResourceService companyResourceService;


    // 列表页面接口
    @PostMapping("/list")
    @Operation(summary = "资源分页列表",  description= "资源分页列表")
    public ResultBean<Page<ResourcePageVo>> list(@Valid @RequestBody ResourcePageDTO dto) {
        return new ResultBean<>(companyResourceService.getPageList(dto));
    }

    @PostMapping("/update")
    @Operation(summary = "资源更新", description = "资源更新")
    public ResultBean<Long> update(@Valid @RequestBody ResourceSaveDTO dto) {
        return new ResultBean<>(companyResourceService.saveOrUpdateResource(dto));
    }

    @PostMapping("/delete")
    @Operation(summary = "资源删除", description = "资源删除")
    public ResultBean<Long> delete(@RequestBody Long id) {
        return new ResultBean<>(companyResourceService.deleteResource(id));
    }

    @PostMapping("/batch/delete")
    @Operation(summary = "资源批量删除", description = "资源批量删除")
    public ResultBean<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return new ResultBean<>(companyResourceService.batchDeleteResource(ids));
    }

    // 新增页面接口
    @GetMapping("/selectType")
    @Operation(summary = "获取资源类型列表", description = "获取资源类型列表")
    public ResultBean<ResourceSelectTypeVo> selectType() {
        return new ResultBean<>(companyResourceService.getSelectType());
    }

    @PostMapping("/attachCompanyList")
    @Operation(summary = "获取附属企业列表", description = "获取附属企业列表")
    public ResultBean<List<AttachCompanyPageVo>> attachCompanyList() {
        return new ResultBean<>(companyResourceService.attachCompanyList());
    }

    @PostMapping(value = "/upload/file", headers = "content-type=multipart/form-data")
    @Operation(summary = "资源上传", description = "资源上传")
    @Parameter(name = "type", description = "上传类型：sheet--表格, picture--图片, text--文本")
    public ResultBean<ResourceUploadVo> upload(@RequestParam("file") MultipartFile file,
                                               @RequestParam("type") String type,
                                               @RequestParam(value = "content", required = false) String content)
            throws IOException {
        return new ResultBean<>(companyResourceService.uploadFile(file, type,content));
    }

    @PostMapping(value = "/batch/upload/file" ,headers = "content-type=multipart/form-data")
    @Operation(summary = "资源批量上传", description = "资源批量上传")
    public ResultBean<List<ResourceUploadVo>> batchUpload(@RequestParam("files") MultipartFile[] files)
            throws IOException {
        return new ResultBean<>(companyResourceService.batchUploadFile(files));
    }

    @PostMapping("/cancelUpload")
    @Operation(summary = "取消上传资源", description = "取消上传资源")
    public ResultBean<ResourceUploadVo> cancelUpload(@RequestBody CancelUploadDTO dto) {
        return new ResultBean<>(companyResourceService.cancelUpload(dto));
    }


    @PostMapping("/save")
    @Operation(summary = "资源保存", description = "资源保存")
    public ResultBean<Long> save(@Valid @RequestBody ResourceSaveDTO dto) {
        return new ResultBean<>(companyResourceService.saveOrUpdateResource(dto));
    }


}
