package nl.blue4it.streaming.config;

import nl.blue4it.streaming.exception.handler.UnCaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;

@Configuration
public class StreamConfiguration {

    final UnCaughtExceptionHandler exceptionHandler;

    public StreamConfiguration(UnCaughtExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Bean
    public StreamsBuilderFactoryBeanConfigurer streamsCustomizer() {
        return new StreamsBuilderFactoryBeanConfigurer() {

            @Override
            public void configure(StreamsBuilderFactoryBean factoryBean) {
                factoryBean.setStreamsUncaughtExceptionHandler(exceptionHandler);
            }

            @Override
            public int getOrder() {
                return Integer.MAX_VALUE;
            }

        };
    }

}
