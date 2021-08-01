package com.hoang.backend_learning.repository;

import com.hoang.backend_learning.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
