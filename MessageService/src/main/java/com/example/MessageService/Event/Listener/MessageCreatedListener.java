package com.example.MessageService.Event.Listener;

import com.example.MessageService.Event.Model.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class MessageCreatedListener {
    private final JavaMailSender mailSender;
    @Bean
    public Consumer<MessageEvent> messageCreatedEvents() {
        return event -> {
            try {
                handleMessageEvent(event);
            } catch (Exception e) {
                System.err.println("!!! Error while creating e-mail orderId:"+event.getOrderId());
            }
        };
    }

    private void handleMessageEvent(MessageEvent event){
        System.out.println("LISTENER CALISTI: " + event.getOrderId());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Siparişiniz Oluşturuldu");
        message.setText(
                "Sipariş No: " + event.getOrderId() +
                        "\nToplam Tutar: " + event.getTotalPrice());

        message.setTo(event.getEmail());
        mailSender.send(message);
    }



}
