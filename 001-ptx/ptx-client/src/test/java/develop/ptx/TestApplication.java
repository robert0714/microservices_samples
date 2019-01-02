package develop.ptx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

//import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;

import develop.ptx.bike.services.PtxBikeRestTemplateClient;
import develop.ptx.bike.services.PtxBikeRestTemplateClientStub; 
 

@SpringBootApplication 
@RibbonClient(name = "ptx-service"  )
public class TestApplication {

	private static final Logger logger = LoggerFactory.getLogger(TestApplication.class);

	
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	public PtxBikeRestTemplateClient getPtxBikeRestTemplateClientStub() {
		return new PtxBikeRestTemplateClientStub();
	}
	@LoadBalanced
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				.setConnectTimeout(1000)
				.setReadTimeout(1000)
				.build();
	}
//	
//	 @Bean
//     public HystrixCommandAspect hystrixCommandAspect() {
//         return new HystrixCommandAspect();
//     }
}