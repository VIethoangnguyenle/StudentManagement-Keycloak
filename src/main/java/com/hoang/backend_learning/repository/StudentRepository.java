package com.hoang.backend_learning.repository;

import com.hoang.backend_learning.entity.StudentInfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<StudentInfoEntity, Long> {

}
