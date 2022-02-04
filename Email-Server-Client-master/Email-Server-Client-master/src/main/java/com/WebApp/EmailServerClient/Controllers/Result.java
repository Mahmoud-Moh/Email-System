package com.WebApp.EmailServerClient.Controllers;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public class Result {
    private String message = "";
    private Boolean error = false;
    private int result = 0;
    private ArrayList<Email> emails = new ArrayList<>();

    public Result(String message, Boolean error, int result, ArrayList<Email> emails) {
        this.message = message;
        this.error = error;
        this.result = result;
        this.emails = emails;
    }

    public Result() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArrayList<Email> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<Email> emails) {
        this.emails = emails;
    }

    public String written() {
        return "Result: \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t---> " +
                "Message = " + message + "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t---> " +
                "Error = " + error + "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t---> " +
                "Final Result = " + result + "\n";
    }
}
