package entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class Medico {
    private String nome;
    private String crm;
    private Double custoDaConsulta;
    List<LocalDateTime> agendaOcupada;
    private List<Especialidade> especialidades;

    public Medico(String crm, Double custoDaConsulta, String nome) {
        this.agendaOcupada = new ArrayList<>();
        this.crm = crm;
        this.custoDaConsulta = custoDaConsulta;
        this.nome = nome;
        this.especialidades = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionarEspecialidade(Especialidade especialidade) {
        if (!this.especialidades.contains(especialidade)) {
            this.especialidades.add(especialidade);
        }
    }

    public Double getCustoDaConsulta() {
        return custoDaConsulta;
    }

    public void setCustoDaConsulta(Double custoDaConsulta) {
        this.custoDaConsulta = custoDaConsulta;
    }

    public String getCrm() {
        return crm;
    }

    public void adicionarAgendaOcupada(LocalDateTime agendaOcupada) {
        this.agendaOcupada.add(agendaOcupada);
    }
    public void removerAgendaOcupada(LocalDateTime agendaOcupada) {
        this.agendaOcupada.remove(agendaOcupada);
    }
    public String mostrarInformacoes()
    {
        return "Nome : " + nome + "\nCusto da consulta: " + custoDaConsulta + "\nArea de especialidade" + "\nCrm: " + crm;
    }
    public boolean isDisponivel(LocalDateTime horario) {
        return !this.agendaOcupada.contains(horario);
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }
}
