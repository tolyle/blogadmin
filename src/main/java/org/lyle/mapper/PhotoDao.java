package org.lyle.mapper;


import org.lyle.entity.Photo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Repository
public class PhotoDao {

    public Page<Photo> pageRows(Long currentPage, Long pageSize, String sql) {
        Long count = count(sql);
        Page<Photo> page = new Page<>();
        page.setPageSize(20L);
        page.setRecordsTotal(count);
        Long startRow = currentPage * page.getPageSize();
        page.setStartRow(startRow);
        sql = sql + " limit " + page.getStartRow() + "," + page.getPageSize();

        List<Photo> photoList = Dal.with(Photo.class).sqlQuery(sql, null, Photo.class);
        page.setData(photoList);

        return page;
    }


    public Long count(String sql) {
        sql = sql.toLowerCase();
        String a = sql.substring(0, sql.indexOf("from"));
        StringBuilder sb = new StringBuilder();
        String[] arr = a.split(",");
        if (a.contains(",")) {
            sb.append(arr[0]);
            sb.append(" ");
            sb.append(sql.substring(sql.indexOf("from")));
        } else {
            sb.append(sql);
        }

        sql = "select count(*) as cnt  from (" + sb.toString() + ") t";
        System.out.println(sql);

        long cnt = Long.parseLong(Dal.with(Map.class).sqlQuery(sql, null, Map.class).get(0).get("cnt").toString());
        System.out.println("cnt:" + cnt);

        return cnt;

    }
}
