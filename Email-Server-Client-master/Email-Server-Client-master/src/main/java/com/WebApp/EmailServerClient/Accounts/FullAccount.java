package com.WebApp.EmailServerClient.Accounts;

import com.WebApp.EmailServerClient.Constants;
import com.WebApp.EmailServerClient.Controllers.ControllerFacade;
import com.WebApp.EmailServerClient.Emails.Email;
import com.WebApp.EmailServerClient.Folders.FolderFactory;
import com.WebApp.EmailServerClient.Folders.IFolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FullAccount extends Account {
    public IFolder SentFolder = FolderFactory.GetFolder("sent");
    public IFolder InboxFolder = FolderFactory.GetFolder("inbox");
    public IFolder DraftFolder = FolderFactory.GetFolder("draft");
    public IFolder TrashFolder = FolderFactory.GetFolder("trash");
    public Map<String, Strategy> strategyMap = new HashMap<>();
    public ArrayList<Contact> Contacts = new ArrayList<>();

    public void AddContact(String Email, String Name) {
        Contacts.add(new Contact(Name, Email));
    }

    public void DeleteContact(Contact contact) {
        Contacts.remove(contact);
    }

    public FullAccount() {
        this.strategyMap.put("inbox", mailID -> {
            Email email = InboxFolder.DeleteEmail(mailID);
            ControllerFacade.JsonWriter(Constants.inbox(ID), InboxFolder.getEmailMap());
            TrashFolder.AddEmail(email);
        });

        this.strategyMap.put("sent", mailID -> {
            Email email = SentFolder.DeleteEmail(mailID);
            ControllerFacade.JsonWriter(Constants.sent(ID), SentFolder.getEmailMap());
            TrashFolder.AddEmail(email);
        });

        this.strategyMap.put("draft", mailID -> {
            Email email = DraftFolder.DeleteEmail(mailID);
            ControllerFacade.JsonWriter(Constants.draft(ID), DraftFolder.getEmailMap());
            TrashFolder.AddEmail(email);
        });

        this.strategyMap.put("trash", mailID -> TrashFolder.DeleteEmail(mailID));
    }
}
