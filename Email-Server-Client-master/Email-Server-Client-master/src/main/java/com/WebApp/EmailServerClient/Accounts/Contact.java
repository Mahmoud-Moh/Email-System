package com.WebApp.EmailServerClient.Accounts;

import java.util.ArrayList;
import java.util.Arrays;

public class Contact {
    public String Name;
    public ArrayList<String> EmailAddress = new ArrayList<>();

    public Contact(String... data) {
        Name = data.length > 0 ? data[0] : null;
        EmailAddress.add(data.length > 1 ? data[1] : null);
        EmailAddress.addAll(Arrays.asList(data).subList(2, data.length));
    }
}
