package com.jfb.supermercado.api.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.jfb.supermercado.api.domain.Cliente;
import com.jfb.supermercado.api.domain.Pedido;

public interface EmailService {

    void sendorderConfirmationEmail(Pedido obj);

    void sendeEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}