package ch1;

import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;

public class ConcurrencyTest
{
    private static long count = 10000000l;

    public static void main(String[] args) throws InterruptedException
    {
        concurrency();
        serial();
    }

    public static void concurrency() throws InterruptedException
    {
        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                int a = 0;
                for (int i = 0; i < count; i++)
                {
                    a+=5;
                }
            }
        });

        thread.start();
        int b = 0;
        for (int i = 0; i < count; i++)
        {
            b--;
        }

        thread.join();

        long time = System.currentTimeMillis() - start;
        System.out.println("Cocurrency: " + time + "ms, b=" + b);
    }

    public static void serial()
    {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < count; i++)
        {
            a+=5;
        }

        int b = 0;
        for (int i = 0; i < count; i++)
        {
            b--;
        }

        long time = System.currentTimeMillis() - start;

        System.out.println("Serial: " + time + "ms, b=" + b + ", a=" + a);
    }
}
