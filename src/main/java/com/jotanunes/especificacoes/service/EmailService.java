package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.exception.SendEmailException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final String emailSistema = "squad12especificacoes@gmail.com";

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void notificarEmpreendimentoPendente(String emailDestinatario, String assunto, Map<String, Object> variaveisTemplate) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveisTemplate);

            String htmlContent = templateEngine.process("empreendimento-pendente-email", context);

            helper.setFrom(emailSistema);
            helper.setTo(emailDestinatario);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SendEmailException("Erro ao notificar gestores por email");
        }
    }
    public void notificarNovoUsuario(String emailDestinatario, String assunto, Map<String, Object> variaveis) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveis);

            String htmlContent = templateEngine.process("new-user-email.html", context);

            helper.setFrom(emailSistema);
            helper.setTo(emailDestinatario);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SendEmailException("Erro ao notificar gestores por email");
        }
    }

    public void notificarResetSenha(String emailDestinatario, String assunto, Map<String, Object> variaveis) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveis);

            String htmlContent = templateEngine.process("email-reset-senha.html", context);

            helper.setFrom(emailSistema);
            helper.setTo(emailDestinatario);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SendEmailException(e.getMessage());
        }
    }
}
