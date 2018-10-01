package ch10;

import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorFrameWorkDemo
{
    public static void main(String[] args)
    {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService CachedThreadPool = Executors.newCachedThreadPool();

        Timer timer = new Timer(true);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


    }
}
