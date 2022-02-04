package com.WebApp.EmailServerClient.Controllers;

import com.WebApp.EmailServerClient.Accounts.Account;
import com.WebApp.EmailServerClient.Accounts.FullAccount;
import com.WebApp.EmailServerClient.Accounts.LoginAccount;
import com.WebApp.EmailServerClient.Constants;
import com.WebApp.EmailServerClient.Emails.Email;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ControllerFacade {
    public static int LogIn(String email, String password, Map<Integer, LoginAccount> LoginData, Map<Integer, FullAccount> Accounts) throws IOException {
        boolean exist = false;
        int id = -1;
        String userName = "";
        LoginAccount RealAccount = new LoginAccount();
        RealAccount.EmailAddress = email;
        RealAccount.Pass = password;

        for (Map.Entry<Integer, LoginAccount> Account : LoginData.entrySet()) {
            if (Account.getValue().EmailAddress.equals(RealAccount.EmailAddress) && Account.getValue().Pass.equals(RealAccount.Pass)) {
                exist = true;
                id = Account.getKey();
                userName = Account.getValue().UserName;
                break;
            }
        }
        if (!exist) return id;

        ObjectMapper map = new ObjectMapper();
        TypeReference<Map<Integer, Email>> tr = new TypeReference<>() {
        };
        FullAccount loginAccount = new FullAccount();

        if (new File(Constants.inbox(id)).length() != 0) {
            loginAccount.InboxFolder.setEmailMap(
                    map.readValue(new FileInputStream(Constants.inbox(id)), tr));
        }
        if (new File(Constants.sent(id)).length() != 0) {
            loginAccount.SentFolder.setEmailMap(
                    map.readValue(new FileInputStream(Constants.sent(id)), tr));
        }
        if (new File(Constants.trash(id)).length() != 0) {
            loginAccount.TrashFolder.setEmailMap(
                    map.readValue(new FileInputStream(Constants.trash(id)), tr));
        }
        if (new File(Constants.draft(id)).length() != 0) {
            loginAccount.DraftFolder.setEmailMap(
                    map.readValue(new FileInputStream(Constants.draft(id)), tr));
        }

        loginAccount.EmailAddress = email;
        loginAccount.Pass = password;
        loginAccount.ID = id;
        loginAccount.UserName = userName;
        Accounts.put(id, loginAccount);
        return id;
    }

    public static Boolean InitDatabase(String AccountPath) throws IOException {
        boolean DirDatabase, DirInbox, DirSent, DirDraft, DirTrash;
        boolean FileInbox, FileSent, FileDraft, FileTrash;
        File Creating = new File(AccountPath);
        DirDatabase = Creating.mkdir();
        Creating = new File(AccountPath + "/inbox");
        FileInbox = Creating.mkdir();
        File inbox = new File(AccountPath + "/inbox/emails.json");
        DirInbox = inbox.createNewFile();
        Creating = new File(AccountPath + "/draft");
        DirDraft = Creating.mkdir();
        File draft = new File(AccountPath + "/draft/emails.json");
        FileDraft = draft.createNewFile();
        Creating = new File(AccountPath + "/trash");
        DirTrash = Creating.mkdir();
        File trash = new File(AccountPath + "/trash/emails.json");
        FileTrash = trash.createNewFile();
        Creating = new File(AccountPath + "/sent");
        DirSent = Creating.mkdir();
        File sent = new File(AccountPath + "/sent/emails.json");
        FileSent = sent.createNewFile();
        return DirDatabase && DirDraft && DirInbox && DirTrash && DirSent &&
                FileDraft && FileInbox && FileSent && FileTrash;
    }

    public static void JsonWriter(String path, Object ToSave) throws IOException {
        ObjectMapper map = new ObjectMapper();
        FileOutputStream File = new FileOutputStream(path);
        map.writeValue(File, ToSave);
    }
}
