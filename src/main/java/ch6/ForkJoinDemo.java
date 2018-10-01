package ch6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo
{
    private static class MyForkJoinTask extends RecursiveTask<Integer>
    {
        int start, end;
        int threshold = 10;

        public MyForkJoinTask(int start, int end)
        {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute()
        {
            int result = 0;

            if (Math.abs(start - end) < threshold)
            {
                for (int i = start; i <= end ; i++)
                {
                    result += i;
                }
            }
            else
            {
                int medium = (start + end) / 2;
                RecursiveTask<Integer> task1 = new MyForkJoinTask(start, medium);
                RecursiveTask<Integer> task2 = new MyForkJoinTask(medium + 1, end);

                task1.fork();
                task2.fork();


                result += task1.join();

                result += task2.join();
            }
            return result;
        }
    }

    public static void main(String[] args)
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyForkJoinTask myForkJoinTask = new MyForkJoinTask(1, 1000);
        ForkJoinTask<Integer> resultTask = forkJoinPool.submit(myForkJoinTask);
        try
        {
           int result = resultTask.get();
            System.out.println("sum of 1 to 1000 is :" + result);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }
}
