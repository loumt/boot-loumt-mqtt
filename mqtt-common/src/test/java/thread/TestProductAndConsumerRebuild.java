package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductAndConsumerRebuild {

    public static void main(String[] args) {
        Clerk2 clerk = new Clerk2();
        Produce2 produce = new Produce2(clerk);
        Consumer2 consumer = new Consumer2(clerk);
        new Thread(produce, "线程A").start();
        new Thread(consumer, "线程B").start();
        new Thread(produce, "线程AA").start();
        new Thread(consumer, "线程BB").start();
    }
}


class Clerk2 {
    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void get() {
        lock.lock();

        try{
            while (product >= 1) {
                System.out.println("产品已满!");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void sale() {
        lock.lock();
        try{
            while (product <= 0) {
                System.out.println("缺货....");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signal();
        }finally {
            lock.unlock();
        }
    }
}


class Produce2 implements Runnable {

    private Clerk2 clerk;

    public Produce2(Clerk2 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}


class Consumer2 implements Runnable {

    private Clerk2 clerk;

    public Consumer2(Clerk2 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}