package ch5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T>
{
    private Object[] items;
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size)
    {
        this.items = new Object[size];
    }

    public void add(Object obj)
    {
        lock.lock();
        try
        {
            while (count == items.length) //满, 防止过早通知
            {
                notFull.await();
            }
            items[addIndex] = obj;
            if (++addIndex == items.length)
            {
                addIndex = 0;
            }
            count++;
            notEmpty.signal();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } finally
        {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException
    {
        lock.lock();
        try
        {
            while (items.length == 0) //如果没有内容
            {
                notEmpty.await();
            }
            Object x = items[removeIndex];
            if (++removeIndex == items.length)
            {
                removeIndex = 0;
            }
            count--;
            notFull.signal();
            return (T) x;
        }
        finally
        {
            lock.unlock();
        }
    }
}
