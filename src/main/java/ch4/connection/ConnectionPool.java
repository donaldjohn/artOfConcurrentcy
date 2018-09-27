package ch4.connection;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool
{
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize)
    {
        if (initialSize > 0)
        {
            for (int i = 0; i < initialSize; i++)
            {
                pool.addLast(ConnectionDrivier.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection)
    {
        if(connection != null)
        {
            synchronized(pool)
            {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }


    public Connection fetchConnection(long milli) throws InterruptedException
    {
        synchronized(pool)
        {
            //永不超时
            if(milli <= 0)
            {
                synchronized (pool)
                {
                    while (pool.isEmpty())
                    {
                        pool.wait();
                    }

                    return pool.removeFirst();
                }
            }
            else
            {
                Long now = System.currentTimeMillis();
                long future = now + milli;
                long remaining = milli;
                while ((future - System.currentTimeMillis()) > 0 && pool.isEmpty())
                {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }

                Connection result = null;
                if(!pool.isEmpty())
                {
                    result = pool.removeFirst();
                }

                return result;
            }
        }
    }
}
