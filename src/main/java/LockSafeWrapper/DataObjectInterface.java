package LockSafeWrapper;

/**
 * Created by abhilashbss on 2/10/21.
 */
public interface DataObjectInterface<T> {
    public String getFilePath();
    public T get();
    public void set(T data);
    public void execute() throws Exception;
}
