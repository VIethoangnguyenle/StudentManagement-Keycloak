package com.hoang.backend_learning.repository;

import com.hoang.backend_learning.entity.ScoreEntity;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<ScoreEntity, String> {
}
