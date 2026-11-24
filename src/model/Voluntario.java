package model;

public class Voluntario{
    private String nome;
    private String email;
    private String matricula;
    private int qtdAcoes;
    private int pontuacaoAcumulada;

    public Voluntario(String nome, String email, String matricula) {
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
    }

    public void adicionarParticipacao(int pontos){
        pontuacaoAcumulada += pontos;

        qtdAcoes++;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\n | Ações: " + qtdAcoes +
                "\n | Pontuação: " + pontuacaoAcumulada;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getQtdAcoes() {
        return qtdAcoes;
    }

    public int getPontuacaoAcumulada() {
        return pontuacaoAcumulada;
    }
}
