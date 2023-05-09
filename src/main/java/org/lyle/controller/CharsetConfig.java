package org.lyle.controller;


import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author : dylanz
 * @since : 11/15/2020
 */
@Configuration
public class CharsetConfig extends WebMvcConfigurationSupport {
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		//自定义配置...
		FastJsonConfig config = new FastJsonConfig();

		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(StandardCharsets.UTF_8);
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));

		config.setWriterFeatures(JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteLongAsString);


		converters.add(0, converter);
	}

}

