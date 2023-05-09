package org.lyle.mapper;

import org.apache.ibatis.annotations.*;
import org.lyle.entity.Photo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Select({"<script>",
            "select",
            " * ",
            "from photo",
            "where 1=1  ",
            "<if test='!searchKey'>and title like '%${searchKey}%' </if>  ",
            "order by id desc  ",
            "LIMIT #{startRow},#{endRow}",
            "</script> "})
    List<Photo> findPhoto(String searchKey,Integer startRow,Integer endRow);

    Long findPhotoCount();
}
