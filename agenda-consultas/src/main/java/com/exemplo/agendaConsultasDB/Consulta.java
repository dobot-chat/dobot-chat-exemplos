package com.exemplo.agendaConsultasDB;

import chat.dobot.bot.annotations.Id;

import java.time.LocalDateTime;

@chat.dobot.bot.annotations.Entidade
public record Consulta(@Id int Id, String paciente, String medico, LocalDateTime dataHora) {
}
