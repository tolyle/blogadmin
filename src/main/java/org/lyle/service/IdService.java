package org.lyle.service;

import com.baidu.fsg.uid.UidGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class IdService {
	@Resource(name = "cachedUidGenerator")
	private UidGenerator uidGenerator;
	public  long getId(){
		return uidGenerator.getUID();
	}

}
