package org.proustservice.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaExchangeProducerTest {
	Logger logger = LoggerFactory.getLogger(KafkaExchangeProducerTest.class);
	final String TEST_TOPIC = "proust-test";
	
	@Test
	public void test() {

		Properties producerProps = new Properties();
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, "ProustKafkaExchangeTest");
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.IntegerSerializer");
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");

		final KafkaProducer<Integer, String> producer = new KafkaProducer<>(producerProps);

		try {
			final Exception[] caughtExceptions = new Exception[1];
			Callback ackCallback = new Callback() {

				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception != null) {
						caughtExceptions[0] = exception;
					}
					Assert.assertNull(exception);
					if (metadata != null) {
						logger.debug("topic     " + metadata.topic());
						logger.debug("offset    " + metadata.offset());
						logger.debug("partition " + metadata.partition());
					}

				}
			};
			List<String> topics = new ArrayList<>();
			topics.add(TEST_TOPIC);
			ProducerRecord<Integer, String> sentRecord = new ProducerRecord<Integer, String>(TEST_TOPIC,
					"test" + System.currentTimeMillis());
			producer.send(sentRecord, ackCallback);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			if (caughtExceptions[0] != null) {
				throw new RuntimeException(caughtExceptions[0]);
			}
		} finally {
			producer.close();
		}
	}
}
