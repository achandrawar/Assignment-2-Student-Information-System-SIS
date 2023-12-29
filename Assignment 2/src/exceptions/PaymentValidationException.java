package exceptions;

public class PaymentValidationException extends Exception {
    public PaymentValidationException(String message) {
        super(message);
    }
}
