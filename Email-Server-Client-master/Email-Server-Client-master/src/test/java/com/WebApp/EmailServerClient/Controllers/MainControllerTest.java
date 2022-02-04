package com.WebApp.EmailServerClient.Controllers;

import com.WebApp.EmailServerClient.Emails.Email;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.Queue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MainController.class)
@RunWith(SpringRunner.class)
class MainControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void sendEmail() throws Exception {
        Email email = new Email();
        Queue<String> q = new LinkedList<>();
        q.add("moamen@Designpatterns.com");
        email.Receiver = new LinkedList<>(q);
        email.Priority = 1;
        email.Body = "we need to finish this project today";
        email.Subject = "project";
        email.Sender = "Youssef@Designpatterns.com";
        mvc.perform(post("/mail/1").contentType(MediaType.APPLICATION_JSON).content(email.toString()));
    }
}