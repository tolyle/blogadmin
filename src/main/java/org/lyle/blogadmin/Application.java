package org.lyle.blogadmin;


import lombok.extern.slf4j.Slf4j;
import org.lyle.blogadmin.utils.network.IpUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.Arrays;


@Slf4j
@SpringBootApplication(scanBasePackages = {"org.lyle.blogadmin"})


public class Application {


	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Application.class);

		String ip =IpUtil.getHostIp();
		log.info("运行IP:{}",ip);
		if (IpUtil.sameNetwork(ip, "192.168.168.1", "255.55.255.0")||ip.equals("127.0.0.1")) {
			//开发环境
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
		} else {
			//生产环境
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "prod");
		}

		//app.addListeners(new ApplicationPidFileWriter("api.pid"));

		System.setProperty("server.connection-timeout","600000");
		System.setProperty("server.tomcat.max-threads","50");

		app.run(args);

		log.info("启动成功,运行环境为{}", System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));

	}

//	@Bean
//	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	public <E> BaseMapper<E> simpleBaseMapper(InjectionPoint ip) {
//		ResolvableType resolved = ResolvableType.forField(ip.getField());
//		Class<E> parameterClass = (Class<E>) resolved.getGeneric(0).resolve();
//		return Dal.with(parameterClass, sqlSession);
//	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("==========Let's inspect the beans provided by Spring Boot start==============:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				//System.out.println(beanName);
			}
			System.out.println("==========Let's inspect the beans provided by Spring Boot end==============:");

		};
	}


}

