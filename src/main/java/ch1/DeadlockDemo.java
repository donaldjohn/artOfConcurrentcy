package ch1;

public class DeadlockDemo
{
    String A = "lockA";
    String B = "lockB";


    public static void main(String[] args)
    {
        new DeadlockDemo().deadlock();
    }
    private void deadlock()
    {
        Thread threadA = new Thread(new Runnable()
        {
            public void run()
            {
                synchronized (A)
                {
                    try
                    {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    synchronized (B)
                    {
                        System.out.println("locking B");
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable()
        {
            public void run()
            {
                synchronized(B)
                {
                    synchronized (A)
                    {
                        System.out.println("Locking A");
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
