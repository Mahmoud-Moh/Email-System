package com.WebApp.EmailServerClient.Folders.Filter;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public interface FilterStrategy {
    ArrayList<Email> Filter(ArrayList<Email> input, String... Value);
}
