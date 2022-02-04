package com.WebApp.EmailServerClient.Emails;

import com.WebApp.EmailServerClient.Constants;
import com.WebApp.EmailServerClient.Controllers.SchemaValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class EmailBuilder {

    public static Email BuildEmail(String email) throws JsonProcessingException {

        ObjectMapper om = SchemaValidator.Validate(Constants.EMAIL_SCHEMA, email);
        Email realEmail = om.readValue(email, Email.class);
        Date date = new Date();
        realEmail.date = date;
        realEmail.StringDate = date.toString();

        return realEmail;
    }
}
