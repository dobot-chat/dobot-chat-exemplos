package chat.exemplos.agendaConsultas;

import chat.dobot.bot.DoBotChat;

public class Main {
    public static void main(String[] args) {
        String msgInicial = "Chatbot de agendamento de consultas inicializado com sucesso!";

        DoBotChat chat = DoBotChat.novoBot();
        chat.setMensagemInicial(msgInicial);
        chat.start(8081, 8083);
    }
}