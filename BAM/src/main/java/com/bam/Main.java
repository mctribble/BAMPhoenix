package com.bam;

import com.bam.bean.BamUser;
import com.bam.service.BamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Main {

    @Autowired
    BamUserService bus;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public CommandLineRunner run() {
        return args -> {
            try {
                BamUser u = bus.findUserById(1);
                System.out.println(u.getEmail());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
