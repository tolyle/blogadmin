plugins {
    java
    war
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "org.lyle"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenCentral()
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-devtools")

    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")

    implementation("cn.hutool:hutool-all:5.8.15")
    //    //Apache工具组件
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-collections4:4.4")

    //    //文件上传工具类
    implementation("commons-fileupload:commons-fileupload:1.5")
    implementation("commons-io:commons-io:2.11.0")

    //json工具
    implementation("com.alibaba.fastjson2:fastjson2:2.0.25")
    // druid连接池，SpringBoot没有管理版本，我们自己导入
    implementation("com.alibaba:druid:1.2.16")

    // MySQL驱动, // druid连接池
    providedRuntime("com.mysql:mysql-connector-j")
    implementation("com.alibaba:druid:1.2.16")


    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
