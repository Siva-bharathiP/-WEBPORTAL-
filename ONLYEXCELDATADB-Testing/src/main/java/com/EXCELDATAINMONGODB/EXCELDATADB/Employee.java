package com.EXCELDATAINMONGODB.EXCELDATADB;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Map<String, Object>> excelData;
}
