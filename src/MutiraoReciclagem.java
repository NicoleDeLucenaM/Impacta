
package model;

import java.time.LocalDateTime;

public class MutiraoReciclagem extends Acao {

    private int duracaoHoras;

    public MutiraoReciclagem(String titulo, String descricao,
                             LocalDateTime data, int maxParticipantes,
                             int duracaoHoras) {

        super(titulo, descricao, data, maxParticipantes);

        if (duracaoHoras <= 0) {
            throw new IllegalArgumentException(
                    "A duração deve ser maior que zero."
            );
        }

        this.duracaoHoras = duracaoHoras;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    @Override
    public int calcularPontuacao() {
        return duracaoHoras * 4;
    }
}
