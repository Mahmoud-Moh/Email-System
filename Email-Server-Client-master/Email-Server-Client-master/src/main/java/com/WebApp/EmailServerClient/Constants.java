package com.WebApp.EmailServerClient;

public class Constants {
    public static final String SERVER_PATH = "/email";

    public static final String DATABASE_PATH = "src/main/resources/Database/";

    public static final String SIGNUP_SCHEMA = "Database/signup.schema.json";
    public static final String EMAIL_SCHEMA = "Database/email.schema.json";
    public static final String LOGIN_SCHEMA = "Database/login.schema.json";

    public static final String LOGIN_DATA = "src/main/resources/Database/LoginData.json";

    public static String inbox(int ID) {
        return DATABASE_PATH + ID + "/inbox/emails.json";
    }

    public static String sent(int ID) {
        return DATABASE_PATH + ID + "/sent/emails.json";
    }

    public static String draft(int ID) {
        return DATABASE_PATH + ID + "/draft/emails.json";
    }

    public static String trash(int ID) {
        return DATABASE_PATH + ID + "/trash/emails.json";
    }

    public static String custom(int ID, String Custom) {
        return DATABASE_PATH + ID + "/" + Custom + "/emails.json";
    }
}
