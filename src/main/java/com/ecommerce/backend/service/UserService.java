package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.UserDto;
import com.ecommerce.backend.exception.IdNotFound;
import com.ecommerce.backend.dto.request.LogInRequest;
import com.ecommerce.backend.dto.request.OTPRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.serviceImpl.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
    public Object saveOrUpdateUser(UserDto userDto) {
        try {
            if (userDto.getId() != null && userRepo.existsById(userDto.getId())) {
                // Update existing user
                User existingUser = userRepo.findById(userDto.getId()).get();
                existingUser.setName(userDto.getName());
                existingUser.setEmail(userDto.getEmail());
                existingUser.setPassword(userDto.getPassword());
                existingUser.setPhone(userDto.getPhone());

                // Role assign only if provided in DTO
                if (userDto.getRole() != null) {
                    existingUser.setRole(userDto.getRole());
                }

                existingUser.setIsActive(userDto.getIsActive() != null ? userDto.getIsActive() : existingUser.getIsActive());
                existingUser.setLastLoggedIn(userDto.getLastLoggedIn() != null ? userDto.getLastLoggedIn() : existingUser.getLastLoggedIn());

                userRepo.save(existingUser);
                log.info("User updated successfully with ID: {}", userDto.getId());
                return "Updated Successfully!!!";

            } else {
                // Insert new user
                User newUser = new User();
                newUser.setName(userDto.getName());
                newUser.setEmail(userDto.getEmail());
                newUser.setPassword(userDto.getPassword());
                newUser.setPhone(userDto.getPhone());

                // Role assign only if provided in DTO
                if (userDto.getRole() != null) {
                    newUser.setRole(userDto.getRole());
                }

                newUser.setIsActive(userDto.getIsActive() != null ? userDto.getIsActive() : true);
                newUser.setLastLoggedIn(userDto.getLastLoggedIn());

                userRepo.save(newUser);
                log.info("New User inserted with ID: {}", newUser.getId());
                return "Inserted Successfully!!!";
            }
        } catch (Exception e) {
            log.error("Error in saveOrUpdateUser for UserDto: {}", userDto, e);
            throw e;
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        log.info("Fetched all users, count: {}", users.size());

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setPhone(user.getPhone());
            dto.setRole(user.getRole()); // Enum Role
            dto.setIsActive(user.getIsActive());
            dto.setLastLoggedIn(user.getLastLoggedIn());

            userDtos.add(dto);
        }
        return userDtos;
    }


    @Override
    public void deleteUserById(Long id) throws Exception {
        try {
            Optional<User> optional = userRepo.findById(id);
            if (optional.isPresent()) {
                userRepo.deleteById(id);
                log.info("User with ID {} has been deleted successfully.", id);
            } else {
                log.error("User not found with ID: {}", id);
                throw new Exception("Id not Found!!!");
            }
        } catch (Exception e) {
            log.error("Error while deleting User with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public Object logIn(LogInRequest logInRequest) {
        try {
            if (userRepo.existsByEmailIgnoreCaseAndIsActive(logInRequest.getEmail(), true)) {
                User user = userRepo.findByEmailIgnoreCase(logInRequest.getEmail());
                OTPRequest otpRequest = new OTPRequest();
                otpRequest.setOtp(generateOTP());
                otpGenerationTime = LocalTime.now();
                this.sendEmail(user.getEmail(),
                        "Successfully LogIn!!",
                        "Hi " + user.getName() + ",\n Your OTP is : " + otpRequest.getOtp());
                log.info("Login OTP sent successfully to email: {}", user.getEmail());
                return "log-in successfully!!";
            } else {
                log.error("Login failed: User Email Not Exist or inactive: {}", logInRequest.getEmail());
                throw new IdNotFound("User Email Not Exist!!");
            }
        } catch (Exception e) {
            log.error("Error during logIn for email: {}", logInRequest.getEmail(), e);
            throw e;
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
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Exception while sending email to: {}", to, e);
        }
    }

    @Override
    public Object checkOTP(OTPRequest otpRequest) {
        try {
            LocalTime verifyTime = LocalTime.now();
            Duration duration = Duration.between(otpGenerationTime, verifyTime);
            if (duration.toMinutes() < 1) {
                if (otp.equals(otpRequest.getOtp()) && otp != 0) {
                    otp = 0;
                    log.info("OTP verified successfully");
                    return "Verify Successfully!!";
                } else {
                    log.error("OTP mismatch: {}", otpRequest.getOtp());
                    throw new IdNotFound("OTP has wrong please Try Again!!");
                }
            } else {
                log.error("OTP expired");
                throw new IdNotFound("OTP Time has Expired!!!");
            }
        } catch (Exception e) {
            log.error("Error while verifying OTP: {}", otpRequest.getOtp(), e);
            throw e;
        }
    }

    @Override
    public Integer generateOTP() {
        Integer max = 999999;
        Integer min = 100000;
        otp = (int) (Math.random() * (max - min + 1) + min);
        log.info("Generated OTP: {}", otp);
        return otp;
    }

}
