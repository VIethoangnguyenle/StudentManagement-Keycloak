package com.hoang.backend_learning.entity;

import lombok.Data;

import javax.persistence.*;
import java.nio.file.LinkOption;
import java.sql.Timestamp;

@Data
@Entity(name = "Student")
@Table(name = "Student", schema = "public", catalog = "backend")
public class StudentInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;

    private String name;

    private String major;

    private int age;

    private Timestamp createAt = new Timestamp(System.currentTimeMillis());

    private Long timeStamp;

    private String address;

    public enum Sex {
        MALE,
        FEMALE
    }
}
