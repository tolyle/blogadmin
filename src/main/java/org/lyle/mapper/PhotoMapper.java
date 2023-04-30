package org.lyle.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lyle.entity.Photo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface PhotoMapper {
    //根据username查询password
    String selectPassword(String username);

    @Select("select * from photo where id = #{id}")
    Photo getPhoto(@Param("id") long id);

}
