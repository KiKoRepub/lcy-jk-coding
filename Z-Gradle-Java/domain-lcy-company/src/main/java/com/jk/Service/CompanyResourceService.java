package com.jk.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jk.dto.CancelUploadDTO;
import com.jk.dto.ResourcePageDTO;
import com.jk.dto.ResourceSaveDTO;
import com.jk.dto.ResourceUploadTextDTO;
import com.jk.entity.CompanyResource;
import com.jk.vo.AttachCompanyPageVo;
import com.jk.vo.ResourcePageVo;
import com.jk.vo.ResourceSelectTypeVo;
import com.jk.vo.ResourceUploadVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyResourceService extends IService<CompanyResource> {
    List<AttachCompanyPageVo> attachCompanyList();
    Page<ResourcePageVo> getPageList(ResourcePageDTO dto);

    ResourceUploadVo uploadFile(MultipartFile file,String type,String content) throws IOException;
    List<ResourceUploadVo> batchUploadFile(MultipartFile[] files) throws IOException;
    ResourceUploadVo cancelUpload(CancelUploadDTO dto);
    Long saveOrUpdateResource(ResourceSaveDTO dto);


    Long deleteResource(Long id);

    Integer batchDeleteResource(List<Long> ids);


    ResourceSelectTypeVo getSelectType();



}
