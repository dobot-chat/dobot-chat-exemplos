package com.exemplo.agendaConsultasLista;

import chat.dobot.bot.Contexto;
import chat.dobot.bot.annotations.DoBot;
import chat.dobot.bot.annotations.EstadoChat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

//@DoBot
public class AgendaConsultasListaBot {

    private Consulta novaConsulta;
    private List<Consulta> consultas = new LinkedList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final String MENSAGEM_MENU_INICIAL = "Olá, eu sou o Gerenciador de Consultas. Em que posso ajudar? <br>1 - Agendar consulta <br>2 - Listar consultas <br>3 - Cancelar consulta <br>4 - Buscar consultas por paciente";
    private static final String MENSAGEM_OPCAO_INVALIDA = "Opção inválida! <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_SEM_CONSULTAS_CADASTRADAS = "Não há consultas cadastradas. <br>Digite 0 se quiser ver as opções novamente.";
    private static final String MENSAGEM_CANCELAR_CONSULTA = "Qual consulta deseja cancelar? (Digite 0 para cancelar)";
    private static final String MENSAGEM_INFORMAR_PACIENTE = "Informe o nome do paciente (Digite 0 para cancelar)";
    private static final String MENSAGEM_INFORMAR_MEDICO = "Informe o médico que irá atender a consulta (Digite 0 para cancelar)";
    private static final String MENSAGEM_INFORMAR_DATA_HORA = "Informe a data e horário da consulta (Digite 0 para cancelar)";
    private static final String MENSAGEM_OPERACAO_CANCELADA = "Operação cancelada. <br>Digite 0 se quiser ver as opções novamente.";

    @EstadoChat(inicial = true)
    public void menuInicial(Contexto contexto) {
        String msg = contexto.getMensagemUsuario();

        if (msg.matches("[0-4]")) {
            processarComando(contexto);
        } else {
            contexto.responder(MENSAGEM_OPCAO_INVALIDA);
        }
    }

    private void processarComando(Contexto contexto) {
        switch (contexto.getMensagemUsuario()) {
            case "0":
                contexto.responder(MENSAGEM_MENU_INICIAL);
                break;
            case "1":
                contexto.responder(MENSAGEM_INFORMAR_PACIENTE, "informarPaciente");
                break;
            case "2":
                contexto.responder(listarConsultas(contexto));
                break;
            case "3":
                if (consultas.isEmpty()) {
                    contexto.responder(MENSAGEM_SEM_CONSULTAS_CADASTRADAS);
                } else {
                    contexto.responder(listarConsultas(contexto) + "<br>" + MENSAGEM_CANCELAR_CONSULTA, "cancelarConsulta");
                }
                break;
            case "4":
                contexto.responder(MENSAGEM_INFORMAR_PACIENTE, "buscarConsultasPorPaciente");
                break;
            default:
                contexto.responder(MENSAGEM_OPCAO_INVALIDA);
                break;
        }
    }

    private String listarConsultas(Contexto contexto) {
        StringBuilder sb = new StringBuilder();

        if (!consultas.isEmpty()) sb.append("Consultas agendadas: <br>");
        else sb.append(MENSAGEM_SEM_CONSULTAS_CADASTRADAS);

        for (int i = 1; i <= consultas.size(); i++) {
            Consulta consulta = consultas.get(i - 1);
            sb.append("(").append(i).append(") ").append("Paciente: ").append(consulta.getPaciente()).append(" - ");
            sb.append("Medico: ").append(consulta.getMedico()).append(" - ");
            sb.append("Data e Hora: ").append(formatter.format(consulta.getDataHora())).append("<br>");
        }
        return sb.toString();
    }

    @EstadoChat
    public void informarPaciente(Contexto contexto) {
        String nomePaciente = contexto.getMensagemUsuario();

        if (!nomePaciente.equals("0")) {
            novaConsulta = new Consulta();
            novaConsulta.setPaciente(nomePaciente);
            contexto.responder(MENSAGEM_INFORMAR_MEDICO, "informarMedico");
        } else {
            contexto.responder(MENSAGEM_OPERACAO_CANCELADA, "menuInicial");
        }
    }

    @EstadoChat
    public void informarMedico(Contexto contexto) {
        String nomeMedico = contexto.getMensagemUsuario();

        if (!nomeMedico.equals("0")) {
            novaConsulta.setMedico(nomeMedico);
            contexto.responder(MENSAGEM_INFORMAR_DATA_HORA, "informarDataHora");
        } else {
            contexto.responder(MENSAGEM_OPERACAO_CANCELADA, "menuInicial");
        }
    }

    @EstadoChat
    public void informarDataHora(Contexto contexto) {
        String msgDataHora = contexto.getMensagemUsuario();

        if (!msgDataHora.equals("0")) {
            try {
                LocalDateTime dataHora = LocalDateTime.parse(msgDataHora, formatter);
                novaConsulta.setDataHora(dataHora);

                consultas.add(novaConsulta);
                contexto.responder("Consulta agendada com sucesso!", "menuInicial");
            } catch (DateTimeParseException e) {
                contexto.responder("Formato inválido! Por favor, informe a data e hora no formato dd/MM/yyyy HH:mm (Exemplo: 01/01/20224 10:00)", "informarDataHora");
            }
        } else {
            contexto.responder(MENSAGEM_OPERACAO_CANCELADA, "menuInicial");
        }
    }

    @EstadoChat
    public void cancelarConsulta(Contexto contexto) {
        String idConsulta = contexto.getMensagemUsuario();

        if (!idConsulta.equals("0")) {
            try {
                int id = Integer.parseInt(idConsulta);

                if (id > 0 && id <= consultas.size()) {
                    consultas.remove(id - 1);
                    contexto.responder("Consulta cancelada com sucesso!", "menuInicial");
                    return;
                }

                contexto.responder("Consulta não encontrada! Digite 0 se quiser ver as opções novamente.", "menuInicial");
            } catch (NumberFormatException e) {
                contexto.responder("Formato inválido! Digite 0 se quiser ver as opções novamente.", "menuInicial");
            }
        } else {
            contexto.responder(MENSAGEM_OPERACAO_CANCELADA, "menuInicial");
        }
    }

    @EstadoChat(estado = "buscarConsultasPorPaciente")
    public void buscarConsultasPorPaciente(Contexto contexto) {
        String nomePaciente = contexto.getMensagemUsuario();

        if (nomePaciente.equals("0")) {
            contexto.responder(MENSAGEM_OPERACAO_CANCELADA, "menuInicial");
            return;
        }

        StringBuilder sb = new StringBuilder();

        List<Consulta> consultasFiltradas = this.consultas.stream().filter(consulta -> consulta.getPaciente().contains(nomePaciente)).toList();

        for (int i = 1; i <= consultasFiltradas.size(); i++) {
            Consulta consulta = consultasFiltradas.get(i - 1);
            sb.append("(").append(i).append(") ").append("Medico: ").append(consulta.getMedico()).append(" - ");
            sb.append("Data e Hora: ").append(formatter.format(consulta.getDataHora())).append("<br>");
        }

        if (!consultas.isEmpty()) sb.insert(0, "Consultas agendadas para " + nomePaciente + ": <br>");
        else sb.append("Não há consultas agendadas para ").append(nomePaciente).append(". <br>");

        contexto.responder(sb.toString());
    }
}
