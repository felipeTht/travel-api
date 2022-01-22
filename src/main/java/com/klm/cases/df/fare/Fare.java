package com.klm.cases.df.fare;


import lombok.Data;

@Data
public class Fare {

    double amount;
    Currency currency;
    String origin, destination;

}