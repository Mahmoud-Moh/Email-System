package com.WebApp.EmailServerClient.Folders.Sorting;


import java.util.*;

public class Sorting {
    public Map<String, SortingStrategy> SortStrategy = new HashMap<>();

    public Sorting() {
        this.SortStrategy.put("date", emails -> {
            emails.sort((e1, e2) -> e2.date.compareTo(e1.date));
            return emails;
        });

        this.SortStrategy.put("sender", emails -> {
            emails.sort((e1, e2) -> e2.Sender.compareTo(e1.Sender));
            return emails;
        });

        this.SortStrategy.put("receiver", emails -> {
            emails.sort((e1, e2) -> {
                if (e1.Receiver.size() < e2.Receiver.size()) return -1;
                else if (e1.Receiver.size() > e2.Receiver.size()) return 1;
                return 0;
            });
            return emails;
        });

        this.SortStrategy.put("subject", emails -> {
            emails.sort((e1, e2) -> e2.Subject.compareTo(e1.Subject));
            return emails;
        });

        this.SortStrategy.put("body", emails -> {
            emails.sort((e1, e2) -> e2.Body.compareTo(e1.Body));
            return emails;
        });

        this.SortStrategy.put("attachment", emails -> {
            emails.sort((e1, e2) -> {
                if (e1.getAttachment().size() < e2.getAttachment().size()) return -1;
                else if (e1.getAttachment().size() > e2.getAttachment().size()) return 1;
                return 0;
            });
            return emails;
        });

        this.SortStrategy.put("priority", emails -> {
            emails.sort((e1, e2) -> {
                if (e1.Priority < e2.Priority) return 1;
                else if (e1.Priority > e2.Priority) return -1;
                return 0;
            });
            return emails;
        });
    }
}
