package io.itpl.microservice.mongo.document;

import io.itpl.microservice.common.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AggregationDocument {
    private static final Logger logger = LoggerFactory.getLogger(AggregationDocument.class);
    public static final String PROPERTY_COUNT = "count";
    public static final String PROPERTY_AMOUNT = "amount";
    public static final String PROPERTY_ID = "_id";
    public static final int MAX_PAGE_SIZE = 2048;
    public static final String DATE_FORMAT_ISO_WITHOUT_TIME = "ISO-Date w/o time";
    public static final String DATE_FORMAT_DEFAULT = DATE_FORMAT_ISO_WITHOUT_TIME;
    private int count;
    private double amount;
    public static List<KeyValuePair> dateFormats;

    static {
        dateFormats = new ArrayList<>();
        dateFormats.add(KeyValuePair.of("ISO-Date w/o time","%Y-%m-%d"));
        dateFormats.add(KeyValuePair.of("ISO Date with Time","%Y-%m-%d %H:%M:%S"));
        dateFormats.add(KeyValuePair.of("Day of Month (i.e. 01-31)","%m"));
        dateFormats.add(KeyValuePair.of("Date w/o time","%d-%m-%Y"));
        dateFormats.add(KeyValuePair.of("Date with Time","%d-%m-%Y %H:%M:%S"));
        dateFormats.add(KeyValuePair.of("Western Date w/o time","%m-%d-%Y"));
        dateFormats.add(KeyValuePair.of("Western Date with Time","%m-%d-%Y %H:%M:%S"));
        dateFormats.add(KeyValuePair.of("Month-Year","%m-%Y"));
        dateFormats.add(KeyValuePair.of("Year Only","%Y"));
        dateFormats.add(KeyValuePair.of("ISO Week of Year (i.e.01-53)","%V"));

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<KeyValuePair> getDateFormats() {
        return dateFormats;
    }

    public void setDateFormats(List<KeyValuePair> dateFormats) {
        this.dateFormats = dateFormats;
    }
}
