package se.jennifer.guesthouseapp.guesthouse.error;

public class BadRequest extends RuntimeException{
    public BadRequest(String message){
        super(message);
    }
}
