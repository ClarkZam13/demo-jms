package com.example.demo.configuracion;

import com.example.demo.dao.Dao;
import com.example.demo.dao.venta.VentaDao;
import com.example.demo.dto.Usuario;
import com.example.demo.dto.Venta;
import com.mockrunner.jms.ConfigurationManager;
import com.mockrunner.jms.DestinationManager;
import com.mockrunner.mock.jms.MockQueue;
import com.mockrunner.mock.jms.MockQueueConnectionFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfigGeneral {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
    webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/demo");
    }


    @Bean
    DestinationManager obtenerDestinationManager(){
        return new DestinationManager();
    }
    @Bean
    MockQueueConnectionFactory obtenerConectionFactory(DestinationManager destinationManager) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        return new MockQueueConnectionFactory(destinationManager,configurationManager);
    }

    @Bean
    MockQueue obtenerQueue(DestinationManager destinationManager){
        return destinationManager.createQueue("mock");
    }

    @Bean
    JmsTemplate obtenerJmsTemplate(MockQueueConnectionFactory mockQueueConnectionFactory){
        JmsTemplate t = new JmsTemplate(mockQueueConnectionFactory);
        t.setReceiveTimeout(10000L);
        return t;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
