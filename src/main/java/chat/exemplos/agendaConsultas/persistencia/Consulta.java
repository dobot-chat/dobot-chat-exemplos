package chat.exemplos.agendaConsultas.persistencia;

import chat.dobot.bot.annotations.Entidade;
import chat.dobot.bot.annotations.Id;

import java.time.LocalDateTime;

@Entidade
public record Consulta(@Id int Id, String paciente, String medico, LocalDateTime dataHora) {
}
