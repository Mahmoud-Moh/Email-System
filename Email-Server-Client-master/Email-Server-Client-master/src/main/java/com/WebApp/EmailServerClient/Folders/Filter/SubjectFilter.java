package com.WebApp.EmailServerClient.Folders.Filter;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public class SubjectFilter implements Filter {
    @Override
    public ArrayList<Email> MeetCriteria(ArrayList<Email> Input, String... Subject) {
        ArrayList<Email> Answer = new ArrayList<>();
        for (Email email : Input) {
            if (email.Subject.equalsIgnoreCase(Subject[0])) {
                Answer.add(email);
            }
        }
        return Answer;
    }
}
