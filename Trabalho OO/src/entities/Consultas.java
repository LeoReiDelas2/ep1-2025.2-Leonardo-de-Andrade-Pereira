package entities;

import enums.StatusConsulta;
import utils.Searcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String diagnosticoStr = (this.diagnostico == null || this.diagnostico.isBlank())
                ? "Não registrado"
                : this.diagnostico;
        String statusStr;
        switch (this.statusConsulta) {
            case AGENDADO:
                statusStr = "Agendado";
                break;
            case CONCLUIDO:
                statusStr = "Concluído";
                break;
            case CANCELADO:
                statusStr = "Cancelado";
                break;
            default:
                statusStr = this.statusConsulta.name();
        }
        return "--- Informações da Consulta ---\n" +
                "  Paciente: " + this.paciente.getNome() + "\n" +
                "  Médico: Dr(a). " + this.medico.getNome() + "\n" +
                "  Especialidade: " + this.especialidadeDaConsulta.getNome() + "\n" +
                "  Data/Hora: " + this.dataHora.format(formatter) + "\n" +
                "  Local: " + this.local + "\n" +
                "  Status: " + statusStr + "\n" +
                "  Diagnóstico: " + diagnosticoStr + "\n" +
                "--------------------------------";
    }
    public static Consultas fromString(String linha, List<Paciente> todosOsPacientes, List<Medico> todosOsMedicos, List<Especialidade> todasAsEspecialidades) {
        try {
            String[] dados = linha.split(";", -1);
            String pacienteCpf = dados[0];
            String medicoCrm = dados[1];
            String nomeEspecialidade = dados[5];
            Paciente paciente = Searcher.getPaciente(pacienteCpf, todosOsPacientes);
            Medico medico = Searcher.getMedicoBusca(medicoCrm, todosOsMedicos);
            Especialidade especialidade = Searcher.buscarEspecialidadePorNome(nomeEspecialidade, todasAsEspecialidades);
            if (paciente == null || medico == null || especialidade == null) {
                System.err.println("Aviso: Não foi possível reconectar a consulta, dados faltando. Linha: " + linha);
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
            LocalDateTime dataHora = LocalDateTime.parse(dados[2], formatter);
            String local = dados[3];
            StatusConsulta status = StatusConsulta.valueOf(dados[4]);
            String diagnostico = dados[6];
            Consultas consulta = new Consultas(dataHora, null, local, medico, paciente,status, especialidade);
            consulta.setDiagnostico(diagnostico.isBlank() ? null : diagnostico);
            return consulta;
        } catch (Exception e) {
            System.err.println("Erro ao converter linha para Consulta: " + linha + " | Erro: " + e.getMessage());
            return null;
        }
    }
    public String objectToString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
        String dataFormatada = this.dataHora.format(formatter);
        String diagnosticoStr = (this.diagnostico == null || this.diagnostico.isBlank()) ? "" : this.diagnostico;

        return String.join(";",
                this.paciente.getCpf(),
                this.medico.getCrm(),
                dataFormatada,
                this.local,
                this.statusConsulta.name(),
                this.especialidadeDaConsulta.getNome(),
                diagnosticoStr
        );
    }
}
