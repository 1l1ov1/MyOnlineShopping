package com.example.myonlineshopping;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        LocalDate specificDate = LocalDate.now();
        System.out.println(specificDate);
        Date oldStyleDate = Date.from(specificDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(oldStyleDate);
    }
}
