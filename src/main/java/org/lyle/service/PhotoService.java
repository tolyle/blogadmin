package org.lyle.service;

import cn.hutool.core.io.FileUtil;
import jakarta.annotation.Resource;
import org.lyle.entity.Photo;
import org.lyle.exception.QiNiuException;
import org.lyle.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Service
@Transactional
public class PhotoService {
        Logger logger = LoggerFactory.getLogger(PhotoService.class);

        @Resource
    private BaseMapper<Photo> baseMapper;
    @Resource
    private IdService idService;

    public void savePhoto(MultipartFile multipartFile, String title, String tags){


        //上传文件
        try{
                String fileName = multipartFile.getOriginalFilename();
                String fileExt = FileUtil.extName(fileName);
                String newFileName = idService.getId()+"."+fileExt;
                long size = multipartFile.getSize()/1024;
                logger.info("save photo,file name:{},file size:{}",newFileName,size);
                FileUtil.writeFromStream(multipartFile.getInputStream(),"d:\\"+newFileName);

                Photo photo = new Photo();
photo.setId(idService.getId());
photo.setCreateTime(new Date());
photo.setTitle(title);
photo.setTags(tags);
                photo.setSrcSize(size);


                System.out.println("==============="+photo);

            baseMapper.insert(photo);

        }catch (Exception e){
            throw new QiNiuException(e);
        }

        //
    }
}
