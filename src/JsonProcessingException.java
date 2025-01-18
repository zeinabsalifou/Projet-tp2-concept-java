// La classe JsonProcessingException étend RuntimeException et permet de gérer des exceptions spécifiques
public class JsonProcessingException extends RuntimeException {

    // constructeur
    public JsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}