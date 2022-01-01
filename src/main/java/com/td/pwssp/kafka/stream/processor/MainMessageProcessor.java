package com.td.pwssp.kafka.stream.processor;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.td.pwssp.kafka.stream.utility.LogHelper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.kstream.KStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaStreamsCustomizer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class MainMessageProcessor {	
	@Autowired 
	private KafkaTemplate<byte[], byte[]> kafkaTemplate;
	
	@Value("${spring.cloud.stream.kafka.streams.bindings.process-in-0.consumer.dlqName}")
	String dlqTopic;
	
    @Bean
    public Consumer<KStream<String, byte[]>> process() {
		LogHelper lh = new LogHelper(log);
		return input ->
                input.foreach((key, value) -> {
                	try {
						lh.setKey(key);
						String s = new String(value, StandardCharsets.UTF_8);
	                	lh.trace("Json Topic Value: " + s);
	                	
	                	// test 1.  unhandle exception 
	                	int a = 1000;
	                	int b = a/0;
	                	log.trace("test unhandle exception:  {}", b);
	                	
	                	JSONParser parser = new JSONParser();
	        			JSONObject returnValue = null;
	        			
	        			// test 3.  bad message exception 
	        			//value = "qwqeqweqrq".getBytes();
	        			returnValue = (JSONObject) parser.parse(s);
	        			//returnValue = (JSONObject) returnValue.get("AciPayload");
	        			lh.trace("return value: " + returnValue);
	        			
	        		} catch (ParseException e) {
	        			lh.error("Bad Message : has Json parse exception");
	        			kafkaTemplate.send(dlqTopic, key.getBytes(),  value );
                	} catch (Exception e) {
                		lh.error("Catch Exception:  {}"+ e.getMessage());
                		String exceptionDetail = "Catch Exception: " + e.getMessage() ;
                		ProducerRecord<byte[], byte[]> record = new ProducerRecord<byte[], byte[]>(dlqTopic,key.getBytes(), value);
                		record.headers().add("kafka_dlt-exception-message", exceptionDetail.getBytes());
                		
                		kafkaTemplate.send(record);
                	}
                });
    }
}
