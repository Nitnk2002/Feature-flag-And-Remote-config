package com.nitnk.FeFlagAndReConfig.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;

    private String companyName;
    @NonNull
    private String email;

    private String role;

    private boolean active;

    private LocalDateTime createdAt;


}
