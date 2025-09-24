package com.ecommerce.backend.service;

import com.ecommerce.backend.exception.IdNotFound;
import com.ecommerce.backend.model.Request.LogInRequest;
import com.ecommerce.backend.model.Request.OTPRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.serviceImpl.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.ecommerce.backend.model.User;


import java.time.Duration;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class UserService implements UserImpl {
    private Integer otp;
    private LocalTime otpGenerationTime;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Override
    public Object saveOrUpdateUser(User user) {
        if (user.getId() != null && userRepo.existsById(user.getId())) {
            User existingUser = userRepo.findById(user.getId()).get();

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setPhone(user.getPhone());
            existingUser.setRole(user.getRole());
            existingUser.setIsActive(user.getIsActive());
            existingUser.setLastLoggedIn(user.getLastLoggedIn());

            userRepo.save(existingUser);
            return "Updated Successfully!!!";
        } else {
            userRepo.save(user);
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public Object getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(Long id) throws Exception {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            userRepo.deleteById(id);
        } else {
            throw new Exception("Id not Found!!!");
        }
    }

    @Override
    public Object logIn(LogInRequest logInRequest) {
        if (userRepo.existsByEmailIgnoreCaseAndIsActive(logInRequest.getEmail(), true)) {
            User user =userRepo.findByEmailIgnoreCase(logInRequest.getEmail());
            if (user.getEmail().equals(logInRequest.getEmail())) {

                OTPRequest otpRequest = new OTPRequest();
                otpRequest.setOtp(generateOTP());
                otpGenerationTime = LocalTime.now();
                this.sendEmail(user.getEmail(),
                        "Successfully LogIn!!",
                        "Hi " + user.getName() + ",\n Your OTP is : " + otpRequest.getOtp());

                return "log-in successfully!!";
            } else {
                throw new IdNotFound("Password wrong Please Try again!!");
            }
        } else {
            throw new IdNotFound("College Email Not Exist!!");
        }
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);

        } catch (IdNotFound e) {
            log.error("Exception while send Email ", e);
        }
    }

    @Override
    public Object checkOTP(OTPRequest otpRequest) {
        LocalTime verifyTime = LocalTime.now();
        Duration duration = Duration.between(otpGenerationTime, verifyTime);
        if (duration.toMinutes() < 1) {
            if (otp.equals(otpRequest.getOtp()) && otp != 0) {
                otp = 0;
                return "Verify Successfully!!";
            } else {
                throw new IdNotFound("OTP has wrong please Try Again!!");
            }
        } else {
            throw new IdNotFound("OTP Time has Expired!!!");
        }
    }

    @Override
    public Integer generateOTP() {
        Integer max = 999999;
        Integer min = 100000;
        otp = (int) (Math.random() * (max - min + 1) + min);
        return otp;

    }

}
