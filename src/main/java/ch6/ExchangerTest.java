package ch6;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExchangerTest
{
    public static void main(String[] args)
    {
        final Exchanger<String> stringExchanger = new Exchanger<>();
        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String A = "Hello, I am A!";
                try
                {
                    String B = stringExchanger.exchange(A);

                    System.out.println("B content in A: " + B);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                String B = "Yeah, this is B";
                try
                {
                    String A = stringExchanger.exchange(B);
                    System.out.println("This is A in B: " + A);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
