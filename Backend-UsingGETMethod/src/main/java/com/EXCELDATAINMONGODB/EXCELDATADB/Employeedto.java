package com.EXCELDATAINMONGODB.EXCELDATADB;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import lombok.Data;
@Data
@Builder
public class Employeedto {
    private String firstName;
    @Field(name = "employee_name")
    private String lastName;
    private String email;
    private String password;
    private List<Map<String, Object>> excelData;
}