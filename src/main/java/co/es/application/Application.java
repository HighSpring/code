package co.es.application;

import co.es.configuration.ElasticsearchConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hungnguyen on 12/28/14.
 */
@Configuration
@ComponentScan(basePackages = "co.es")
@EnableAutoConfiguration(exclude = {ElasticsearchConfiguration.class})
public class Application {
    public static void main(String args[]){
        SpringApplication.run(Application.class);
    }
}
