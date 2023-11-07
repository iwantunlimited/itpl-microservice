package io.itpl.microservice.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
public class Person {
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobile;
    private String mcc;
    private String email;
    private String gender;
    private String relation;
    private String designation;
    private String identityNumber;
}
