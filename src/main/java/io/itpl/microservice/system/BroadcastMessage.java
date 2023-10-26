package io.itpl.microservice.system;

import com.fasterxml.jackson.databind.JsonNode;
import io.itpl.microservice.ActionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BroadcastMessage {
    private String domain;
    private ActionRequest actionRequest;
    private JsonNode requestObject;
    private JsonNode responseObject;
    private String pathVariable;
    private String extraInfo;
}
