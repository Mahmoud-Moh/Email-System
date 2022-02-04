package com.WebApp.EmailServerClient.Folders.Filter;

import java.util.HashMap;
import java.util.Map;

public class FilterMethod {
    public Map<String, FilterStrategy> FilterStrategy = new HashMap<>();
    public Filter SenderFilter = new SenderFilter();
    public Filter SubjectFilter = new SubjectFilter();
    public Filter AndFilter = new AndFilter(SenderFilter, SubjectFilter);

    public FilterMethod() {
        this.FilterStrategy.put("sender", (emails, value) -> SenderFilter.MeetCriteria(emails, value));
        this.FilterStrategy.put("subject", (emails, value) -> SubjectFilter.MeetCriteria(emails, value));
        this.FilterStrategy.put("sender&subject", (emails, value) -> AndFilter.MeetCriteria(emails, value));
    }
}
