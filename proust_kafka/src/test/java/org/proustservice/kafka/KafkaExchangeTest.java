package org.proustservice.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaExchangeTest {
	Logger logger = LoggerFactory.getLogger(KafkaExchangeTest.class);

	@Test
	public void test() {
		;
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");

		final KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);

		Properties producerProps = new Properties();
		producerProps.put("bootstrap.servers", "localhost:9092");
		producerProps.put("client.id", "ProustKafkaExchangeTest");
		producerProps.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		final KafkaProducer<Integer, String> producer = new KafkaProducer<>(producerProps);
		final Exception[] caughtExceptions = new Exception[1];
		Callback ackCallback = new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					caughtExceptions[0] = exception;
				}
				if (metadata != null) {
					logger.debug("topic     " + metadata.topic());
					logger.debug("offset    " + metadata.offset());
					logger.debug("partition " + metadata.partition());
				}
			}
		};
		List<String> topics = new ArrayList<>();
		final String TEST_TOPIC = "proust-test";
		topics.add(TEST_TOPIC);
		ProducerRecord<Integer, String> sentRecord = new ProducerRecord<Integer, String>(TEST_TOPIC, "test");
		producer.send(sentRecord, ackCallback);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		if (caughtExceptions[0] != null) {
			throw new RuntimeException(caughtExceptions[0]);
		}
		List<PartitionInfo> partitions = consumer.partitionsFor("proust-test");
		consumer.subscribe(topics);
		ConsumerRecords<Integer, String> results = consumer.poll(100);
		ConsumerRecord<Integer, String> result = results.records(TEST_TOPIC).iterator().next();
		logger.debug("key : " + result.key());
		logger.debug("value : " + result.value());
		Assert.assertEquals("test", result.value());

	}
}
