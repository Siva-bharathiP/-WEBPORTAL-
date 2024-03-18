package com.EXCELDATAINMONGODB.EXCELDATADB;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Payload {
    private Employeedto emp;
    private List<Map<String, Object>> excelData;
}