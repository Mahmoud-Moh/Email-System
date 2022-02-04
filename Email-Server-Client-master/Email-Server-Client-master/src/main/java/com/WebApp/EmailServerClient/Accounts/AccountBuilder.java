package com.WebApp.EmailServerClient.Accounts;

import com.WebApp.EmailServerClient.Constants;
import com.WebApp.EmailServerClient.Controllers.SchemaValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountBuilder {
    private static LoginAccount RealAccount;

    public static LoginAccount BuildAccount(String account) throws JsonProcessingException {
        ObjectMapper om = SchemaValidator.Validate(Constants.SIGNUP_SCHEMA, account);
        RealAccount = om.readValue(account, LoginAccount.class);
        return RealAccount;
    }
}
