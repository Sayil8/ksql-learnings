package com.smuehr.ksql;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;
import org.json.JSONObject;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SyntheticProducer {

    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "ksql-kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(config);

        int id = 0;
        while (true) {
            System.out.println("Producing record");
            try {
                producer.send(getTransaction(id));
                Thread.sleep(1000);
                id ++;
                if (id == 100) break;
            } catch (InterruptedException e) {
                break;
            }
        }

        producer.close();
    }

    public static ProducerRecord<String, String> getTransaction(Integer userid) {
        JSONObject transaction = new JSONObject();
        Double rating = ThreadLocalRandom.current().nextDouble(0, 5);
        transaction.put("userid", userid);
        transaction.put("firstname", generateName());
        transaction.put("lastname", generateName());
        transaction.put("countrycode", "MX");
        transaction.put("rating", rating);
        return new ProducerRecord<String, String>("USERPROFILE", null, transaction.toString());
    }

    private static String generateName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
