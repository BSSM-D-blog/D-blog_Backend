package com.example.Dblog.error;

import lombok.Data;

@Data
public class ResponseMessage {
    private ReponseStatus status;
    private String message;
    private Object data;

    public ResponseMessage(){
        this.status = ReponseStatus.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }
}
