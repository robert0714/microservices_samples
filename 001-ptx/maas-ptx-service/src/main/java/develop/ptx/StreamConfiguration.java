//package develop.ptx;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty; 
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//  
//import develop.ptx.config.ServiceConfig; 
// 
//
///***
// * 增加參數 -Dusestream=true 才會啟動<br/>
// * spring.cloud.stream.bindings.input.destination=hamipointTopic<br/>
// * spring.cloud.stream.bindings.input.content-type=application/json<br/>
// * spring.cloud.stream.kafka.binder.zkNodes=localhost<br/>
// * spring.cloud.stream.kafka.binder.brokers=localhost<br/>
// * 
// * **/
//@ConditionalOnProperty(name = "usestream", havingValue = "true")
//@Configuration
//@ConditionalOnMissingClass 
//public class StreamConfiguration {
//	private static final Logger logger = LoggerFactory.getLogger(StreamConfiguration.class);
//	
//    @Autowired
//    private ServiceConfig serviceConfig;
// 
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
//        jedisConnFactory.setHostName( serviceConfig.getRedisServer());
//        jedisConnFactory.setPort( serviceConfig.getRedisPort() );
//        return jedisConnFactory;
//    } 
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate ;
//    
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        
//        return template;
//    }
//}
