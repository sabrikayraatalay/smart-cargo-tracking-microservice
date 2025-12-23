package com.KayraAtalay.service;

import com.KayraAtalay.shared.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = "notification-queue")
    public void handleNotification(NotificationMessage message) {
        System.out.println("Message Received: " + message.getTrackingNumber());
        sendEmail(message);
    }

    private void sendEmail(NotificationMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();


        mailMessage.setFrom("sabrikayraa@gmail.com");
        mailMessage.setTo(message.getReceiverEmail());

        String subject = "";
        String body = "";

        switch (message.getMessageType()) {
            case "CARGO_CREATED":
                subject = "Order Received - LogiTrack";
                body = "Hello " + message.getReceiverName() + ",\n\n" +
                        "Your cargo order has been successfully created.\n" +
                        "Tracking Number: " + message.getTrackingNumber() + "\n" +
                        "Please provide this code to the branch for drop-off: " + message.getDeliveryCode() + "\n\n" +
                        "Thank you for choosing us.";
                break;

            case "CARGO_ACCEPTED":
            case "CARGO_RECEIVED": // Service'de yazdığın string ile birebir aynı olmalı
                subject = "Cargo On The Way - LogiTrack";
                body = "Hello " + message.getReceiverName() + ",\n\n" +
                        "Your cargo with tracking number " + message.getTrackingNumber() +
                        " has been accepted by our branch and is now on its way.\n\n" +
                        "Best Regards.";
                break;


            case "CARGO_DELIVERED":
                subject = "Delivery Successful - LogiTrack";
                body = "Hello " + message.getReceiverName() + ",\n\n" +
                        "Your cargo (" + message.getTrackingNumber() + ") has been delivered successfully.\n" +
                        "We hope to see you again!";
                break;

            case "CARGO_CANCELLED":
                subject = "Order Cancelled - LogiTrack";
                body = "Hello " + message.getReceiverName() + ",\n\n" +
                        "Your cargo order (" + message.getTrackingNumber() + ") has been cancelled as per your request.\n\n" +
                        "If this was a mistake, please create a new order.";
                break;

            default:
                subject = "Cargo Notification";
                body = "There is an update regarding your cargo: " + message.getTrackingNumber();
                break;
        }

        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        try {
            javaMailSender.send(mailMessage);
            System.out.println("Email sent successfully to: " + message.getReceiverEmail());
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}