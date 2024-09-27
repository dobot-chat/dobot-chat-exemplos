package chat.exemplos.agendaConsultas;

import chat.dobot.bot.DoBotChat;

public class Main {
    public static void main(String[] args) {
        String msgInicial = "Chatbot de agendamento de consultas inicializado com sucesso!";

        DoBotChat chat = DoBotChat.novoBot();
        chat.getTema().setCorFundoPagina("#C0C0C0");
        chat.getTema().setCorFundoChat("#90EE90");
        chat.getTema().setCorTextoChat("white");
        chat.getTema().setCorFundoMensagemBot("#3CB371");
        chat.getTema().setCorFundoMensagemUsuario("orange");

        chat.setMensagemInicial(msgInicial);
        chat.start(8081, 8083);
    }
}
