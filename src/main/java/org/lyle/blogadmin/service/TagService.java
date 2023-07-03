package org.lyle.blogadmin.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lyle.blogadmin.entity.Tag;
import org.lyle.blogadmin.mapper.TagMapper;
import org.springframework.stereotype.Service;

@Service
public class TagService extends ServiceImpl<TagMapper, Tag> {
}
