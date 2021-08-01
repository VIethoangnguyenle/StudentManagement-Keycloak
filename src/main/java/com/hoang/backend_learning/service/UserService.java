package com.hoang.backend_learning.service;

import com.hoang.backend_learning.dto.*;
import com.hoang.backend_learning.entity.StudentInfoEntity;
import com.hoang.backend_learning.entity.UserEntity;
import com.hoang.backend_learning.keycloak.KeycloakService;
import com.hoang.backend_learning.repository.StudentRepository;
import com.hoang.backend_learning.repository.UserRepository;
import com.hoang.backend_learning.utils.FnCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;

@Component
@Slf4j
public class UserService {

    @Autowired
    KeycloakService keycloakService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    public RestResponseDto<Object> createUser(StudentInfoDto studentInfoDto, UserRegisterDto userRegisterDto, PasswordDto passwordDto) {
        RestResponseDto<Object> restResponseDto;
        UserEntity userEntity;
        StudentInfoEntity studentInfoEntity;

        if (studentInfoDto != null && userRegisterDto != null) {

            // Create user keycloak
            restResponseDto = keycloakService.createUser(
                userRegisterDto.getUserName(),
                passwordDto.getPassword(),
                userRegisterDto.getEmail(),
                userRegisterDto.getUserName()
            );
            System.out.println("ahhaha "+ restResponseDto.toString());
            if (restResponseDto.getStatus() != 200) {
                log.error("Keycloak error ne " + restResponseDto.getStatus());
                return restResponseDto;
            }

            log.trace("Create keycloak user successfully");
            userEntity = new UserEntity();
            userEntity.setEmail(userEntity.getEmail());
            userEntity.setUserName(userRegisterDto.getUserName());
            userEntity.setIsActivate(true);
            userEntity.setPhoneNumber(userEntity.getPhoneNumber());
            userEntity.setUserId((String) ((HashMap) restResponseDto.getData()).get("userId"));

            log.trace("Set basic info successfully");
        } else {
            log.trace("Fail to create user");
            return new RestResponseDto<>().badRequest();
        }

        studentInfoEntity = new StudentInfoEntity();
        studentInfoEntity.setAddress(studentInfoDto.getAddress());
        studentInfoEntity.setAge(studentInfoDto.getAge());
        studentInfoEntity.setMajor(studentInfoDto.getMajor());
        studentInfoEntity.setName(studentInfoDto.getFullName());
        studentInfoEntity.setTimeStamp((new Timestamp(System.currentTimeMillis())).getTime());
        studentInfoEntity.setAddress(studentInfoDto.getAddress());
        studentInfoEntity.setStudentId(FnCommon.generateStudentId());
        log.trace("Set info student successfully");

        userRepository.save(userEntity);
        studentRepository.save(studentInfoEntity);

        return restResponseDto;
    }

    public String getUserIdByPhoneNumber(PhoneNumberDto dto) {
        return keycloakService.getUserIdByPhoneNumber(dto);
    }
}
