package com.exemplo;


import chat.dobot.anotacoes.Entidade;
import chat.dobot.anotacoes.Id;

import java.time.LocalDateTime;

@Entidade
public record Consulta(@Id int Id, String paciente, String medico, LocalDateTime dataHora) {
}
