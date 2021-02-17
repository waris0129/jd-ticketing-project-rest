package com.cybertek.implementation;

import com.cybertek.entity.ConfirmationToken;
import com.cybertek.exception.TicketingException;
import com.cybertek.repository.ConfirmationTokenRepository;
import com.cybertek.service.ConfirmationTokenService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenImpl implements ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;
    private JavaMailSender javaMailSender;

    public ConfirmationTokenImpl(ConfirmationTokenRepository confirmationTokenRepository, JavaMailSender javaMailSender) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {

        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    @Async // send mail right away, handle it right away
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Override
    public ConfirmationToken readByToken(String token) throws TicketingException {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElse(null);

        if(confirmationToken==null)
            throw new TicketingException("this token does not exist");

        if(!confirmationToken.isTokenValid(confirmationToken.getExpiredDate()))
            throw new TicketingException("this token does not exist");

        return confirmationToken;
    }

    @Override
    public void delete(ConfirmationToken confirmationToken) {

        confirmationToken.setIsDeleted(true);

        confirmationTokenRepository.save(confirmationToken);

    }
}
