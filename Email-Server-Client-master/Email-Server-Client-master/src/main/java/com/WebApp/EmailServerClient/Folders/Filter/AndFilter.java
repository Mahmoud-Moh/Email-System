package com.WebApp.EmailServerClient.Folders.Filter;

import com.WebApp.EmailServerClient.Emails.Email;

import java.util.ArrayList;

public class AndFilter implements Filter {
    private Filter Criteria;
    private Filter OtherCriteria;

    public AndFilter(Filter criteria, Filter otherCriteria) {
        Criteria = criteria;
        OtherCriteria = otherCriteria;
    }

    public ArrayList<Email> MeetCriteria(ArrayList<Email> Input, String... Data) {
        ArrayList<Email> FirstCriteria = Criteria.MeetCriteria(Input, Data[0]);
        return OtherCriteria.MeetCriteria(FirstCriteria, Data[1]);
    }
}
