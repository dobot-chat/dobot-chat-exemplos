package com.exemplo;

import chat.dobot.app.DoBotApp;

public class Main {
    public static void main(String[] args) {
        String msgInicial = "Chatbot de agendamento de consultas inicializado com sucesso!<br> Consultas serão adicionadas a uma lista conforme forem agendadas.";
        DoBotApp.setMensagemInicial(msgInicial);
        DoBotApp.getDoBotTema().setCorTextoTitulo("#8A2BE2");
        DoBotApp.getDoBotTema().setCorFundoMensagemBot("#4B0082");
        DoBotApp.getDoBotTema().setCorFundoMensagemUsuario("#FF1493");
        DoBotApp.start();
    }
}