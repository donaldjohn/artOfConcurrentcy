package ch6;

import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;

public class HashMapTest
{
    @Test
    public void HashMapConcurrentTest() throws InterruptedException
    {
        final HashMap<String, String> map = new HashMap<>(2);

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < 100000; i++)
                {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    },"ftf" + i).start();
                }
            }
        });

        t.start();
        t.join();

    }
}
