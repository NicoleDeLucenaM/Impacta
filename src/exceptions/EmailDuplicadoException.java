package exceptions;

public class EmailDuplicadoException extends Exception {
    public EmailDuplicadoException(String email) {
        super("Já existe um voluntário cadastrado com o e-mail: " + email);
    }
}