package com.example;

import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {
    private Integer id;

    public Customer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(getRandomNumber(1, 4));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Покупателя номер: "+id+" обслуживает кассир");
    }
}
