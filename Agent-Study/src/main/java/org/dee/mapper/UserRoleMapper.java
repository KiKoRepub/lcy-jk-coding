package org.dee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dee.entity.UserRole;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("SELECT * FROM user_role WHERE user_id = #{userId}")
    List<UserRole> selectByUserId(@Param("userId") Long userId);
}
