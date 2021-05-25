package com.example;

import java.util.concurrent.*;

public class Main {
    private static final Integer QUEUE_CAPACITY = 50;
    private static final Integer CORE_POOL_SIZE = 10;
    private static final Integer MAX_POOL_SIZE = 20;

    public static void main(String[] args) {
        Integer customerCounter = 0;
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<>(QUEUE_CAPACITY);

        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE, 4000, TimeUnit.MILLISECONDS, blockingQueue);

        executor.setRejectedExecutionHandler((r, executor1) -> {
            System.out.println("У пользователя номер: "+((Customer) r).getId()+" произошла ошибка обслуживания");
            System.out.println("Обработка ошибки...");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Повторное добавление пользователя номер: " + ((Customer) r).getId()+" в очередь");
            executor1.execute(r);
        });

        executor.prestartAllCoreThreads();
        while (true) {
            customerCounter++;
            System.out.println("Пользователь номер : "+customerCounter+" заходит в очередь");
            executor.execute(new Customer(customerCounter));

            if (customerCounter == 100) { // выставление лимита на общее количество покупателей
                break;
            }
        }
    }
}
