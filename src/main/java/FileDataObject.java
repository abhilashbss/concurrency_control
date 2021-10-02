/**
 * Created by abhilashbss on 2/10/21.
 */
public class FileDataObject implements DataObjectInterface {

    public String filePath;
    public String dataEntity;
    public FileHandler handler;

    public String getFilePath() {
        return filePath;
    }

    public String getDataEntity() {
        return dataEntity;
    }

    public FileHandler getHandler() {
        return handler;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setDataEntity(String dataEntity) {
        this.dataEntity = dataEntity;
    }

    public void setHandler(FileHandler handler) {
        this.handler = handler;
    }

    public String get(){
        this.dataEntity = handler.readFile(this.getFilePath());
        return this.dataEntity;
    }

    public void set(String data){
        this.setDataEntity(data);
        handler.writeFile(this.getFilePath(), data);
    }
}
