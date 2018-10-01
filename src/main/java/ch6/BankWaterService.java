package ch6;

import java.util.Map;
import java.util.concurrent.*;

public class BankWaterService implements Runnable
{

    CyclicBarrier cyclicBarrier = new CyclicBarrier(4, this);
    private ConcurrentHashMap<String, Integer> resultHashMap = new ConcurrentHashMap<>();

    Executor executor = Executors.newFixedThreadPool(4);

    private void count()
    {
        for (int i = 0; i < 4; i++)
        {
            executor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    resultHashMap.put(Thread.currentThread().getName(), 1);

                    try
                    {
                        cyclicBarrier.await();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void run()
    {
        int result = 0;
        for (Map.Entry<String, Integer> entry: resultHashMap.entrySet()
             )
        {
            result += entry.getValue();
        }

        System.out.println("The total number is : " + result);
    }

    public static void main(String[] args)
    {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
