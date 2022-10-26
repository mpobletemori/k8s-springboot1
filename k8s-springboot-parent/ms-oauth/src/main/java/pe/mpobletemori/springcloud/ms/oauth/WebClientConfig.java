package pe.mpobletemori.springcloud.ms.oauth;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //Anotacion para configurar loadbalancer
    @LoadBalanced
    @Bean
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }

}
