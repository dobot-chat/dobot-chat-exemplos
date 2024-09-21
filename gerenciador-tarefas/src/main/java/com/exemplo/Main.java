package com.exemplo;

import chat.dobot.bot.DoBotChat;

public class Main {
    public static void main(String[] args) {
        String msgInicial = "Chatbot para gerenciamento de tarefas inicializado com sucesso!";
        DoBotChat chat = DoBotChat.novoBot();

        chat.setMensagemInicial(msgInicial);
        chat.getTema().setCorTextoTitulo("blue");
        chat.getTema().setCorFundoMensagemBot("purple");

        chat.start();
    }
}