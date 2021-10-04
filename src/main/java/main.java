import LockSafeWrapper.DBPersister;
import LockSafeWrapper.RemoteLockHandler;
import java.util.concurrent.TimeUnit;


/**
 * Created by abhilashbss on 2/10/21.
 */
public class main {
    public static void main(String[] args) throws Exception{
        // Set the DB details for concurrency control in remote transactions
        DBPersister dbPersister = new DBPersister();
        dbPersister.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        dbPersister.setPass("abc");
        dbPersister.setUser("postgres");
        dbPersister.setTableName("concurrency_control");

        RemoteLockHandler lockHandler = new RemoteLockHandler();
        lockHandler.setDbPersister(dbPersister);

        DAOOperationalThread thread1 = new DAOOperationalThread();
        DAOOperationalThread thread2 = new DAOOperationalThread();

        thread1.setServiceName("thread-1");
        thread1.setDbPersister(dbPersister);
        thread1.setLockHandler(lockHandler);

        thread2.setServiceName("thread-2");
        thread2.setDbPersister(dbPersister);
        thread2.setLockHandler(lockHandler);

        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        thread2.start();

    }
}
