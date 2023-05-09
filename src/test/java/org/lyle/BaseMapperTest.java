package org.lyle;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lyle.entity.Photo;
import org.lyle.mapper.BaseMapper;
import org.lyle.mapper.Dal;
import org.lyle.mapper.PhotoDao;
import org.lyle.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseMapperTest {
    @Autowired
    private BaseMapper<Photo> baseMapper;
    @Autowired
    private PhotoMapper photoMapper;

    private Dal dal;


    @Autowired
    private PhotoDao photoDao;

    @Test
    public void page() {
        Photo photo = new Photo();
        photo.setTitle("这是");
        // System.out.println(baseMapper.select(photo));

        List<String> l = new ArrayList<String>();
        l.add("aa");
        Serializable[] s = {2, 2};
        String sql = "title like ('这%')";
        // baseMapper.selectByColumn("title",s);


        //baseMapper.addWhere(sql);

        //baseMapper.addWherePage(sql,1,10);


        //Dal.with(Photo.class).sqlQuery("select * from photo limit 0,10",null, Map.class);


//        List<Photo> photoList=photoMapper.findPhoto("这",0,2);
//
//        for(Photo p:photoList){
//            System.out.println(p.getTitle());
//        }

        String sqls = "select * from photo where title like '%这%' ";
        photoDao.pageRows(1L, 10L, sqls);
    }


}
