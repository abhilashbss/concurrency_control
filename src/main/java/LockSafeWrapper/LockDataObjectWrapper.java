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
        //This prevents unnecessary DB calls
        if (this.isReadLock()){
            throw new Exception("LOCAL LOCK : Data is locked not available for read");
        }

        if (this.remoteLockHandler.isRemoteReadLock(this.getDataObject().getFilePath())){
            throw new Exception("REMOTE LOCK : Data is locked not available for read");
        }
        this.setReadLock();
        this.remoteLockHandler.lockObject("read",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));

        if(this.remoteLockHandler.isRemoteMultipleReadLock(this.getDataObject().getFilePath())){
            this.releaseReadLock();
            this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());
            throw new Exception("Data is locked not available for read");
        }

        String data = this.dataObject.get();

        this.releaseReadLock();
        this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());

        return data;
    }

    public void setSafe(String data) throws Exception{
        //This prevents unnecessary DB calls
        if (this.isReadLock() || this.isWriteLock()){
            throw new Exception("LOCAL LOCK : Data is locked not available for write");
        }

        if (this.remoteLockHandler.isRemoteReadLock(this.getDataObject().getFilePath())
                || this.remoteLockHandler.isRemoteWriteLock(this.getDataObject().getFilePath())){
            throw new Exception("REMOTE LOCK : Data is locked not available for write");
        }

        this.setReadLock();
        this.setWriteLock();
        this.remoteLockHandler.lockObject("read",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));
        this.remoteLockHandler.lockObject("write",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));

        if(this.remoteLockHandler.isRemoteMultipleReadLock(this.getDataObject().getFilePath())
                || this.remoteLockHandler.isRemoteMultipleWriteLock(this.getDataObject().getFilePath())){
            this.releaseReadLock();
            this.releaseWriteLock();
            this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());
            this.remoteLockHandler.releaseObject("write",this.getDataObject().getFilePath());
            throw new Exception("REMOTE LOCK :Data is locked not available for write");
        }

        this.dataObject.set(data);

        this.releaseReadLock();
        this.releaseWriteLock();
        this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());
        this.remoteLockHandler.releaseObject("write",this.getDataObject().getFilePath());
    }

    public void executeSafe() throws Exception{
        //This prevents unnecessary DB calls
        if (this.isReadLock() || this.isWriteLock() ){
            throw new Exception("LOCAL LOCK : Data is locked not available for write");
        }

        if(this.remoteLockHandler.isRemoteReadLock(this.getDataObject().getFilePath())
                || this.remoteLockHandler.isRemoteWriteLock(this.getDataObject().getFilePath())){
            throw new Exception("REMOTE LOCK : Data is locked not available for write");
        }
        this.setReadLock();
        this.setWriteLock();
        this.remoteLockHandler.lockObject("read",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));
        this.remoteLockHandler.lockObject("write",this.getDataObject().getFilePath(),this.getServiceName(),new Timestamp(System.currentTimeMillis()));

        if(this.remoteLockHandler.isRemoteMultipleReadLock(this.getDataObject().getFilePath())
                || this.remoteLockHandler.isRemoteMultipleWriteLock(this.getDataObject().getFilePath())){
            this.releaseReadLock();
            this.releaseWriteLock();
            this.remoteLockHandler.releaseObject("read",this.getDataObject().getFilePath());
            this.remoteLockHandler.releaseObject("write",this.getDataObject().getFilePath());
            throw new Exception("REMOTE LOCK : Data is locked not available for write");
        }

        this.dataObject.execute();

        this.releaseReadLock();
        this.releaseWriteLock();
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

    public boolean isReadLock() {
        return readLock;
    }

    public synchronized void setWriteLock() throws Exception{
        if(!this.writeLock){
            this.writeLock = true;
        }
        else{
            throw new Exception("LOCAL LOCK : Data trying to be accessed is locked");
        }
    }

    public synchronized void releaseWriteLock(){
        this.writeLock = false;
    }

    public synchronized void setReadLock() throws Exception {
        if(!this.readLock) {
            this.readLock = true;
        }
        else{
            throw new Exception("LOCAL LOCK : Data trying to be accessed is locked");
        }
    }

    public synchronized void releaseReadLock(){
        this.readLock = false;
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
