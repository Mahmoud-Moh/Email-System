package com.WebApp.EmailServerClient.Folders.Filter;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public interface Filter {
    ArrayList<Email> MeetCriteria(ArrayList<Email> Input, String... Sender);
}
