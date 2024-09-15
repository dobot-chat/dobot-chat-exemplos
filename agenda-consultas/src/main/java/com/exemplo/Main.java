package com.exemplo;

import chat.dobot.bot.DoBotChat;

public class Main {
    public static void main(String[] args) {
        String msgInicial = "Chatbot de agendamento de consultas inicializado com sucesso!";
        DoBotChat chat = DoBotChat.novoBot();

        chat.setMensagemInicial(msgInicial);
        chat.getTema().setCorTextoTitulo("#00BFFF");

        chat.start(8081, 8083);
    }
}