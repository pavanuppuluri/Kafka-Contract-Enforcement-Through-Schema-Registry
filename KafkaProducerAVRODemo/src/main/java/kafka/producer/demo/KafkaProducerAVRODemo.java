package kafka.producer.demo;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import kafka.schemas.Employee;




import java.util.Properties;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class KafkaProducerAVRODemo {
    public static void main(String[] args) {

        // Producer Properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer",StringSerializer.class.getName());
        //props.put("value.serializer",KafkaAvroSerializer.class);
        props.put("value.serializer",KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://localhost:8081");

//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
//        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        // Create the Producer
         KafkaProducer<String,Employee> producer = new KafkaProducer<String, Employee>(props);

        // Create Producer Record

        Employee employee=Employee.newBuilder()
               .setId("1")
                .setName("SaiRamayya!!!")
                .setAge(33)
                .setSalary(1000)
                .build();
        int recordsCount=0;


        while(recordsCount<=10) {
            ProducerRecord<String, Employee> producerRecord = new ProducerRecord<String, Employee>("employee_data", employee);
            // Send data
            System.out.println("Sending Employee Data "+employee.getName()+" "+
                                       employee.getAge()+" "+
                                       employee.getSalary()
            );
            producer.send(producerRecord);
            recordsCount++;
        }
        producer.flush();
        producer.close();

    }
}
