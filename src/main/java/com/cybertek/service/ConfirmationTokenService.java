package com.cybertek.service;

import com.cybertek.entity.ConfirmationToken;
import com.cybertek.exception.TicketingException;
import org.springframework.mail.SimpleMailMessage;

public interface ConfirmationTokenService {

    ConfirmationToken save(ConfirmationToken confirmationToken);
    void sendEmail(SimpleMailMessage email);
    ConfirmationToken readByToken(String token) throws TicketingException;
    void delete(ConfirmationToken confirmationToken);


}
