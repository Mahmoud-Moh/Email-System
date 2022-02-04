package com.WebApp.EmailServerClient.Accounts;

public abstract class Account {
    public String UserName, Pass, EmailAddress;
    public int ID;

    @Override
    public String toString() {
        return "Account{" +
                "UserName='" + UserName + '\'' +
                ", Pass='" + Pass + '\'' +
                ", ID='" + ID + '\'' +
                ", EmailAddress='" + EmailAddress + '\'' +
                '}';
    }
}
