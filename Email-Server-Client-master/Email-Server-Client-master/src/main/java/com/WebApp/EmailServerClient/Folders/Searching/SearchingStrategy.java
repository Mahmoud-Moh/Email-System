package com.WebApp.EmailServerClient.Folders.Searching;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public interface SearchingStrategy {
    ArrayList<Email> Search(ArrayList<Email> input ,String Value);
}
