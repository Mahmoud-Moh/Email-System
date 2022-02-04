package com.WebApp.EmailServerClient.Controllers;

import com.WebApp.EmailServerClient.Accounts.AccountBuilder;
import com.WebApp.EmailServerClient.Accounts.FullAccount;
import com.WebApp.EmailServerClient.Accounts.LoginAccount;
import com.WebApp.EmailServerClient.Constants;
import com.WebApp.EmailServerClient.Emails.Email;
import com.WebApp.EmailServerClient.Emails.EmailBuilder;
import com.WebApp.EmailServerClient.Folders.Filter.FilterMethod;
import com.WebApp.EmailServerClient.Folders.Searching.Search;
import com.WebApp.EmailServerClient.Folders.Sorting.Sorting;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin()
@RestController
@RequestMapping(Constants.SERVER_PATH)
public class MainController {
    Map<Integer, LoginAccount> LoginData = new HashMap<>();
    Map<Integer, FullAccount> Accounts = new HashMap<>();

    MainController() throws IOException {
        ObjectMapper map = new ObjectMapper();
        TypeReference<Map<Integer, LoginAccount>> tr = new TypeReference<>() {
        };
        FileInputStream fileInputStream = new FileInputStream(Constants.LOGIN_DATA);
        if (new File(Constants.LOGIN_DATA).length() != 0)
            LoginData = map.readValue(fileInputStream, tr);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Result SignUp(@RequestBody String account) throws IOException {
        LoginAccount RealAccount = AccountBuilder.BuildAccount(account);
        Result result = new Result();

        for (Map.Entry<Integer, LoginAccount> Account : LoginData.entrySet()) {
            if (Account.getValue().EmailAddress.equals(RealAccount.EmailAddress)) {
                result.setResult(-2);
                result.setError(Boolean.TRUE);
                return result;
            }
        }

        if (LoginData.keySet().size() != 0)
            RealAccount.ID = Collections.max(LoginData.keySet()) + 1;
        else
            RealAccount.ID = 1;

        LoginData.put(RealAccount.ID, RealAccount);

        ControllerFacade.JsonWriter(Constants.LOGIN_DATA, LoginData);

        String AccountPath = Constants.DATABASE_PATH + RealAccount.ID;
        if (ControllerFacade.InitDatabase(AccountPath)) {
            result.setResult(RealAccount.ID);
            result.setError(Boolean.FALSE);
            ControllerFacade.LogIn(RealAccount.EmailAddress, RealAccount.Pass, LoginData, Accounts);
            return result;
        }
        result.setResult(-1);
        result.setError(Boolean.TRUE);
        ControllerFacade.LogIn(RealAccount.EmailAddress, RealAccount.Pass, LoginData, Accounts);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result LogIn(@RequestParam String email, @RequestParam String password) throws IOException {
        Result result = new Result();
        result.setResult(ControllerFacade.LogIn(email, password, LoginData, Accounts));
        return result;
    }

    @RequestMapping(value = "/mail/{user_id}", method = RequestMethod.POST)
    public Result SendEmail(@RequestBody String email, @PathVariable int user_id) throws IOException {
        Email RealEmail = EmailBuilder.BuildEmail(email);
        Result result = new Result();
        int receiverID, sentID;

        var UserAccountFolder = Accounts.get(user_id).SentFolder;
        if (UserAccountFolder.getEmailMap().keySet().size() != 0)
            RealEmail.ID = Collections.max(UserAccountFolder.getEmailMap().keySet()) + 1;
        else
            RealEmail.ID = 1;

        UserAccountFolder.AddEmail(RealEmail.initClone());
        result.setResult(RealEmail.ID);
        ControllerFacade.JsonWriter(Constants.sent(user_id), UserAccountFolder.getEmailMap());

        while (!RealEmail.Receiver.isEmpty()) {
            for (Map.Entry<Integer, LoginAccount> Account : LoginData.entrySet()) {

                if (Account.getValue().EmailAddress.equals(RealEmail.Receiver.peek())) {
                    receiverID = Account.getValue().ID;
                    ControllerFacade.LogIn(Account.getValue().EmailAddress, Account.getValue().Pass,
                            LoginData, Accounts);

                    var ReceiverAccountFolder = Accounts.get(receiverID).InboxFolder;

                    if (ReceiverAccountFolder.getEmailMap().keySet().size() != 0)
                        sentID = Collections.max(ReceiverAccountFolder.getEmailMap().keySet()) + 1;
                    else
                        sentID = 1;

                    ReceiverAccountFolder.AddEmail(RealEmail.initClone(sentID, RealEmail.Receiver.poll()));
                    ControllerFacade.JsonWriter(Constants.inbox(receiverID), ReceiverAccountFolder.getEmailMap());

                    Accounts.remove(receiverID);
                    break;
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "search/{folder}/{search}/{user_id}", method = RequestMethod.GET)
    public Result SearchEmail(@RequestParam String value, @PathVariable String folder, @PathVariable String search, @PathVariable int user_id) {
        FullAccount UserAccount = Accounts.get(user_id);
        Search SearchEmail = new Search();

        Result result = new Result();
        if (folder.equalsIgnoreCase("inbox")) {
            result.setEmails(SearchEmail.SearchStrategy.get(search).Search(UserAccount.InboxFolder.getArrayEmails(), value.toLowerCase()));
        } else if (folder.equalsIgnoreCase("draft")) {
            result.setEmails(SearchEmail.SearchStrategy.get(search).Search(UserAccount.DraftFolder.getArrayEmails(), value.toLowerCase()));
        } else if (folder.equalsIgnoreCase("sent")) {
            result.setEmails(SearchEmail.SearchStrategy.get(search).Search(UserAccount.SentFolder.getArrayEmails(), value.toLowerCase()));
        } else if (folder.equalsIgnoreCase("trash")) {
            result.setEmails(SearchEmail.SearchStrategy.get(search).Search(UserAccount.TrashFolder.getArrayEmails(), value.toLowerCase()));
        }
        result.setError(Boolean.FALSE);
        return result;
    }

    @RequestMapping(value = "filter/{folder}/{filter}/{user_id}", method = RequestMethod.GET)
    public Result FilterEmail(@PathVariable String folder, @PathVariable String filter, @PathVariable int user_id, @RequestParam String... value) {
        FullAccount UserAccount = Accounts.get(user_id);
        FilterMethod FilterEmail = new FilterMethod();
        Result result = new Result();

        if (folder.equalsIgnoreCase("inbox")) {
            result.setEmails(FilterEmail.FilterStrategy.get(filter).Filter(UserAccount.InboxFolder.getArrayEmails(), value));
        } else if (folder.equalsIgnoreCase("draft")) {
            result.setEmails(FilterEmail.FilterStrategy.get(filter).Filter(UserAccount.DraftFolder.getArrayEmails(), value));
        } else if (folder.equalsIgnoreCase("sent")) {
            result.setEmails(FilterEmail.FilterStrategy.get(filter).Filter(UserAccount.SentFolder.getArrayEmails(), value));
        } else if (folder.equalsIgnoreCase("trash")) {
            result.setEmails(FilterEmail.FilterStrategy.get(filter).Filter(UserAccount.TrashFolder.getArrayEmails(), value));
        }

        result.setError(Boolean.FALSE);
        return result;
    }

    @RequestMapping(value = "sort/{folder}/{sort}/{user_id}", method = RequestMethod.GET)
    public Result ShowEmail(@PathVariable String folder, @PathVariable String sort, @PathVariable int user_id) {
        FullAccount UserAccount = Accounts.get(user_id);
        Sorting SortArray = new Sorting();

        Result result = new Result();
        if (folder.equalsIgnoreCase("inbox")) {
            result.setEmails(SortArray.SortStrategy.get(sort).Sort(UserAccount.InboxFolder.getArrayEmails()));
        } else if (folder.equalsIgnoreCase("draft")) {
            result.setEmails(SortArray.SortStrategy.get(sort).Sort(UserAccount.DraftFolder.getArrayEmails()));
        } else if (folder.equalsIgnoreCase("sent")) {
            result.setEmails(SortArray.SortStrategy.get(sort).Sort(UserAccount.SentFolder.getArrayEmails()));
        } else if (folder.equalsIgnoreCase("trash")) {
            result.setEmails(SortArray.SortStrategy.get(sort).Sort(UserAccount.TrashFolder.getArrayEmails()));
        }
        result.setError(Boolean.FALSE);
        return result;
    }

    @RequestMapping(value = "/draft/{user_id}", method = RequestMethod.POST)
    public void DraftEmail(@RequestBody String email, @PathVariable int user_id) throws IOException {
        Email RealEmail = EmailBuilder.BuildEmail(email);

        var UserAccountFolder = Accounts.get(user_id).DraftFolder;
        UserAccountFolder.AddEmail(RealEmail);

        ControllerFacade.JsonWriter(Constants.DATABASE_PATH + user_id + "/draft/emails.json", UserAccountFolder.getEmailMap());
    }

    @RequestMapping(value = "/delete/{user_id}/{folder}/{mail_id}", method = RequestMethod.DELETE)
    public void DeleteEmail(@PathVariable int user_id, @PathVariable String folder, @PathVariable int mail_id) throws IOException {
        var UserAccount = Accounts.get(user_id);
        UserAccount.strategyMap.get(folder).delete(mail_id);
        ControllerFacade.JsonWriter(Constants.DATABASE_PATH + UserAccount.ID +
                "/trash/emails.json", UserAccount.TrashFolder.getEmailMap());
    }
}
