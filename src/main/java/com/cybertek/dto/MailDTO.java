package com.cybertek.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class MailDTO {

    private String emailTo;
    private String emailFrom;
    private String message;
    private String token;
    private String subject;
    private String url;


}
