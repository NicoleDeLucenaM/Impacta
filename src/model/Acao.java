package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Acao {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private int maxParticipantes;
    private List<Voluntario> inscritos;

    public Acao(int id, String titulo, String descricao, LocalDateTime data, int maxParticipantes) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.maxParticipantes = maxParticipantes;
        this.inscritos = new ArrayList<>();
    }

    public abstract int calcularPontuacao();

    public abstract String exibirDetalhes();

    public void adicionarInscrito(Voluntario voluntario) {
        inscritos.add(voluntario);
    }

    public int getQuantidadeInscritos(){
        return inscritos.size();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public List<Voluntario> getInscritos() {
        return inscritos;
    }
}
