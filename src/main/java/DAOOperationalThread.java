import DataObjects.FileDataObject;
import DataObjects.FileHandler;
import LockSafeWrapper.DBPersister;
import LockSafeWrapper.LockDataObjectWrapper;
import LockSafeWrapper.RemoteLockHandler;

/**
 * Created by abhilashbss on 3/10/21.
 */
public class DAOOperationalThread extends Thread {

    public String serviceName;
    public DBPersister dbPersister;
    public RemoteLockHandler lockHandler;
    public LockDataObjectWrapper lockDataObjectWrapper;

    public void run(){

        // Create custom Data Objects implementing DataObjectInterface
        FileDataObject fileDAO = new FileDataObject();
        fileDAO.setHandler(new FileHandler());
        fileDAO.setFilePath("/home/abhilashbss/IdeaProjects/concurrency_control/src/main/resources/test.txt");

        // Wrap the data object for lock based safer usage
        this.lockDataObjectWrapper.setDataObject(fileDAO);
        this.lockDataObjectWrapper.setServiceName(this.getServiceName());
        this.lockDataObjectWrapper.setRemoteLockHandler(lockHandler);

        try{
            this.lockDataObjectWrapper.executeSafe();
        }
        catch(Exception e){
            System.out.println("Service : " + serviceName + " Exception : " + e);
        }

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public DBPersister getDbPersister() {
        return dbPersister;
    }

    public void setDbPersister(DBPersister dbPersister) {
        this.dbPersister = dbPersister;
    }

    public RemoteLockHandler getLockHandler() {
        return lockHandler;
    }

    public void setLockHandler(RemoteLockHandler lockHandler) {
        this.lockHandler = lockHandler;
    }

    public LockDataObjectWrapper getLockDataObjectWrapper() {
        return lockDataObjectWrapper;
    }

    public void setLockDataObjectWrapper(LockDataObjectWrapper lockDataObjectWrapper) {
        this.lockDataObjectWrapper = lockDataObjectWrapper;
    }

}
