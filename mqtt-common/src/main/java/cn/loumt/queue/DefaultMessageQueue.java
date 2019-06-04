package cn.loumt.queue;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultMessageQueue<T extends Serializable> implements MessageQueue<T> {


    private final ReentrantLock lock = new ReentrantLock();

    private Long capacity;

    private Long maxCapacity;

    private Deque<T> queue = new LinkedList<T>();

    public DefaultMessageQueue() {
        this(DEFAULT_MAX_CAPACITY);
    }

    public DefaultMessageQueue(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean addFirst(T t) {

        lock.lock();

        try {
            if (maxCapacity == capacity) {
                return false;
            }

            queue.addFirst(t);
            capacity++;
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.lock();

        try {
            return capacity == 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isFull() {
        lock.lock();

        try {
            return capacity == maxCapacity;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Long getCapacity() {
        lock.lock();

        try {
            return capacity;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T getFirst() {
        lock.lock();

        try {
            if (capacity == 0) {
                return null;
            }

            T t = queue.pollFirst();
            capacity--;
            return t;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean addLast(T t) {
        lock.lock();

        try {
            if (capacity == maxCapacity) {
                return false;
            }

            queue.addLast(t);
            capacity++;
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T getLast() {
        lock.lock();

        try {
            if (capacity == 0) {
                return null;
            }

            T t = queue.pollLast();
            capacity--;
            return t;
        } finally {
            lock.unlock();
        }
    }
}
