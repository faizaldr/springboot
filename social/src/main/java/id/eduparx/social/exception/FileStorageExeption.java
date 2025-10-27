package id.eduparx.social.exception;

public class FileStorageExeption extends RuntimeException{
    public FileStorageExeption(String message){
        super(message);
    }

    public FileStorageExeption(String message, Throwable cause){
        super(message, cause);
    }
    
}
