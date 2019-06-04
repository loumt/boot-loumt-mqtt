package cn.loumt.queue;

import java.io.Serializable;

public interface MessageQueue<T extends Serializable> {

    public static final long DEFAULT_MAX_CAPACITY = 10000;

    boolean addFirst(T t);

    boolean isEmpty();

    boolean isFull();

    Long getCapacity();

    T getFirst();

    boolean addLast(T t);

    T getLast();

}
