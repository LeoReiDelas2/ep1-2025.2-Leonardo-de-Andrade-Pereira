package entities;

import enums.StatusConsulta;

import java.time.LocalDateTime;

public class Consultas
{
    private Paciente paciente;
    private String medico;
    private LocalDateTime dataHora;
    private String local;
    private StatusConsulta statusConsulta;
    private String diagnostico;

    public Consultas() {
    }

    public Consultas(LocalDateTime dataHora, String diagnostico, String local, String medico, Paciente paciente, StatusConsulta statusConsulta) {
        this.dataHora = dataHora;
        this.diagnostico = diagnostico;
        this.local = local;
        this.medico = medico;
        this.paciente = paciente;
        this.statusConsulta = statusConsulta;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getLocal() {
        return local;
    }

    public String getMedico() {
        return medico;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

}
