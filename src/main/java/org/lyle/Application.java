package org.lyle;


import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.lyle.mapper.BaseMapper;
import org.lyle.mapper.Dal;
import org.lyle.utils.network.IpUtil;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.AbstractEnvironment;

import java.util.Arrays;


@SpringBootApplication(scanBasePackages = {"org.lyle", "com.baidu"})
public class Application {


	@Resource
	SqlSession sqlSession;

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Application.class);

		if (IpUtil.sameNetwork(IpUtil.getHostIp(), "192.168.168.1", "255.55.255.0")) {
			//开发环境
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
		} else {
			//生产环境
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "prod");
		}

		app.addListeners(new ApplicationPidFileWriter("api.pid"));
		app.run(args);


	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public <E> BaseMapper<E> simpleBaseMapper(InjectionPoint ip) {
		ResolvableType resolved = ResolvableType.forField(ip.getField());
		Class<E> parameterClass = (Class<E>) resolved.getGeneric(0).resolve();
		return Dal.with(parameterClass, sqlSession);
	}

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

