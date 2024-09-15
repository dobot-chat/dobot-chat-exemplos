package com.exemplo;

import chat.dobot.app.DoBotApp;

public class Main {
    public static void main(String[] args) {
        String msgInicial = "Chatbot de agendamento de consultas inicializado com sucesso!<br> Consultas serão persistidas no banco de dados H2!";
        DoBotApp.setMensagemInicial(msgInicial);
        DoBotApp.getDoBotTema().setCorTextoTitulo("blue");
        DoBotApp.start(8080, 8082);
    }
}