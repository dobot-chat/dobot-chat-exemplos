package com.exemplo.gerenciadorTarefasDB;

import chat.dobot.bot.annotations.Entidade;
import chat.dobot.bot.annotations.Id;

@Entidade
public record Tarefa(@Id int id, String descricao) {}
