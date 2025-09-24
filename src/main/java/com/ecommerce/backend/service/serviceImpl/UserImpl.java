package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Request.LogInRequest;
import com.ecommerce.backend.model.Request.OTPRequest;
import com.ecommerce.backend.model.User;

public interface UserImpl {
    public Object saveOrUpdateUser(User user);
    public Object getAllUser();
    public void deleteUserById(Long id) throws Exception;
    Object logIn(LogInRequest logInRequest);
    public void sendEmail(String to, String subject, String body);
    Object checkOTP(OTPRequest otpRequest);
    public Integer generateOTP();
}
