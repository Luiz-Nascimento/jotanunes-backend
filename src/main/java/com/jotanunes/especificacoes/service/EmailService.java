package com.jotanunes.especificacoes.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailTexto(List<String> destinatarios, String assunto, String mensagem) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            String[] gestores = destinatarios.toArray(new String[0]);
            simpleMailMessage.setFrom("squad12especificacoes@gmail.com");
            simpleMailMessage.setTo(gestores);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);

            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.err.println("Erro ao enviar o email: " + e.getMessage());
        }
    }
}
