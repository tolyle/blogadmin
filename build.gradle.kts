import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.session.SessionHandler

plugins {
    java
    war
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.hidetake.ssh") version "2.10.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"

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
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-json")
    }


    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-devtools")

    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")

    implementation("cn.hutool:hutool-all:5.8.15")
    //    //Apache工具组件
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("commons-lang:commons-lang:2.6")
    implementation("org.apache.commons:commons-collections4:4.4")

    //    //文件上传工具类
    implementation("commons-fileupload:commons-fileupload:1.5")
    implementation("commons-io:commons-io:2.11.0")
    implementation("javax.persistence:persistence-api:1.0.2")

    //json工具
    implementation("com.alibaba.fastjson2:fastjson2:2.0.25")
    // druid连接池，SpringBoot没有管理版本，我们自己导入
    implementation("com.alibaba:druid:1.2.16")

    //集成log4jdbc 查看完整sql
    implementation("org.lazyluke:log4jdbc-remix:0.2.7")
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    // 照片元素据
    implementation("com.drewnoakes:metadata-extractor:2.18.0")
    //七牛云存储
    implementation("com.qiniu:qiniu-java-sdk:7.7.0")
    //fastjson
    implementation("com.alibaba.fastjson2:fastjson2:2.0.30")
    implementation("com.alibaba.fastjson2:fastjson2-extension-spring6:2.0.30")

    // MySQL驱动, // druid连接池
    providedRuntime("com.mysql:mysql-connector-j")
    implementation("com.alibaba:druid:1.2.16")

    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.0")
    testImplementation("org.projectlombok:lombok:1.18.26")

}


tasks {
    System.setProperty("spring.profiles.active", "asdfasdfasfasdfsd");

    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "org.lyle.Application"))
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("file.encoding", "UTF-8")
}


remotes {
    withGroovyBuilder {
        "create"("tencent") {
            setProperty("host", "118.126.88.185")
            setProperty("user", "root")
            //ssh-keygen -t rsa -m PEM
            setProperty("identity", file("${System.getProperty("user.home")}/.ssh/id_rsa"))
        }
    }
}


tasks.register("deployToTencent") {
    val tomcatPath = "/root/";

    doLast {
        ssh.run(delegateClosureOf<RunHandler> {
            session(remotes["tencent"], delegateClosureOf<SessionHandler> {
                println("上传jar........")
                put(hashMapOf("from" to "${project.projectDir}/build/libs/blogadmin-0.0.1-SNAPSHOT.jar", "into" to "${tomcatPath}/api.jar"))
                // execute("mv  ${tomcatPath}/deploy.war ${tomcatPath}/api.war");
                //execute("/root/t/bin/shutdown.sh");
                //execute("/root/t/bin/startup.sh");

            })
        })
    }
}

