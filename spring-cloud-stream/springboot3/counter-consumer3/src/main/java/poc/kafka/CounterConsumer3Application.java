package poc.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@SpringBootApplication
public class CounterConsumer3Application {

    public static void main(String[] args) {
        SpringApplication.run(CounterConsumer3Application.class, args);
    }

    @Value("${api.host:localhost}")
    private String apiHost;

    @Value("${api.port:8080}")
    private String apiPort;

    @Value("${api.failrate:50}")
    private int failrate;

    private WebClient webclient;

    @PostConstruct
    public void postConstruct() {
        log.info(String.format("http://%s:%s/api",apiHost,apiPort));
        this.webclient = WebClient.builder()
                .baseUrl(String.format("http://%s:%s/api",apiHost,apiPort))
                .build();
    }

    @Bean
    public Consumer<Long> consume2() {
        return (l) -> {
            log.info("Received counter [{}]", l);
            if (Math.random()>0.5) {
                log.info("Raise exception when processing counter [{}]", l);
                throw new RuntimeException("Consumer raised exception!!");
            }
        };
    }

    @Bean
    public Consumer<Message<Long>> consume() {
        return (msg) -> {
            MessageHeaders headers = msg.getHeaders();
            String topic = headers.get(KafkaHeaders.RECEIVED_TOPIC,String.class);
            AtomicInteger attempt = headers.get("deliveryAttempt", AtomicInteger.class);
            long l = msg.getPayload();
            log.info("Received counter [{}] from topic [{}] attempt [{}]", l, topic, attempt);

            String result = webclient.post()
                    .uri(builder -> builder//.host(apiHost).port(apiPort)
                            .path("/create")
                            .queryParam("counter", msg.getPayload())
                            .queryParam("failrate", failrate)
                            .build()
                    )
                    .accept(MediaType.TEXT_PLAIN)
                    .retrieve()
                    .bodyToMono(String.class)
                    //.log()
                    .block();

            log.info("call API success [{}]",result);
//            if (Math.random()>0.5) {
//                log.info("Raise exception when processing counter [{}]", l);
//                throw new RuntimeException("Consumer raised exception!!");
//            }
        };
    }
}
