package com.yantriks.example.demo.beans;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    private String name;
    private String id;
    private String address;
    private String organization;
    private String role;

}
