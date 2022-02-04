package com.WebApp.EmailServerClient.Folders.Searching;


import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search {
    public Map<String, SearchingStrategy> SearchStrategy = new HashMap<>();

    public Search() {
        this.SearchStrategy.put("sender", (emails, value) -> {
            ArrayList<Email> ToSearch = new ArrayList<>();
            for (Email email : emails) {
                if (email.Sender.toLowerCase().contains(value))
                    ToSearch.add(email);
            }
            return ToSearch;
        });

        this.SearchStrategy.put("receiver", (emails, value) -> {
            ArrayList<Email> ToSearch = new ArrayList<>();
            for (Email email : emails) {
                ArrayDeque<String> CurrentEmail = new ArrayDeque<>(email.Receiver);

                while (!CurrentEmail.isEmpty()) {
                    if (CurrentEmail.poll().toLowerCase().contains(value)) {
                        ToSearch.add(email);
                        break;
                    }
                }

            }
            return ToSearch;
        });

        this.SearchStrategy.put("subject", (emails, value) -> {
            ArrayList<Email> ToSearch = new ArrayList<>();
            for (Email email : emails) {
                if (email.Subject.toLowerCase().contains(value))
                    ToSearch.add(email);
            }
            return ToSearch;
        });

        this.SearchStrategy.put("body", (emails, value) -> {
            ArrayList<Email> ToSearch = new ArrayList<>();
            for (Email email : emails) {
                if (email.Body.toLowerCase().contains(value))
                    ToSearch.add(email);
            }
            return ToSearch;
        });

        this.SearchStrategy.put("importance", (emails, value) -> {
            int priority = Integer.parseInt(value) ;
            ArrayList<Email> ToSearch = new ArrayList<>();
            for (Email email : emails) {
                if (email.Priority == priority)
                    ToSearch.add(email);
            }
            return ToSearch;
        });
    }
}
