package kafka.consumer.demo;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import kafka.schemas.Employee;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerAVRODemo {
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "employee_consumer_group");
        props.put("schema.registry.url", "http://localhost:8081");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                StringDeserializer.class.getName());
        props.put("value.deserializer",
                KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,true);
        KafkaConsumer<String, Employee> consumer = new KafkaConsumer<String, Employee>(props);

        consumer.subscribe(Arrays.asList("employee_data"));

        int i = 0;

        while (true) {
            ConsumerRecords<String, Employee> records = consumer.poll(100);
            for (ConsumerRecord<String, Employee> record : records)
                System.out.println(record.value().getName()+" "+
                        record.value().getAge()+" "+
                        record.value().getSalary()
                        //+" "+
                        //record.value().getSalary()
                );
        }

    }
}
