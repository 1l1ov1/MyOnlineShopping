package com.example.myonlineshopping;

import java.time.Duration;
import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) {
        Double time = 0.5;

        Duration duration = Duration.ofHours(time.longValue()).plusMinutes(
                (long) (time % 1 * 60)
        );
        LocalDateTime now = LocalDateTime.now();


        System.out.println(duration);
        System.out.println(now);
        System.out.println(now.plus(duration));
    }
}
