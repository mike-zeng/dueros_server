package cn.hephaestus.smartmeetingroom.dueros_server;

import cn.hephaestus.smartmeetingroom.dueros_server.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@MapperScan("cn.hephaestus.smartmeetingroom.dueros_server.mapper")
public class DuerosServerApplication {

    public static void main(String[] args) {
        ApplicationContext app=SpringApplication.run(DuerosServerApplication.class, args);
        SpringUtil.setApplicationContext(app);
    }

}

