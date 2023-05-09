package org.lyle.service;

import com.baidu.fsg.uid.UidGenerator;
import jakarta.annotation.Resource;

public class BaseService {

    @Resource(name = "cachedUidGenerator")
    private UidGenerator uidGenerator;
    public  long getId(){
        return uidGenerator.getUID();
    }
}
