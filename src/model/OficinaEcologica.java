package model;

import java.time.LocalDateTime;

public class OficinaEcologica extends Acao{
    private int duracaoHoras;
    private boolean kitMaterial;

    public OficinaEcologica(int id, String titulo, String descricao, LocalDateTime data, int maxParticipantes, int duracaoHoras, boolean kitMaterial) {
        super(id, titulo, descricao, data, maxParticipantes);

        this.duracaoHoras = duracaoHoras;
        this.kitMaterial = kitMaterial;
    }

    @Override
    public int calcularPontuacao() {
        return 3 * duracaoHoras + (kitMaterial ? 10:0);
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public boolean isKitMaterial() {
        return kitMaterial;
    }
}

