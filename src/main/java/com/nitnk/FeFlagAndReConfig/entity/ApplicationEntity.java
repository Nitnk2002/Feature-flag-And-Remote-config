package com.nitnk.FeFlagAndReConfig.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "applications")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApplicationEntity {

    @Id
    private String id;

    private String appName;

    private String userId;

    private String apiKey;


}
