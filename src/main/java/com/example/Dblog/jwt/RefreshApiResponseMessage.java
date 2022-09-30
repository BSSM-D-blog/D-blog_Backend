package com.example.Dblog.jwt;

import lombok.Getter;

import java.util.Map;

@Getter
public class RefreshApiResponseMessage {
    private String status;
    private String message;
    private String errortype;
    private String accessToken;
    public RefreshApiResponseMessage(Map<String, String> map){
        this.status = map.get("status");
        this.message = map.get("message");
        this.errortype = map.get("errortype");
        this.accessToken = map.get("accessToken");
    }
}
