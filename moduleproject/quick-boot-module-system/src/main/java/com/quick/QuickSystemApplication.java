package com.quick;

import com.quick.common.util.MyConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

/**
 * 单体启动类（采用此类启动为单体模式）
 * 特别提醒:
 * 1.需要集成mongodb请删除 exclude={MongoAutoConfiguration.class}
 * 2.切换微服务 勾选profile的SpringCloud，这个类就无法启动，启动会报错
 */
@Slf4j
@SpringBootApplication
public class QuickSystemApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(QuickSystemApplication.class, args);
        Iterator<String> beanNamesIterator = applicationContext.getBeanFactory().getBeanNamesIterator();
        while (beanNamesIterator.hasNext()){
            System.out.println(beanNamesIterator.next());
        }
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        String path = MyConvertUtils.getString(environment.getProperty("server.servlet.context-path"));
        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n\t" +
                "Druid监控: \thttp://" + ip + ":" + port + path + "/druid\n" +
                "----------------------------------------------------------");

    }

}
