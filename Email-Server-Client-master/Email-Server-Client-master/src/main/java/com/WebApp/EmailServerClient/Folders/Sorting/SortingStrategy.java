package com.WebApp.EmailServerClient.Folders.Sorting;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public interface SortingStrategy {
    ArrayList<Email> Sort(ArrayList<Email> input);
}
