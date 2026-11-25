package Sistema;

import model.*;
import exceptions.*;

import java.time.LocalDateTime;
import java.util.*;

public class Impacta {
    private Map<String, Voluntario> voluntarios;
    private Map<Integer, Acao> acoes;
    private int proximoId;

    public Impacta() {
        this.voluntarios = new HashMap<>();
        this.acoes = new HashMap<>();
        this.proximoId = 1;
    }

    public boolean cadastrarVoluntario(String nome, String email, String matricula) throws EmailDuplicadoException {
        if (voluntarios.containsKey(email)) {
            throw new EmailDuplicadoException(email);
        } else {
            Voluntario voluntarioNovo = new Voluntario(nome, email, matricula);
            voluntarios.put(email, voluntarioNovo);
            return true;
        }
    }

    public String exibirVoluntario(String email){
        Voluntario voluntario = voluntarios.get(email);
        if (voluntario == null) {
            return "Voluntário não encontrado.";
        } else {
            return voluntario.toString();
        }
    }

    private int gerarId() {
        int id = proximoId;
        proximoId++;
        return id;
    }

    public int cadastrarPlantio(String titulo, String descricao, String data, int maxParticipantes, int qtdMudas) {
        LocalDateTime dataConvertida = LocalDateTime.parse(data);

        int id = gerarId();

        PlantioMudas plantio = new PlantioMudas(id, titulo, descricao, dataConvertida, maxParticipantes, qtdMudas);

        acoes.put(id, plantio);
        return id;
    }

    public int cadastrarMutirao(String titulo, String descricao, String data, int maxParticipantes, int duracaoHoras) {
        LocalDateTime dataConvertida = LocalDateTime.parse(data);

        int id = gerarId();

        MutiraoReciclagem mutirao = new MutiraoReciclagem(id, titulo, descricao, dataConvertida, maxParticipantes, duracaoHoras);

        acoes.put(id, mutirao);
        return id;
    }

    public int cadastrarOficina(String titulo, String descricao, String data, int maxParticipantes, int duracaoHoras, boolean kitMaterial){
        LocalDateTime dataConvertida = LocalDateTime.parse(data);

        int id = gerarId();

        OficinaEcologica oficina = new OficinaEcologica(id, titulo, descricao, dataConvertida, maxParticipantes, duracaoHoras, kitMaterial);

        acoes.put(id, oficina);
        return id;
    }

    public boolean inscreverVoluntario(String emailVoluntario, int idAcao) throws AcaoLotadaException, InscricaoDuplicadaException{
        Voluntario voluntario = voluntarios.get(emailVoluntario);
        Acao acao = acoes.get(idAcao);

        if (voluntario == null || acao == null) {
            return false;
        }

        if (acao.getQuantidadeInscritos() >= acao.getMaxParticipantes()) {
            throw new AcaoLotadaException(acao.getTitulo());
        }

        for (Voluntario voluntarioInscrito : acao.getInscritos()) {
            if (voluntarioInscrito.getEmail().equals(emailVoluntario)) {
                throw new InscricaoDuplicadaException(voluntarioInscrito.getNome(), acao.getTitulo());
            }
        }

        acao.adicionarInscrito(voluntario);
        voluntario.adicionarParticipacao(acao.calcularPontuacao());
        return true;
    }

    public String[] listarVoluntarios(){
        List<Voluntario> lista = new ArrayList<>(voluntarios.values());

        lista.sort(Comparator.comparingInt(Voluntario::getPontuacaoAcumulada).reversed().thenComparing(Voluntario::getNome));

        String[] resultado = new String[lista.size()];

        for (int item = 0; item < lista.size(); item++) {
            resultado[item] = lista.get(item).toString();
        }

        return resultado;
    }

    public String exibirDetalhesAcao(int idAcao) {
        Acao acao = acoes.get(idAcao);
        if (acao == null) {
            return "Ação não encontrada.";
        }
        return acao.exibirDetalhes();
    }
}
