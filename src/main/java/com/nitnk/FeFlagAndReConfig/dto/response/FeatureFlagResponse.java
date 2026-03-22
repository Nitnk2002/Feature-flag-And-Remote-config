package com.nitnk.FeFlagAndReConfig.dto.response;

import lombok.Data;

@Data
public class FeatureFlagResponse {

    private String featureName;
    private boolean enabled;

}
