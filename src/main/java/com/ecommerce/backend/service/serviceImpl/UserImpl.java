package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.UserDto;
import com.ecommerce.backend.dto.request.LogInRequest;
import com.ecommerce.backend.dto.request.OTPRequest;
import com.ecommerce.backend.model.User;

import java.util.List;

public interface UserImpl {
    Object saveOrUpdateUser(UserDto userDto);
    List<UserDto> getAllUsers();
    public void deleteUserById(Long id) throws Exception;
    Object logIn(LogInRequest logInRequest);
    public void sendEmail(String to, String subject, String body);
    Object checkOTP(OTPRequest otpRequest);
    public Integer generateOTP();
}
