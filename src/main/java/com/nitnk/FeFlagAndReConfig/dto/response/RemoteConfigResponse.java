package com.nitnk.FeFlagAndReConfig.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteConfigResponse {
    // We only send the exact Key and Value to the mobile app. No IDs!
    private String configKey;
    private String configValue;
}