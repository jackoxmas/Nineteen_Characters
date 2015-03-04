package src;

public interface Function<T, F> {
	public T apply(F foo);
}
//A simple copy of Java 8s Function interface(more or less)
//Used to pass in a function to the chatbox