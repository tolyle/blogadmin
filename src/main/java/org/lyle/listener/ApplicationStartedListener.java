package org.lyle.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedListener  implements ApplicationListener<ApplicationStartedEvent> {
	private static final Logger log = LoggerFactory.getLogger(ApplicationStartedListener.class);
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		log.info("进入到了监听器.................");
	}
}
