package io.itpl.microservice.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import io.itpl.microservice.LoggedInUser;
import io.itpl.microservice.common.UserObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDelivery {

    private String id;
    private String messageId;
    private JsonNode content;
    private Map<String, Object> contents;
    private String senderId;
    private LoggedInUser sender;
    private String recipientId;
    private LoggedInUser recipient;
    private String roomId;
    private boolean delivered;
    private Date lastRetryOn;
    private boolean expired;
    private Date deliveredOn;
    private String eventName;
    private boolean markAsRead;
    private String imageUrl;
    private String domain;
    private boolean emergencyContact;
    private Object feed;
    private Object cloud;
    private String cloudName;
    private Object alert;

    public static MessageDelivery builder(JsonNode content){
        MessageDelivery message = new MessageDelivery();
        message.setContent(content);
        return message;
    }
}
