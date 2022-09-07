// package com.demo.ebankingportal.kafka;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.support.KafkaHeaders;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.support.MessageBuilder;
// import org.springframework.stereotype.Service;

// import com.demo.ebankingportal.models.User;
// import com.demo.ebankingportal.payload.responses.TransactionResponse;

// @Service
// public class JsonKafkaProducer {
//     private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

//     private KafkaTemplate<String, User> kafkaTemplate;

//     public JsonKafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
//         this.kafkaTemplate = kafkaTemplate;
//     }

//     public void sendMessage(User user) {
//         LOGGER.info(String.format("Message sent -> %s",user.toString()));
//         Message<User> message = MessageBuilder.withPayload(user).setHeader(KafkaHeaders.TOPIC, "eBanking_json").build();
//         kafkaTemplate.send(message);
//     }

//     public void sendTransactionMessage(TransactionResponse transactionResponse) {
//         LOGGER.info(String.format("Message sent -> %s",transactionResponse.toString()));
//         Message<TransactionResponse> message = MessageBuilder.withPayload(transactionResponse).setHeader(KafkaHeaders.TOPIC, "eBanking_json").build();
//         kafkaTemplate.send(message);
//     }
    
// }
