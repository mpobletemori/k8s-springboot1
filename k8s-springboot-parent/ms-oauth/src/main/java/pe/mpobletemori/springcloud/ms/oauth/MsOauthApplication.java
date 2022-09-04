package pe.mpobletemori.springcloud.ms.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOauthApplication.class, args);
	}

}
