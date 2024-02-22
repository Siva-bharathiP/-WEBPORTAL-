package com.EXCELDATAINMONGODB.EXCELDATADB;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
