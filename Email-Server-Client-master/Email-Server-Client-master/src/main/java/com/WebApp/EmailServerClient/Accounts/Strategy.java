package com.WebApp.EmailServerClient.Accounts;

import java.io.IOException;

public interface Strategy {
    void delete(int mailID) throws IOException;
}
