package com.td.pwssp.kafka.stream.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public Map<String, Object> producerConfigs(){
    	Map<String, Object> props = new HashMap<>();
    	props.put("security.protocol", "SSL");
    	props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "bk02.r1.c4.pkfka.dev.td.com:9096,bk03.r1.c4.pkfka.dev.td.com:9096,bk01.r1.c4.pkfka.dev.td.com:9096,bk04.r1.c4.pkfka.dev.td.com:9096");
    	props.put("ssl.truststore.location", "C:\\Wells\\Workspace\\Git\\demokafkastream\\src\\main\\resources\\kafka_dev_certificates\\tprtsp363sprs.client.prtsp.openstack.pkfka.dev.td.com.truststore.jks");
    	props.put("ssl.truststore.password", "prtsp2021DEV");
    	props.put("ssl.keystore.location", "C:\\Wells\\Workspace\\Git\\demokafkastream\\src\\main\\resources\\kafka_dev_certificates\\tprtsp363sprs.client.prtsp.openstack.pkfka.dev.td.com.keystore.jks");
    	props.put("ssl.keystore.password", "prtsp2021DEV");
    	props.put("ssl.key.password", "prtsp2021DEV");
    	props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    	props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    	
    	
    	return props;
    }
    
    @Bean
    public KafkaTemplate<byte[], byte[]> kafkaTemplate(){
    	return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }
}
