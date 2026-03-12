package com.nitnk.FeFlagAndReConfig.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feature_flags")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureFlagEntity {

    @Id
    private ObjectId id;

    private String featureName;

    private boolean enabled;

    private String applicationId;
}
