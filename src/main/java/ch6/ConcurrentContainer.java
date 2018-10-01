package ch6;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;

public class ConcurrentContainer
{
    public static void main(String[] args)
    {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

        concurrentLinkedQueue.offer("hello");

        DelayQueue delayQueue = new DelayQueue();


        String str1 = "hello";
        String str2 = "world";
        System.out.println(str1 == (str1 = str2)?"True":"False");
        System.out.println(str1);

    }
}
