package LockSafeWrapper;

import java.sql.Timestamp;

/**
 * Created by abhilashbss on 2/10/21.
 */
public class RemoteLockHandler {
    public DBPersister dbPersister;

    public void lockObject(String lockType, String filePath, String serviceName,Timestamp timestamp) throws Exception{
        String query = "Insert into " + dbPersister.getTableName() + " (lock_type, file_path, service_name, timestamp) values ('"
                + lockType + "','"
                + filePath + "','"
                + serviceName + "','"
                + timestamp + "');";

        dbPersister.updateDB(query);
    }

    public void releaseObject(String lockType, String filePath) throws Exception{
        String query = "delete from " + dbPersister.getTableName() + " where "
                + " lock_type = '"  + lockType + "' and "
                + " file_path = '" + filePath + "';";

        dbPersister.updateDB(query);
    }

    public boolean isRemoteReadLock(String filePath) throws Exception {
        String query= "select * from " + dbPersister.getTableName()
                + " where file_path = '" + filePath + "' and "
                + " lock_type = 'read';";
        return dbPersister.ifSelectReturns(query);
    }

    public boolean isRemoteMultipleReadLock(String filePath) throws Exception {
        String query= "select count(1) from " + dbPersister.getTableName()
                + " where file_path = '" + filePath + "' and "
                + " lock_type = 'read' group by file_path having count(1)>1";
        return dbPersister.ifSelectReturns(query);
    }

    public boolean isRemoteWriteLock(String filePath) throws Exception {
        String query= "select * from " + dbPersister.getTableName()
                + " where file_path = '" + filePath + "' and "
                + " lock_type = 'write';";
        return dbPersister.ifSelectReturns(query);
    }


    public boolean isRemoteMultipleWriteLock(String filePath) throws Exception {
        String query= "select count(1) from " + dbPersister.getTableName()
                + " where file_path = '" + filePath + "' and "
                + " lock_type = 'write' group by file_path having count(1)>1";
        return dbPersister.ifSelectReturns(query);
    }

    public DBPersister getDbPersister() {
        return dbPersister;
    }

    public void setDbPersister(DBPersister dbPersister) {
        this.dbPersister = dbPersister;
    }
}
