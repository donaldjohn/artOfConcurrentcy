package ch4.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest
{
    static ConnectionPool connectionPool = new ConnectionPool(10);
    static CountDownLatch startLatch = new CountDownLatch(1);
    static CountDownLatch endLatch;

    public static void main(String[] args) throws InterruptedException
    {
        int threadCount = 15;
        endLatch = new CountDownLatch(threadCount);

        int count = 20;
        AtomicInteger gotCount = new AtomicInteger();
        AtomicInteger notGotCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++)
        {
            Thread runner = new Thread(new ConnectionRunner(count, gotCount, notGotCount), "connectionRunner" + i);
            runner.start();
        }




        startLatch.countDown();//启动所有线程
        endLatch.await(); //主线程等待所有线程终止

        System.out.println("Total invoke:" + (threadCount * count));
        System.out.println("Got conneciton count:" + gotCount);
        System.out.println("Not got connection count:" + notGotCount);
    }

    static class ConnectionRunner implements Runnable
    {
        int count;
        AtomicInteger gotCount;
        AtomicInteger notGotCount;

        public ConnectionRunner(int count, AtomicInteger gotCount, AtomicInteger notGotCount)
        {
            this.count = count;
            this.gotCount = gotCount;
            this.notGotCount = notGotCount;
        }

        @Override
        public void run()
        {
            try
            {
                startLatch.await();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            while (count > 0)
            {
                try
                {
                    Connection conn = connectionPool.fetchConnection(100);
                    if (conn == null)
                    {
                        notGotCount.incrementAndGet();
                    }
                    else
                    {
                        try
                        {
                            conn.createStatement();
                            conn.commit();
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                        finally
                        {
                            connectionPool.releaseConnection(conn);
                            gotCount.incrementAndGet();
                        }
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    count--;
                }
            }
            endLatch.countDown();
        }
    }
}
