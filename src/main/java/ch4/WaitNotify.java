package ch4;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WaitNotify
{
    static boolean flag =true;
    static Object lock = new Object();
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");


    public static void main(String[] args)
    {
        for (int i = 0; i < 1; i++)
        {
            new Thread(new Wait(), "Wait thread" + i).start();
        }
        SleepUtils.second(1);
        new Thread(new Notify(), "Notify Thread").start();
    }




    static class Wait implements Runnable
    {
        @Override
        public void run()
        {
            synchronized (lock)
            {
                while (flag)
                {
                    try
                    {
                        System.out.println(Thread.currentThread() + " flag is true, wait @ " + simpleDateFormat.format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread() + " flag is false, running @ " + simpleDateFormat.format(new Date()));
            }
        }
    }

    static  class Notify implements Runnable{
        @Override
        public void run()
        {
            synchronized (lock)
            {
                System.out.println(Thread.currentThread() + " hold lock, notify @ " + simpleDateFormat.format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }

            synchronized (lock)
            {
                System.out.println(Thread.currentThread() + " hold lock again, sleep @ " + simpleDateFormat.format(new Date()));
                SleepUtils.second(5);
            }
        }
    }
}
