package com.nitnk.FeFlagAndReConfig.dto.request;

import lombok.Data;

@Data
public class CreateFeatureRequest {

    private String featureName;
    private boolean enabled;
    private String applicationId;
    private int rolloutPercentage;

}
