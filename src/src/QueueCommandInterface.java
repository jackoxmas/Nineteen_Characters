package src;

public interface QueueCommandInterface<T> {
	
	public void enqueue(T command);
	public void sendInterrupt();

}
