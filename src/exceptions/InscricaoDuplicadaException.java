package exceptions;

public class InscricaoDuplicadaException extends Exception {
    public InscricaoDuplicadaException(String nome, String titulo) {
        super("O voluntário " + nome + " já está inscrito na ação " + titulo + ".");
    }
}
