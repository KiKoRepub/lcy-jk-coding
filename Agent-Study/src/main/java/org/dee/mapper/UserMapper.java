package org.dee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dee.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM `user` WHERE username = #{username} LIMIT 1")
    User findByUsername(@Param("username") String username);
}
