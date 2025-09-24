package com.ecommerce.backend.model.Request;

import lombok.Data;

@Data
public class OTPRequest {
    private Integer otp;

    // Getter and Setter for otp
    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
