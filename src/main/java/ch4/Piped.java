package ch4;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped
{


    public static void main(String[] args) throws IOException
    {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        pipedReader.connect(pipedWriter);

        Thread printThread = new Thread(new Print(pipedReader), "print thread");
        printThread.start();
        int recieve = 0;
        while ((recieve = System.in.read()) != -1)
        {
            pipedWriter.write(recieve);
        }
    }



    static class Print implements Runnable
    {
        private PipedReader in;

        public Print(PipedReader in)
        {
            this.in = in;
        }

        @Override
        public void run()
        {
            int recieve = 0;
            try
            {
                while (-1 != (recieve = in.read()))
                {
                    System.out.print((char) recieve);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
