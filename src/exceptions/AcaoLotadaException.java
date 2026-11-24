package exceptions;

public class AcaoLotadaException extends Exception {
    public AcaoLotadaException(String titulo) {
        super("A ação " + titulo + " já atingiu a capacidade máxima de voluntários.");
    }
}
