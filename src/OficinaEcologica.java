
package model;

import java.time.LocalDateTime;

public class OficinaEcologica extends Acao {

    private int duracaoHoras;
    private boolean kitMaterial;

    public OficinaEcologica(String titulo, String descricao,
                            LocalDateTime data, int maxParticipantes,
                            int duracaoHoras, boolean kitMaterial) {

        super(titulo, descricao, data, maxParticipantes);

        if (duracaoHoras <= 0) {
            throw new IllegalArgumentException(
                    "A duração deve ser maior que zero."
            );
        }

        this.duracaoHoras = duracaoHoras;
        this.kitMaterial = kitMaterial;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public boolean isKitMaterial() {
        return kitMaterial;
    }

    @Override
    public int calcularPontuacao() {
        int pontuacao = duracaoHoras * 3;

        if (kitMaterial) {
            pontuacao += 10;
        }

        return pontuacao;
    }
}
