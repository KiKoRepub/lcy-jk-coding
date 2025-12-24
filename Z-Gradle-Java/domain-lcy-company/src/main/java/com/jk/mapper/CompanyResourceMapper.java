package com.jk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.entity.CompanyResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyResourceMapper extends BaseMapper<CompanyResource> {
    Long deleteResource(Long id);

    Integer deleteBatchResource(List<Long> ids);
}
