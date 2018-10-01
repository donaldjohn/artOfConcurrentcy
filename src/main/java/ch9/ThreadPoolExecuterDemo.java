package ch9;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecuterDemo
{
    public static void main(String[] args)
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("hahahahhah !!!!fdsflkjkldsfds111~~~~~~~~~~~~~~~~~~~~~~");
            }
        });

        threadPoolExecutor.shutdown();

        threadPoolExecutor.shutdownNow();

        System.out.println();
    }
}
