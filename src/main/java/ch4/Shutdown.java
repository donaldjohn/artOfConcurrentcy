package ch4;

public class Shutdown
{
    public static void main(String[] args)
    {
        Thread thread1 = new Thread(new Runner(), "thread 1");
        thread1.start();
        System.out.println("Started  thread1");
        SleepUtils.second(3);
        thread1.interrupt();
        System.out.println("Interrupted thread1");

        Runner runnerTwo = new Runner();
        Thread thread2 = new Thread(runnerTwo, "thread 2");
        thread2.start();
        System.out.println("Started  thread2");
        SleepUtils.second(3);
        runnerTwo.cancel();
        System.out.println("Canceled thread2");
    }

}

class Runner implements Runnable{
    private long i;
    private volatile boolean on = true;

    @Override
    public void run()
    {
        while (on && !Thread.currentThread().isInterrupted())
        {
            i++;
        }
        System.out.println("Count i = " + i);
    }

    public void cancel()
    {
        on = false;
    }
}
