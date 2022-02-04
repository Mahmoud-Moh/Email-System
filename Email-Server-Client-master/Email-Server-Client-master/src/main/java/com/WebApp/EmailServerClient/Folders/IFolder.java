package com.WebApp.EmailServerClient.Folders;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.*;

public abstract class IFolder {
    private Map<Integer, Email> emailMap = new HashMap<>();
    private PriorityQueue<Email> emailPriorityQueue = new PriorityQueue<>(new EmailComparator());

    public void AddEmail(Email email) {

        emailMap.put(email.ID, email);
        emailPriorityQueue.add(email);
    }

    public Email DeleteEmail(int ID) {
        var DeletedEmail = emailMap.get(ID);
        emailMap.remove(ID);
        emailPriorityQueue.remove(DeletedEmail);
        return DeletedEmail;
    }

    public Map<Integer, Email> getEmailMap() {
        return emailMap;
    }

    public ArrayList<Email> getArrayEmails() {
        PriorityQueue<Email> EmailsQueue = new PriorityQueue<>(getEmailPriorityQueue());
        ArrayList<Email> Emails = new ArrayList<>();
        while (!EmailsQueue.isEmpty()) {
            Emails.add(EmailsQueue.poll());
        }
        return Emails;
    }

    public void setEmailMap(Map<Integer, Email> emailMap) {
        this.emailMap = emailMap;
        setEmailPriorityQueue();
    }

    public PriorityQueue<Email> getEmailPriorityQueue() {
        return emailPriorityQueue;
    }

    private void setEmailPriorityQueue() {
        for (Map.Entry<Integer, Email> email : this.emailMap.entrySet()) {
            this.emailPriorityQueue.add(email.getValue());
        }
    }

    static class EmailComparator implements Comparator<Email> {
        public int compare(Email e1, Email e2) {
            if (e1.Priority < e2.Priority) return 1;
            else if (e1.Priority > e2.Priority) return -1;
            return 0;
        }
    }
}