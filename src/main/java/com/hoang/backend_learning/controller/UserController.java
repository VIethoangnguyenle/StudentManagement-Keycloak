package com.hoang.backend_learning.controller;

import com.hoang.backend_learning.dto.*;
import com.hoang.backend_learning.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
@Tag(name = "User")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("register")
    public RestResponseDto<Object> register(@Valid @RequestBody UserRegisterDto userRegisterDto, HttpServletRequest context) {
        log.info("Start register");
        context.getSession().invalidate();
        context.getSession().setAttribute(userRegisterDto.getClass().getName(), userRegisterDto);
        return new RestResponseDto<>().success(userRegisterDto);
    }

    @PostMapping("account")
    public RestResponseDto<Object> createAccount(@Valid @RequestBody StudentInfoDto studentInfoDto, HttpServletRequest context) {
        log.info("Write information of student");
        if (context.getSession().isNew()) {
            context.getSession().invalidate();
            log.error("Session timeout");
            return new RestResponseDto<>().badRequest();
        }
        context.getSession().setAttribute(studentInfoDto.getClass().getName(), studentInfoDto);
        return new RestResponseDto<>().success(studentInfoDto);
    }

    @PostMapping("set-password")
    public RestResponseDto<Object> setPassword(@Valid @RequestBody PasswordDto passwordDto, HttpServletRequest context) {
        log.info("Set password");
        if (context.getSession().isNew()) {
            context.getSession().invalidate();
            log.error("Session timeout");
            return new RestResponseDto<>().badRequest();
        }
        context.getSession().setAttribute(PasswordDto.class.getName(), passwordDto);
        return new RestResponseDto<>().success(passwordDto);
    }

    @PostMapping("verify")
    public RestResponseDto<Object> verify(@Valid @RequestBody VerifyDto verifyDto, HttpServletRequest context) {
        log.info("Verified");
        UserRegisterDto userRegisterDto = (UserRegisterDto) context.getSession().getAttribute(UserRegisterDto.class.getName());
        StudentInfoDto studentInfoDto = (StudentInfoDto) context.getSession().getAttribute(StudentInfoDto.class.getName());
        PasswordDto passwordDto = (PasswordDto) context.getSession().getAttribute(PasswordDto.class.getName());
        if (verifyDto.getVerify().equals("12345")){
            return userService.createUser(studentInfoDto, userRegisterDto, passwordDto);
        }
        return new RestResponseDto<>().badRequest();
    }
}
