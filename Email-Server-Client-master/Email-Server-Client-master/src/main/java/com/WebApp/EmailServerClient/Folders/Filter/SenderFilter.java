package com.WebApp.EmailServerClient.Folders.Filter;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public class SenderFilter implements Filter {
    @Override
    public ArrayList<Email> MeetCriteria(ArrayList<Email> Input, String... Sender) {
        ArrayList<Email> Answer = new ArrayList<>();
        for (Email email : Input) {
            if (email.Sender.equalsIgnoreCase(Sender[0])) {
                Answer.add(email);
            }
        }
        return Answer;
    }
}
