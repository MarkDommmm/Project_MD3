package Database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile<T> implements Serializable{
    public static final String LISTUSE_FILE = "G:\\ProjectMD3\\ProjectMD3\\src\\Database\\listuse.txt";
    public static final String LISTCARD_FILE = "G:\\ProjectMD3\\ProjectMD3\\src\\Database\\listcard.txt";
    public static final String LISTATM_FILE = "G:\\ProjectMD3\\ProjectMD3\\src\\Database\\listATM.txt";
    public static  final String BANKSYSTEM_FILE = "G:\\ProjectMD3\\ProjectMD3\\src\\Database\\bankSystem.txt";
     public static final String TRANSACTION_FILE = "G:\\ProjectMD3\\ProjectMD3\\src\\Database\\transaction.txt";
    public void writeToFile(List<T> list, String fileName){
        File file =null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            fos= new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            // ghi file
            oos.writeObject(list);
            oos.flush();
        }catch (IOException e){
            throw  new RuntimeException(e);
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public List<T> readFromFile(String fileName){
        File file = null;
        FileInputStream fis =null;
        ObjectInputStream ois = null;
        List<T> list = new ArrayList<>();
        try{
            file = new File(fileName);
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();

        }catch (ClassNotFoundException c){
            c.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return list;
    }

    public List<T> refeshData(List<T> list, String filePath){
        writeToFile(list, filePath);
        return readFromFile(filePath);
    }
}