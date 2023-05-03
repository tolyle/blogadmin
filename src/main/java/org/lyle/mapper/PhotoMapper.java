package org.lyle.mapper;

import org.apache.ibatis.annotations.*;
import org.lyle.entity.Photo;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface PhotoMapper {
    //根据username查询password
    String selectPassword(String username);

    @Select("select * from photo where id = #{id}")
    Photo getPhoto(@Param("id") long id);

    @Insert("insert into photo (id, name) values(null, #{name})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    long  insertPhoto(Photo photo);

    long  savePhoto(Photo photo);

}
