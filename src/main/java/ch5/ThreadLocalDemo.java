package ch5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLocalDemo
{
    private ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    private static ThreadLocal<String> localString = new ThreadLocal<String>(){
        @Override
        protected String initialValue()
        {
            return "theInitialValue";
        }
    };

    private static class MyRunner implements Runnable
    {
        @Override
        public void run()
        {
            System.out.println("Init Value from thread " + Thread.currentThread().getName() + ": " + localString.get());
            String randomString = "randNum" + Math.random()*100;
            System.out.println("Random string is " + Thread.currentThread().getName()  + ": "  + randomString);
            localString.set(randomString);
            TimeUnit.SECONDS.toSeconds(2);
            System.out.println("Random string after sleep is " + Thread.currentThread().getName() + ": "  + randomString);
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        Thread t1 = new Thread(new MyRunner(), "Thread1");
        t1.start();


        Thread t2 = new Thread(new MyRunner(), "Thread2");
        t2.start();

        t1.join();
        t2.join();
    }
}
