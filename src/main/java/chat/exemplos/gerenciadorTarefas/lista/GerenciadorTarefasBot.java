package chat.exemplos.gerenciadorTarefas.lista;

import chat.dobot.bot.Contexto;
import chat.dobot.bot.annotations.DoBot;
import chat.dobot.bot.annotations.EstadoChat;

import java.util.LinkedList;
import java.util.List;

//@DoBot
public class GerenciadorTarefasBot {

    private final List<String> tarefas = new LinkedList<>();

    private static final String MENSAGEM_BOAS_VINDAS = "Olá, eu sou o Gerenciador de Tarefas, em que posso ajudar? <br>1 - Adicionar tarefa <br>2 - Listar tarefas <br>3 - Remover tarefa";
    private static final String MENSAGEM_ADICIONAR_TAREFA = "Qual tarefa deseja adicionar? (Digite 0 para cancelar)";
    private static final String MENSAGEM_REMOVER_TAREFA = "Qual tarefa deseja remover? (Digite 0 para cancelar)";
    private static final String MENSAGEM_SEM_TAREFAS_CADASTRADAS = "Não há tarefas cadastradas. <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_OPERACAO_CANCELADA = "Operação cancelada. <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_OPCAO_INVALIDA = "Opção inválida! <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_ADICAO_TAREFA_SUCESSO = "Tarefa adicionada com sucesso! <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_REMOCAO_TAREFA_SUCESSO = "Tarefa removida com sucesso! <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_TAREFA_NAO_ENCONTRADA = "Tarefa não encontrada! <br>Digite 0 se quiser ver as opções novamente.";


    @EstadoChat(estado = "menu", inicial = true)
    public void menuInicial(Contexto contexto) {
        String msg = contexto.getMensagemUsuario();
        System.out.println("Recebendo mensagem: " + msg);

        if (msg.matches("[0-3]")) {
            processarComando(contexto);
        } else {
            contexto.responder(MENSAGEM_OPCAO_INVALIDA);
        }
    }

    private void processarComando(Contexto contexto) {
        switch (contexto.getMensagemUsuario()) {
            case "0":
                contexto.responder(MENSAGEM_BOAS_VINDAS, "menu");
                break;
            case "1":
                contexto.responder(MENSAGEM_ADICIONAR_TAREFA, "adicionarTarefa");
                break;
            case "2":
                contexto.responder(listarTarefas(), "menu");
                break;
            case "3":
                if (tarefas.isEmpty()) {
                    contexto.responder(MENSAGEM_SEM_TAREFAS_CADASTRADAS, "menu");
                } else {
                    contexto.responder(listarTarefas() + "<br>" + MENSAGEM_REMOVER_TAREFA, "removerTarefa");
                }
                break;
            default:
                contexto.responder(MENSAGEM_OPCAO_INVALIDA, "menu");
                break;
        }
    }

    @EstadoChat(estado = "adicionarTarefa")
    public void adicionarTarefa(Contexto contexto) {
        String msg = contexto.getMensagemUsuario();

        if (!msg.equals("0")) {
            tarefas.add(msg);
            contexto.responder(MENSAGEM_ADICAO_TAREFA_SUCESSO);
        } else {
            contexto.responder(MENSAGEM_OPERACAO_CANCELADA);
        }

        contexto.mudarEstado("menu");
    }

    private String listarTarefas() {
        if (tarefas.isEmpty()) {
            return MENSAGEM_SEM_TAREFAS_CADASTRADAS;
        } else {
            StringBuilder listaDeTarefas = new StringBuilder();
            for (int i = 0; i < tarefas.size(); i++) {
                listaDeTarefas.append((i + 1)).append(" - ").append(tarefas.get(i)).append("<br>");
            }

            return listaDeTarefas.toString();
        }
    }

    @EstadoChat
    public void removerTarefa(Contexto contexto) {
        try {
            String msg = contexto.getMensagemUsuario();
            int indice = Integer.parseInt(msg) - 1;

            if (indice >= 0 && indice < tarefas.size()) {
                tarefas.remove(indice);
                contexto.responder(MENSAGEM_REMOCAO_TAREFA_SUCESSO);
            } else if (msg.equals("0")) {
                contexto.responder(MENSAGEM_OPERACAO_CANCELADA);
            } else {
                contexto.responder(MENSAGEM_TAREFA_NAO_ENCONTRADA);
            }

            contexto.mudarEstado("menu");
        } catch (NumberFormatException e) {
            contexto.responder(MENSAGEM_OPCAO_INVALIDA, "menu");
        }
    }
}
