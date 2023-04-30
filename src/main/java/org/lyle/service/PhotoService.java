package org.lyle.service;

import jakarta.annotation.Resource;
import org.lyle.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    @Resource
    private PhotoMapper photoMapper;



    public String login(String username){
        return photoMapper.selectPassword(username);
    }
}
