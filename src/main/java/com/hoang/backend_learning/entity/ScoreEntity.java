package com.hoang.backend_learning.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Score")
@Table(name = "Score", schema = "public", catalog = "backend")
public class ScoreEntity {

    @Id
    private String studentId;

    private float math;

    private float literature;

    private float programmer;
}
