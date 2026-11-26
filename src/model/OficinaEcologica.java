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

    @Override
    public String exibirDetalhes() {
        return "Título: " + getTitulo() +
                "\n | Descrição: " + getDescricao() +
                "\n | Data: " + getData() +
                "\n | Pontuação: " + calcularPontuacao() +
                "\n | Inscritos: " + getInscritos() +
                "\n | Duração (h): " + duracaoHoras +
                "\n | Kit: " + (kitMaterial ? "Sim" : "Não");
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public boolean isKitMaterial() {
        return kitMaterial;
    }
}
