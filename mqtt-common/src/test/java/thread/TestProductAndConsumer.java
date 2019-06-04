package thread;

import java.util.InputMismatchException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Produce produce = new Produce(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(produce, "线程A").start();
        new Thread(consumer, "线程B").start();
        new Thread(produce, "线程AA").start();
        new Thread(consumer, "线程BB").start();
    }
}


class Clerk {
    private int product = 0;

    public synchronized void get() {
        while (product >= 1) {
            System.out.println("产品已满!");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll();
    }

    public synchronized void sale() {
        while (product <= 0) {
            System.out.println("缺货....");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();

    }
}


class Produce implements Runnable {

    private Clerk clerk;

    public Produce(Clerk clerk) {
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


class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}