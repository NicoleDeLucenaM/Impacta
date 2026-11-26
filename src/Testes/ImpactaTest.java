package Testes;

import Sistema.Impacta;
import exceptions.AcaoLotadaException;
import exceptions.EmailDuplicadoException;
import exceptions.InscricaoDuplicadaException;
import exceptions.MatriculaDuplicadaException;
import model.Acao;
import model.MutiraoReciclagem;
import model.OficinaEcologica;
import model.PlantioMudas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImpactaTest {

    //Testes obrigatorios que Samara mandou

    //Formula: 5 pontos de base + (2 * quantidade de mudas plantadas)
    @Test
    @DisplayName("Teste plantio: 5 mudas = 15 pontos")
    public void testePontuacaoPlantio(){
        PlantioMudas plantio = new PlantioMudas(1,"Teste plantio", "O plantio de 5 mudas deve dar 15 pontos", java.time.LocalDateTime.now(), 7, 5);

        int resultado = plantio.calcularPontuacao();
        assertEquals(15, resultado);

    }

    @Test
    @DisplayName("Teste mutirao: 4 horas = 16 pontos")
    //Formula: 4 pontos * hora de duracao
    public void testePontuacaoMutirao(){
        MutiraoReciclagem mutirao = new MutiraoReciclagem(2,"Teste mutirao","O mutirao de 4 horas deve dar 16", java.time.LocalDateTime.now(), 8, 4);

        int resultado = mutirao.calcularPontuacao();
        assertEquals(16, resultado);
    }

    @Test
    @DisplayName("Teste oficina com kit")
    //Formula: 3 pontos * hora de duração + 10 pontos se tiver o kit
    public void testePontuacaoOficinaComKit(){
        OficinaEcologica oficina = new OficinaEcologica(3, "Teste oficina com kit","A oficina de 3 horas mais o kit deve dar 19", java.time.LocalDateTime.now(), 9, 3, true);

        int resultado = oficina.calcularPontuacao();
        assertEquals(19, resultado);
    }

    @Test
    @DisplayName("Teste oficina sem kit")
    //Formula: 3 pontos * hora de duração + 10 pontos se tiver o kit (nesse caso vai ser 0, pois não tem kit)
    public void testePontuacaoOficinaSemKit(){
        OficinaEcologica oficina = new OficinaEcologica(3, "Teste oficina sem kit","A oficina de 3 horas sem o kit deve dar 9", java.time.LocalDateTime.now(), 9, 3, false);

        int resultado = oficina.calcularPontuacao();
        assertEquals(9, resultado);
    }

    @Test
    @DisplayName("Teste ordenação")
    public void testeOrdenacao() throws EmailDuplicadoException, AcaoLotadaException, InscricaoDuplicadaException, MatriculaDuplicadaException {
        Impacta sistema = new Impacta();

        sistema.cadastrarVoluntario("Flora","WinxFlora@gmail.com", "1111");
        sistema.cadastrarVoluntario("Musa","WinxMusa@gmail.com", "2222");
        sistema.cadastrarVoluntario("Aisha","WinxAisha@gmail.com", "3333");
        sistema.cadastrarVoluntario("Estela","WinxEstela@gmail.com", "4444");

        int oficinaEquipeF = sistema.cadastrarOficina("Equipe F","Mutirao da equipe F", "2006-12-21T10:00:00",1,7,false);
        int oficinaEquipeM = sistema.cadastrarOficina("Equipe M","Mutirao da equipe M", "2006-12-21T10:00:00",1,2,true);
        int oficinaEquipeAE = sistema.cadastrarOficina("Equipe AE","Mutirao da equipe AE", "2006-12-21T10:00:00",2,4,false);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", oficinaEquipeF);
        sistema.inscreverVoluntario("WinxMusa@gmail.com", oficinaEquipeM);
        sistema.inscreverVoluntario("WinxAisha@gmail.com", oficinaEquipeAE);
        sistema.inscreverVoluntario("WinxEstela@gmail.com", oficinaEquipeAE);

        String[] ranking = sistema.listarVoluntarios();

        assertTrue(ranking[0].contains("Flora"));
        assertTrue(ranking[1].contains("Musa"));
        assertTrue(ranking[2].contains("Aisha"));
        assertTrue(ranking[3].contains("Estela"));
    }

    //Testes extras

    @Test
    @DisplayName("Teste polimorfismo: Ação calcula a pontuação de cada tipo")
    public void testePolimorfismoPontuacao(){
        Acao plantio = new PlantioMudas(1, "Plantio Winx", "5 mudas = 15", java.time.LocalDateTime.now(), 5, 5);
        Acao mutirao = new MutiraoReciclagem(2, "Mutirao Winx", "4 horas = 16", java.time.LocalDateTime.now(), 5, 4);
        Acao oficina = new OficinaEcologica(3, "Oficina Winx", "3 horas com kit = 19", java.time.LocalDateTime.now(), 5, 3, true);

        assertEquals(15, plantio.calcularPontuacao());
        assertEquals(16, mutirao.calcularPontuacao());
        assertEquals(19, oficina.calcularPontuacao());
    }

    @Test
    @DisplayName("Teste plantio sem mudas")
    public void testePontuacaoPlantioSemMudas(){
        PlantioMudas plantio = new PlantioMudas(1, "Plantio sem mudas", "0 mudas deve dar 5", java.time.LocalDateTime.now(), 5, 0);

        assertEquals(5, plantio.calcularPontuacao());
    }

    @Test
    @DisplayName("Teste mutirao: 1 hora = 4 pontos")
    public void testePontuacaoMutiraoUmaHora(){
        MutiraoReciclagem mutirao = new MutiraoReciclagem(1, "Mutirao curto", "1 hora deve dar 4", java.time.LocalDateTime.now(), 5, 1);

        assertEquals(4, mutirao.calcularPontuacao());
    }

    @Test
    @DisplayName("Teste cadastro de voluntario da true")
    public void testeCadastroVoluntario() throws EmailDuplicadoException, MatriculaDuplicadaException {
        Impacta sistema = new Impacta();

        boolean resultado = sistema.cadastrarVoluntario("Bloom", "WinxBloom@gmail.com", "5555");

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Teste exibir voluntario")
    public void testeExibirVoluntarioNovo() throws EmailDuplicadoException, MatriculaDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Bloom", "WinxBloom@gmail.com", "5555");

        String detalhes = sistema.exibirVoluntario("WinxBloom@gmail.com");

        assertTrue(detalhes.contains("Bloom"));
        assertTrue(detalhes.contains("Ações: 0"));
        assertTrue(detalhes.contains("Pontuação: 0"));
    }

    @Test
    @DisplayName("Teste exibir voluntario que nao existe")
    public void testeExibirVoluntarioInexistente(){
        Impacta sistema = new Impacta();

        assertEquals("Voluntário não encontrado.", sistema.exibirVoluntario("WinxTecna@gmail.com"));
    }

    @Test
    @DisplayName("Teste lista de voluntarios vazia")
    public void testeListarVoluntariosVazio(){
        Impacta sistema = new Impacta();

        assertEquals(0, sistema.listarVoluntarios().length);
    }

    @Test
    @DisplayName("Teste desempate por ordem alfabetica")
    public void testeDesempateAlfabetico() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();

        sistema.cadastrarVoluntario("Tecna", "WinxTecna@gmail.com", "6666");
        sistema.cadastrarVoluntario("Bloom", "WinxBloom@gmail.com", "5555");
        sistema.cadastrarVoluntario("Layla", "WinxLayla@gmail.com", "7777");

        int mutirao = sistema.cadastrarMutirao("Mutirao empate", "Todas ganham 8 pontos", "2026-12-21T10:00:00", 3, 2);

        sistema.inscreverVoluntario("WinxTecna@gmail.com", mutirao);
        sistema.inscreverVoluntario("WinxBloom@gmail.com", mutirao);
        sistema.inscreverVoluntario("WinxLayla@gmail.com", mutirao);

        String[] ranking = sistema.listarVoluntarios();

        assertTrue(ranking[0].contains("Bloom"));
        assertTrue(ranking[1].contains("Layla"));
        assertTrue(ranking[2].contains("Tecna"));
    }

    @Test
    @DisplayName("Teste voluntario sem ações fica no final do ranking")
    public void testeVoluntarioSemAcaoFicaNoFinal() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();

        sistema.cadastrarVoluntario("Aisha", "WinxAisha@gmail.com", "3333");
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        int plantio = sistema.cadastrarPlantio("Plantio da Flora", "Flora ganha 9 pontos", "2026-12-21T10:00:00", 2, 2);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", plantio);

        String[] ranking = sistema.listarVoluntarios();

        assertTrue(ranking[0].contains("Flora"));
        assertTrue(ranking[1].contains("Aisha"));
    }

    @Test
    @DisplayName("Teste email duplicado")
    public void testeEmailDuplicado() throws EmailDuplicadoException, MatriculaDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        assertThrows(EmailDuplicadoException.class, () -> sistema.cadastrarVoluntario("Musa", "WinxFlora@gmail.com", "2222"));
    }

    @Test
    @DisplayName("Teste email duplicado nao cadastra o segundo voluntario")
    public void testeEmailDuplicadoNaoSobrescreve() throws EmailDuplicadoException, MatriculaDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        assertThrows(EmailDuplicadoException.class, () -> sistema.cadastrarVoluntario("Musa", "WinxFlora@gmail.com", "2222"));

        assertEquals(1, sistema.listarVoluntarios().length);
        assertTrue(sistema.exibirVoluntario("WinxFlora@gmail.com").contains("Flora"));
    }

    @Test
    @DisplayName("Teste matricula duplicada")
    public void testeMatriculaDuplicada() throws EmailDuplicadoException, MatriculaDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        assertThrows(MatriculaDuplicadaException.class, () -> sistema.cadastrarVoluntario("Musa", "WinxMusa@gmail.com", "1111"));
    }

    @Test
    @DisplayName("Teste acao lotada")
    public void testeAcaoLotada() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");
        sistema.cadastrarVoluntario("Musa", "WinxMusa@gmail.com", "2222");

        int plantio = sistema.cadastrarPlantio("Plantio lotado", "So cabe 1 voluntario", "2026-12-21T10:00:00", 1, 3);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", plantio);

        assertThrows(AcaoLotadaException.class, () -> sistema.inscreverVoluntario("WinxMusa@gmail.com", plantio));
    }

    @Test
    @DisplayName("Teste acao lotada nao da pontos para quem ficou de fora")
    public void testeAcaoLotadaNaoDaPontos() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");
        sistema.cadastrarVoluntario("Musa", "WinxMusa@gmail.com", "2222");

        int plantio = sistema.cadastrarPlantio("Plantio lotado", "So cabe 1 voluntario", "2026-12-21T10:00:00", 1, 3);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", plantio);

        assertThrows(AcaoLotadaException.class, () -> sistema.inscreverVoluntario("WinxMusa@gmail.com", plantio));

        String detalhesMusa = sistema.exibirVoluntario("WinxMusa@gmail.com");
        assertTrue(detalhesMusa.contains("Ações: 0"));
        assertTrue(detalhesMusa.contains("Pontuação: 0"));
    }

    @Test
    @DisplayName("Teste inscrição duplicada")
    public void testeInscricaoDuplicada() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        int mutirao = sistema.cadastrarMutirao("Mutirao da Flora", "Flora nao pode entrar 2 vezes", "2026-12-21T10:00:00", 5, 2);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", mutirao);

        assertThrows(InscricaoDuplicadaException.class, () -> sistema.inscreverVoluntario("WinxFlora@gmail.com", mutirao));
    }

    @Test
    @DisplayName("Teste inscrição duplicada nao soma pontos de novo")
    public void testeInscricaoDuplicadaNaoSomaPontos() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        int mutirao = sistema.cadastrarMutirao("Mutirao da Flora", "2 horas = 8 pontos", "2026-12-21T10:00:00", 5, 2);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", mutirao);

        assertThrows(InscricaoDuplicadaException.class, () -> sistema.inscreverVoluntario("WinxFlora@gmail.com", mutirao));

        String detalhes = sistema.exibirVoluntario("WinxFlora@gmail.com");
        assertTrue(detalhes.contains("Ações: 1"));
        assertTrue(detalhes.contains("Pontuação: 8"));
    }

    @Test
    @DisplayName("Teste inscrição valida da true")
    public void testeInscricaoValida() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        int plantio = sistema.cadastrarPlantio("Plantio da Flora", "Inscrição normal", "2026-12-21T10:00:00", 5, 5);

        assertTrue(sistema.inscreverVoluntario("WinxFlora@gmail.com", plantio));
    }

    @Test
    @DisplayName("Teste inscrição com voluntario que nao existe da false")
    public void testeInscricaoVoluntarioInexistente() throws AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();

        int plantio = sistema.cadastrarPlantio("Plantio vazio", "Ninguem cadastrado", "2026-12-21T10:00:00", 5, 5);

        assertFalse(sistema.inscreverVoluntario("WinxTecna@gmail.com", plantio));
    }

    @Test
    @DisplayName("Teste inscrição em acao que nao existe da false")
    public void testeInscricaoAcaoInexistente() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        assertFalse(sistema.inscreverVoluntario("WinxFlora@gmail.com", 99));
    }

    @Test
    @DisplayName("Teste voluntario acumula pontos de varias ações")
    public void testeAcumuloDePontos() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        int plantio = sistema.cadastrarPlantio("Plantio da Flora", "3 mudas = 11", "2026-12-21T10:00:00", 5, 3);
        int oficina = sistema.cadastrarOficina("Oficina da Flora", "2 horas com kit = 16", "2026-12-22T10:00:00", 5, 2, true);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", plantio);
        sistema.inscreverVoluntario("WinxFlora@gmail.com", oficina);

        String detalhes = sistema.exibirVoluntario("WinxFlora@gmail.com");
        assertTrue(detalhes.contains("Ações: 2"));
        assertTrue(detalhes.contains("Pontuação: 27"));
    }

    @Test
    @DisplayName("Teste acao com capacidade exata aceita todos")
    public void testeCapacidadeExata() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");
        sistema.cadastrarVoluntario("Musa", "WinxMusa@gmail.com", "2222");

        int mutirao = sistema.cadastrarMutirao("Mutirao para 2", "Cabe exatamente 2", "2026-12-21T10:00:00", 2, 1);

        assertTrue(sistema.inscreverVoluntario("WinxFlora@gmail.com", mutirao));
        assertTrue(sistema.inscreverVoluntario("WinxMusa@gmail.com", mutirao));
    }

    //Cadastro e detalhes das ações

    @Test
    @DisplayName("Teste ids das ações sao sequenciais")
    public void testeIdsSequenciais(){
        Impacta sistema = new Impacta();

        int plantio = sistema.cadastrarPlantio("Plantio", "Primeira ação", "2026-12-21T10:00:00", 5, 5);
        int mutirao = sistema.cadastrarMutirao("Mutirao", "Segunda ação", "2026-12-21T10:00:00", 5, 2);
        int oficina = sistema.cadastrarOficina("Oficina", "Terceira ação", "2026-12-21T10:00:00", 5, 2, false);

        assertEquals(1, plantio);
        assertEquals(2, mutirao);
        assertEquals(3, oficina);
    }

    @Test
    @DisplayName("Teste detalhes de acao que nao existe")
    public void testeDetalhesAcaoInexistente(){
        Impacta sistema = new Impacta();

        assertEquals("Ação não encontrada.", sistema.exibirDetalhesAcao(99));
    }

    @Test
    @DisplayName("Teste detalhes do plantio")
    public void testeDetalhesPlantio(){
        Impacta sistema = new Impacta();

        int plantio = sistema.cadastrarPlantio("Plantio Magix", "Plantio de 5 mudas", "2026-12-21T10:00:00", 5, 5);

        String detalhes = sistema.exibirDetalhesAcao(plantio);

        assertTrue(detalhes.contains("Plantio Magix"));
        assertTrue(detalhes.contains("Plantio de 5 mudas"));
        assertTrue(detalhes.contains("Pontuação: 15"));
        assertTrue(detalhes.contains("Mudas: 5"));
    }

    @Test
    @DisplayName("Teste detalhes do mutirao")
    public void testeDetalhesMutirao(){
        Impacta sistema = new Impacta();

        int mutirao = sistema.cadastrarMutirao("Mutirao Magix", "Mutirao de 4 horas", "2026-12-21T10:00:00", 5, 4);

        String detalhes = sistema.exibirDetalhesAcao(mutirao);

        assertTrue(detalhes.contains("Mutirao Magix"));
        assertTrue(detalhes.contains("Pontuação: 16"));
        assertTrue(detalhes.contains("Duração (h): 4"));
    }

    @Test
    @DisplayName("Teste detalhes da oficina com e sem kit")
    public void testeDetalhesOficina(){
        Impacta sistema = new Impacta();

        int comKit = sistema.cadastrarOficina("Oficina com kit", "Oficina com kit", "2026-12-21T10:00:00", 5, 3, true);
        int semKit = sistema.cadastrarOficina("Oficina sem kit", "Oficina sem kit", "2026-12-21T10:00:00", 5, 3, false);

        assertTrue(sistema.exibirDetalhesAcao(comKit).contains("Kit: Sim"));
        assertTrue(sistema.exibirDetalhesAcao(semKit).contains("Kit: Não"));
    }

    @Test
    @DisplayName("Teste detalhes da acao mostram o voluntario inscrito")
    public void testeDetalhesMostramInscritos() throws EmailDuplicadoException, MatriculaDuplicadaException, AcaoLotadaException, InscricaoDuplicadaException {
        Impacta sistema = new Impacta();
        sistema.cadastrarVoluntario("Flora", "WinxFlora@gmail.com", "1111");

        int plantio = sistema.cadastrarPlantio("Plantio da Flora", "Flora inscrita", "2026-12-21T10:00:00", 5, 5);

        sistema.inscreverVoluntario("WinxFlora@gmail.com", plantio);

        assertTrue(sistema.exibirDetalhesAcao(plantio).contains("Flora"));
    }

    @Test
    @DisplayName("Teste data em formato invalido")
    public void testeDataInvalida(){
        Impacta sistema = new Impacta();

        assertThrows(java.time.format.DateTimeParseException.class, () -> sistema.cadastrarPlantio("Plantio", "Data errada", "21/12/2026", 5, 5));
    }
}
