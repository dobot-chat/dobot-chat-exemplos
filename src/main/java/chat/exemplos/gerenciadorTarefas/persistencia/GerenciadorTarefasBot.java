package chat.exemplos.gerenciadorTarefas.persistencia;

import chat.dobot.bot.Contexto;
import chat.dobot.bot.annotations.DoBot;
import chat.dobot.bot.annotations.EstadoChat;
import org.yorm.exception.YormException;

import java.util.List;

//@DoBot
public class GerenciadorTarefasBot {

    private static final String MENSAGEM_BOAS_VINDAS = "Olá, eu sou o Gerenciador de Tarefas. Em que posso ajudar? <br>1 - Adicionar tarefa <br>2 - Listar tarefas <br>3 - Remover tarefa";
    private static final String MENSAGEM_ADICIONAR_TAREFA = "Qual tarefa deseja adicionar? (Digite 0 para cancelar)";
    private static final String MENSAGEM_REMOVER_TAREFA = "Qual tarefa deseja remover? (Digite 0 para cancelar)";
    private static final String MENSAGEM_SEM_TAREFAS_CADASTRADAS = "Não há tarefas cadastradas. <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_OPERACAO_CANCELADA = "Operação cancelada. <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_OPCAO_INVALIDA = "Opção inválida! <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_ADICAO_TAREFA_SUCESSO = "Tarefa adicionada com sucesso! <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_REMOCAO_TAREFA_SUCESSO = "Tarefa removida com sucesso! <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_TAREFA_NAO_ENCONTRADA = "Tarefa não encontrada! <br>Digite 0 se quiser ver as opções novamente.";


    @EstadoChat(inicial = true)
    public void menuInicial(Contexto contexto) throws YormException {
        String msg = contexto.getMensagemUsuario();
        System.out.println("Recebendo mensagem: " + msg);

        if (msg.matches("[0-3]")) {
            processarComando(contexto);
        } else {
            contexto.responder(MENSAGEM_OPCAO_INVALIDA);
        }
    }

    private void processarComando(Contexto contexto) throws YormException {
        switch (contexto.getMensagemUsuario()) {
            case "0":
                contexto.responder(MENSAGEM_BOAS_VINDAS, "menuInicial");
                break;
            case "1":
                contexto.responder(MENSAGEM_ADICIONAR_TAREFA, "adicionarTarefa");
                break;
            case "2":
                contexto.responder(listarTarefas(contexto), "menuInicial");
                break;
            case "3":
                if (contexto.getServico(Tarefa.class).buscarTodos().isEmpty()) {
                    contexto.responder(MENSAGEM_SEM_TAREFAS_CADASTRADAS, "menuInicial");
                } else {
                    contexto.responder(listarTarefas(contexto) + "<br>" + MENSAGEM_REMOVER_TAREFA, "removerTarefa");
                }
                break;
            default:
                contexto.responder(MENSAGEM_OPCAO_INVALIDA, "menuInicial");
                break;
        }
    }

    @EstadoChat(estado = "adicionarTarefa")
    public void adicionarTarefa(Contexto contexto) throws YormException {
        String msg = contexto.getMensagemUsuario();

        if (!msg.equals("0")) {
            Tarefa tarefa = new Tarefa(0, msg);
            contexto.getServico(Tarefa.class).salvar(tarefa);
            contexto.responder(MENSAGEM_ADICAO_TAREFA_SUCESSO);
        } else {
            contexto.responder(MENSAGEM_OPERACAO_CANCELADA);
        }

        contexto.mudarEstado("menuInicial");
    }

    private String listarTarefas(Contexto contexto) throws YormException {
        List<Tarefa> tarefas = contexto.getServico(Tarefa.class).buscarTodos();

        if (tarefas.isEmpty()) {
            return MENSAGEM_SEM_TAREFAS_CADASTRADAS;
        } else {
            StringBuilder listaDeTarefas = new StringBuilder();
            for (Tarefa tarefa : tarefas) {
                listaDeTarefas.append(tarefa.id()).append(" - ").append(tarefa.descricao()).append("<br>");
            }

            return listaDeTarefas.toString();
        }
    }

    @EstadoChat
    public void removerTarefa(Contexto contexto) {
        try {
            String msg = contexto.getMensagemUsuario();
            int idTarefa = Integer.parseInt(msg);

            Tarefa tarefaParaRemover = contexto.getServico(Tarefa.class).buscarPorId(idTarefa);

            if (tarefaParaRemover != null) {
                contexto.getServico(Tarefa.class).deletarPorId(idTarefa);
                contexto.responder(MENSAGEM_REMOCAO_TAREFA_SUCESSO);
            }  else if (msg.equals("0")) {
                contexto.responder(MENSAGEM_OPERACAO_CANCELADA);
            } else {
                contexto.responder(MENSAGEM_TAREFA_NAO_ENCONTRADA);
            }

            contexto.mudarEstado("menuInicial");
        } catch (NumberFormatException e) {
            contexto.responder(MENSAGEM_OPCAO_INVALIDA, "menuInicial");
        } catch (YormException e) {
            throw new RuntimeException(e);
        }
    }
}
