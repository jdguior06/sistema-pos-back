package com.sistema.pos.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;

    public void enviarFacturaPorCorreo(String destinatario, byte[] archivoPDF, String nombreArchivo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true); // "true" habilita multipart para adjuntos

        helper.setTo(destinatario);
        helper.setSubject("Factura de Venta");
        helper.setText("Adjuntamos la factura correspondiente a su compra.");
        helper.addAttachment(nombreArchivo, new ByteArrayResource(archivoPDF));

        mailSender.send(mensaje);
    }

}
