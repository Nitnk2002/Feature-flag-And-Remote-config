package com.nitnk.FeFlagAndReConfig.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRemoteConfigRequest {

    @NotBlank(message = "Config key cannot be blank (e.g., 'promo_text')")
    private String configKey;

    @NotBlank(message = "Config value cannot be blank")
    private String configValue;

    private String description; // Optional, so no @NotBlank

    @NotBlank(message = "Application ID is required")
    private String applicationId;
}
