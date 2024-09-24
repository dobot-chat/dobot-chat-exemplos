package chat.exemplos.gerenciadorTarefas;

import chat.dobot.bot.DoBotChat;

public class Main {
    public static void main(String[] args) {
        String msgInicial = "Chatbot para gerenciamento de tarefas inicializado com sucesso!";

        DoBotChat chat = DoBotChat.novoBot();
        chat.setMensagemInicial(msgInicial);
        chat.getTema().setCorFundoChat("#808080");
        chat.getTema().setCorFundoMensagemBot("purple");
        chat.getTema().setCorFundoMensagemUsuario("#00BFFF");

        chat.start();
    }
}