package com.nitnk.FeFlagAndReConfig.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "remote_configs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteConfigEntity {

    @Id
    private String id;

    private String key;

    private String value;

    private String applicationId;

}
