package LockSafeWrapper;

import java.sql.Timestamp;

/**
 * Created by abhilashbss on 2/10/21.
 */
public class LockDataObjectWrapper {

    public DataObjectInterface<String> dataObject;
    public boolean writeLock;
    public boolean readLock;
    public RemoteLockHandler remoteLockHandler;
    public String serviceName;

    public String getSafe() throws Exception{
        if (this.isReadLock() || this.remoteLockHandler.isRemoteReadLock(this.getDataObject().getFilePath())){
            throw new Exception("Data is locked not available for read");
        }
        this.setReadLock(true);
        this.remoteLockHandler.lockObject("read",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));

        String data = this.dataObject.get();

        this.setReadLock(false);
        this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());

        return data;
    }

    public void setSafe(String data) throws Exception{
        if (this.isReadLock() || this.isWriteLock()  || this.remoteLockHandler.isRemoteReadLock(this.getDataObject().getFilePath())
                || this.remoteLockHandler.isRemoteWriteLock(this.getDataObject().getFilePath())){
            throw new Exception("Data is locked not available for write");
        }
        this.setReadLock(true);
        this.setWriteLock(true);
        this.remoteLockHandler.lockObject("read",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));
        this.remoteLockHandler.lockObject("write",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));

        this.dataObject.set(data);

        this.setReadLock(false);
        this.setWriteLock(false);
        this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());
        this.remoteLockHandler.releaseObject("write",this.getDataObject().getFilePath());
    }

    public void executeSafe() throws Exception{
        if (this.isReadLock() || this.isWriteLock()  || this.remoteLockHandler.isRemoteReadLock(this.getDataObject().getFilePath())
                || this.remoteLockHandler.isRemoteWriteLock(this.getDataObject().getFilePath())){
            throw new Exception("Data is locked not available for write");
        }
        this.setReadLock(true);
        this.setWriteLock(true);
        this.remoteLockHandler.lockObject("read",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));
        this.remoteLockHandler.lockObject("write",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));

        this.dataObject.execute();

        this.setReadLock(false);
        this.setWriteLock(false);
        this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());
        this.remoteLockHandler.releaseObject("write",this.getDataObject().getFilePath());
    }

    public DataObjectInterface getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObjectInterface dataObject) {
        this.dataObject = dataObject;
    }

    public boolean isWriteLock() {
        return writeLock;
    }

    public void setWriteLock(boolean writeLock) {
        this.writeLock = writeLock;
    }

    public boolean isReadLock() {
        return readLock;
    }

    public void setReadLock(boolean readLock) {
        this.readLock = readLock;
    }

    public RemoteLockHandler getRemoteLockHandler() {
        return remoteLockHandler;
    }

    public void setRemoteLockHandler(RemoteLockHandler remoteLockHandler) {
        this.remoteLockHandler = remoteLockHandler;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
