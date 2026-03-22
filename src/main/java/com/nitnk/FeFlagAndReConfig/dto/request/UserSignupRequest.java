package com.nitnk.FeFlagAndReConfig.dto.request;

import lombok.Data;

@Data
public class UserSignupRequest {

    private String username;
    private String password;
    private String email;
    private String companyName;
}
