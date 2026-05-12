package se.jennifer.guesthouseapp.guesthouse.error;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
