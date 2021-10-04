package DataObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by abhilashbss on 2/10/21.
 */
public class FileHandler {

    private final static Logger logger = Logger.getLogger("concurrency_control");

    public String readFile(String filePath){
        String data = "";
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found : " + e);
        }
        return data;
    }

    public void writeFile(String data,String filePath){
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(data);
            myWriter.close();
            logger.log(Level.INFO, "Data written to file " + filePath + " at " + new Timestamp(System.currentTimeMillis()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while writing to path " + filePath + " error :  " + e.toString());
        }
    }

}
