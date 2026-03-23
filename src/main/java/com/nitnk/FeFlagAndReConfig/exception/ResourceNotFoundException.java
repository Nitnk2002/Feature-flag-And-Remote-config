package com.nitnk.FeFlagAndReConfig.exception;

import lombok.Data;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException (String message){
        super(message);
    }
}
