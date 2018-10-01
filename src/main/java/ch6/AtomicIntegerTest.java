package ch6;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicIntegerTest
{
    public static void main(String[] args)
    {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.get());

        int[] value = {1, 2};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(value);

        Object o = new Object();
        AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(o, true);


    }
}
