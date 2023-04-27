package com.example.p4v2;

import java.sql.Timestamp;
import java.util.Date;

public class Payment {
    private Timestamp created_at;
    private Float amount;

    public Payment(Date date, float amount) {
        this.created_at = new Timestamp(date.getTime());
        this.amount = amount;
    }
}
