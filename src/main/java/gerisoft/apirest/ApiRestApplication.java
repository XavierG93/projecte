package gerisoft.apirest;

import gerisoft.apirest.utils.MyDatabaseSeeder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiRestApplication {

    @Autowired
    private MyDatabaseSeeder myDatabaseSeeder;
    
    public static void main(String[] args) {
        SpringApplication.run(ApiRestApplication.class, args);
    }
    
    @Bean
    InitializingBean seedDatabase() {
        return () -> {
            myDatabaseSeeder.seedDatabase();
        };
    }

}
