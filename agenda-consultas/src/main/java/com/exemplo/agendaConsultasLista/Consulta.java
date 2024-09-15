package com.exemplo.agendaConsultasLista;

import java.time.LocalDateTime;

public class Consulta {

    private String paciente;
    private String medico;
    private LocalDateTime dataHora;

    public Consulta() {
    }

    public Consulta(String paciente, String medico, LocalDateTime dataHora) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
