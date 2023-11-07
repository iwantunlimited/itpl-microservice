package io.itpl.microservice.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class GeoLocation {
    private String type;
    private List<Double> coordinates;
}
