package com.niewhic.vetclinic.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageService {

    public String getConfirmationEmailBody(String language, String patientName, String confirmationLink) {
        Locale locale = new Locale(language);
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        String subject = messages.getString("appointment.confirmation.subject");
        String bodyTemplate = messages.getString("appointment.confirmation.body");

        return MessageFormat.format(bodyTemplate, patientName, confirmationLink);
    }
    public String getSubject(String language, String subjectKey) {
        Locale locale = new Locale(language);
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(subjectKey);
    }


}


