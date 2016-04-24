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

public class KafkaExchangeConsumerTest {
	Logger logger = LoggerFactory.getLogger(KafkaExchangeConsumerTest.class);
	final String TEST_TOPIC = "proust-test";
	
	@Test
	public void test() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "proust-test-consumer");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		final KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);
		try {
			consumer.subscribe(Arrays.asList(TEST_TOPIC));
			//List<PartitionInfo> partitions = consumer.partitionsFor(TEST_TOPIC);
            ConsumerRecords<Integer, String> records = null;
            do {
            	records = consumer.poll(1000);
            } while (records.isEmpty());
			logger.debug("nb results " + records.count());
			String lastValue = "";
	        for (ConsumerRecord<Integer, String> record : records) {
	        	long recordOffset = record.offset();
	        	Integer recordKey = record.key();
	        	lastValue = record.value();
	        	logger.debug("offset = %d, key = %s, value = %s", recordOffset, (recordKey == null)?0:recordKey, lastValue);
	        }
			Assert.assertTrue(lastValue.startsWith("test"));
		} finally {
			consumer.close();
		}
	}
}
