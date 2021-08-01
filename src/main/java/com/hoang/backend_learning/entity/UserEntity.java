package com.hoang.backend_learning.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity(name = "User")
@Table(name = "User", schema = "public", catalog = "backend")
public class UserEntity {

    @Id
    private String userId;

    private String userName;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private Boolean isActivate;

    private Date createAt = new Timestamp(System.currentTimeMillis());
}
