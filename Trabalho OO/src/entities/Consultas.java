package entities;

import enums.StatusConsulta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consultas
{
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private StatusConsulta statusConsulta;
    private String diagnostico;
    private Especialidade especialidadeDaConsulta;

    public Consultas() {
    }
    public Consultas(LocalDateTime dataHora, String diagnostico, String local, Medico medico, Paciente paciente, StatusConsulta statusConsulta, Especialidade especialidadeDaConsulta) {
        this.dataHora = dataHora;
        this.diagnostico = diagnostico;
        this.local = local;
        this.medico = medico;
        this.paciente = paciente;
        this.statusConsulta = statusConsulta;
        this.especialidadeDaConsulta = especialidadeDaConsulta;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getLocal() {
        return local;
    }

    public Medico getMedico() {
        return medico;
    }
    public Especialidade getEspecialidadeDaConsulta() {
        return especialidadeDaConsulta;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
        String diagnosticoStr = (this.diagnostico == null) ? "" : this.diagnostico;
        return String.join(";",
                this.paciente.getCpf(),
                this.medico.getCrm(),
                this.dataHora.format(formatter),
                this.local,
                this.statusConsulta.name(),
                this.especialidadeDaConsulta.getNome(),
                diagnosticoStr
        );
    }
}
