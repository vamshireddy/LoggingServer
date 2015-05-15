
/*
 * This is the Main class which is used to start the reader and writer threads
 */
public class Master {

	public static void main(String[] args)
	{
		/*
		 * Start the reader thread.
		 */
		Reader reader = new Reader();
		Thread readerThread = new Thread(reader);
		readerThread.start();
		/*
		 * Start the writer thread.
		 */
		Writer writer = new Writer();
		Thread writerThread = new Thread(writer);
		writerThread.start();
		/*
		 * Add a shutdown hook
		 */
		shutdownHook hook = new shutdownHook(reader, writer);
		Runtime.getRuntime().addShutdownHook(hook);
		/*
		 * Start Monitor thread
		 */
		Monitor monitor = new Monitor(reader, writer);
		Thread monitorThread = new Thread(monitor);
		monitorThread.start();
		/*
		 * Wait for the threads to finish
		 */
		try {
			readerThread.join();
			writerThread.join();
			monitorThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class shutdownHook extends Thread
{
	Reader reader;
	Writer writer;
	public shutdownHook(Reader r, Writer w)
	{
		reader = r;
		writer = w;
	}
	public void run()
	{
		 System.out.println("Shutdown hook\n");
	     reader.cleanup();
	     writer.cleanup();
	}
}