package com.WebApp.EmailServerClient.Emails;

public interface Clone<T> {
    T initClone(int mailID, String Receiver);

    T initClone();
}
