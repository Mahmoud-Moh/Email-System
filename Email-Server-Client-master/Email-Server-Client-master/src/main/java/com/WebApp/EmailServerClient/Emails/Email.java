package com.WebApp.EmailServerClient.Emails;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

public class Email implements Clone<Email> {
    public ArrayList<String> Attachment;
    public String Sender, Subject, Body, StringDate;
    public Date date;
    public Queue<String> Receiver;
    public int Priority, ID;

    public Email(ArrayList<String> attachment, String sender, String subject, String body, String stringDate,
                 Date date, Queue<String> receiver, int priority, int ID) {
        this.Sender = sender;
        this.Subject = subject;
        this.Body = body;
        this.StringDate = stringDate;
        this.date = date;
        this.Receiver = receiver;
        this.Priority = priority;
        this.ID = ID;
        this.Attachment = attachment;
    }

    public Email() {
    }

    @Override
    public String toString() {
        return "Email{" +
                "Sender='" + Sender + '\'' +
                ", Receiver='" + Receiver.peek() + '\'' +
                ", Subject='" + Subject + '\'' +
                ", Body='" + Body + '\'' +
                ", Priority=" + Priority +
                '}';
    }

    @Override
    public Email initClone(int mailID, String Receiver) {
        Email email = initClone();
        email.Receiver.clear();
        email.Receiver.add(Receiver);
        email.ID = mailID;
        return email;
    }

    @Override
    public Email initClone() {
        Email email = new Email(Attachment, Sender, Subject, Body, StringDate, date, Receiver, Priority, ID);
        email.Receiver = new ArrayDeque<>(email.Receiver);
        return email;
    }

    public ArrayList<String> getAttachment() {
        return Attachment;
    }

    public void setAttachment(ArrayList<String> attachment) {
        Attachment = attachment;
    }
}
