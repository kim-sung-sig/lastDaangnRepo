package com.demo.daangn.app.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "daangn.chat.queue";

    public static final String EXCHANGE_DIRECT_NAME = "daangn.chat.direct.exchange";
    public static final String EXCHANGE_TOPIC_NAME = "daangn.chat.topic.exchange";
    public static final String EXCHANGE_FANOUT_NAME = "daangn.chat.fanout.exchange";
    public static final String EXCHANGE_HEADERS_NAME = "daangn.chat.headers.exchange";

    public static final String ROUTING_KEY = "daangn.chat.routingKey";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    DirectExchange directExchange() {
        /*
         * 라우팅 기준 : 라우팅 키와 바인딩 키가 정확히 일치하는 경우에만 메시지를 전달
         * 라우팅 키 사용 : 사용 (1:1 채팅)
         * 사용 사례 : 채팅방 ID를 라우팅 키로 사용하여 특정 채팅방으로 메시지 전달
         */
        return new DirectExchange("daangn.chat.direct.exchange");
    }

    @Bean
    TopicExchange topicExchange() {
        /*
         * 라우팅 기준 : 라우팅 키와 바인딩 키가 일부 일치하는 경우에도 메시지를 전달
         * 라우팅 키 사용 : 사용 (1:N 채팅)
         * 사용 사례 : 채팅방 ID를 라우팅 키로 사용하여 특정 채팅방으로 메시지 전달
         */
        return new TopicExchange("daangn.chat.topic.exchange");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        /*
         * 라우팅 기준 : 라우팅 키와 바인딩 키가 무시되고 모든 메시지를 바인딩된 큐로 전달
         * 라우팅 키 사용 : 무시 (N:N 채팅)
         * 사용 사례 : 모든 채팅방에 메시지 전달
         */
        return new FanoutExchange("daangn.chat.fanout.exchange");
    }

    @Bean
    HeadersExchange headersExchange() {
        /*
         * 라우팅 기준 : 메시지 헤더와 바인딩 키가 일치하는 경우에만 메시지를 전달
         * 라우팅 키 사용 : 무시 (N:N 채팅)
         * 사용 사례 : 특정 조건을 만족하는 채팅방에 메시지 전달
         */
        return new HeadersExchange("daangn.chat.headers.exchange");
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) { // 선언적 바인딩
        // return new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, EXCHANGE_DIRECT_NAME, ROUTING_KEY, null);
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // @Value("${spring.rabbitmq.host}")
    // private String host;

    // @Value("${spring.rabbitmq.port}")
    // private int port;

    // @Value("${spring.rabbitmq.username}")
    // private String username;

    // @Value("${spring.rabbitmq.password}")
    // private String password;

    // @Value("${spring.rabbitmq.virtual-host}")
    // private String virtualHost;

    // @Value("${spring.rabbitmq.listener.simple.concurrency}")
    // private int concurrency;

    // @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    // private int maxConcurrency;

    // @Value("${spring.rabbitmq.listener.simple.prefetch}")
    // private int prefetch;

    // @Value("${spring.rabbitmq.listener.simple.default-requeue-rejected}")
    // private boolean defaultRequeueRejected;

    // @Value("${spring.rabbitmq.listener.simple.retry.enabled}")
    // private boolean retryEnabled;

    // @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    // private int retryMaxAttempts;

    // @Value("${spring.rabbitmq.listener.simple.retry.initial-interval}")
    // private long retryInitialInterval;

    // @Value("${spring.rabbitmq.listener.simple.retry.multiplier}")
    // private double retryMultiplier;

    // @Value("${spring.rabbitmq.listener.simple.retry.max-interval}")
    // private long retryMaxInterval;

    // @Value("${spring.rabbitmq.listener.simple.retry.max-duration}")
    // private long retryMaxDuration;

    // @Bean
    // public ConnectionFactory connectionFactory() {
    //     CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    //     connectionFactory.setHost(host);
    //     connectionFactory.setPort(port);
    //     connectionFactory.setUsername(username);
    //     connectionFactory.setPassword(password);
    //     connectionFactory.setVirtualHost(virtualHost);
    //     return connectionFactory;
    // }

    // @Bean
    // public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    //     SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    //     factory.setConnectionFactory(connectionFactory());
    //     factory.setConcurrentConsumers(concurrency);
    //     factory.setMaxConcurrentConsumers(maxConcurrency);
    //     factory.setPrefetchCount(prefetch);
    //     factory.setDefaultRequeueRejected(defaultRequeueRejected);
    //     factory.setAdviceChain(retryInterceptor());
    //     return factory;
    // }

    // @Bean
    // public RetryOperationsInterceptor retryInterceptor() {
    //     return RetryInterceptorBuilder.stateless()
    //         .maxAttempts(retryMaxAttempts)
    //         .backOffOptions(retryInitialInterval, retryMultiplier, retryMaxInterval)
    //         .maxInterval(retryMaxInterval)
    //         .build();
    // }
}
