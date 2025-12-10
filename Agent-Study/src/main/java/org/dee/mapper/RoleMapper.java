package org.dee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dee.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("""
            SELECT * FROM role 
            WHERE role_name = #{name} LIMIT 1
            """)
    Role findByName(@Param("name") String name);

    @Select("""
            SELECT r.role_name 
            FROM role r JOIN user_role ur
            ON r.id = ur.role_id 
            WHERE ur.user_id = #{userId}
            """)
    List<String> selectRoleNamesByUserId(@Param("userId") Long userId);
}
