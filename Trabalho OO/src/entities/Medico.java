package entities;
import enums.AreaDeEspecialidade;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class Medico {
    private String nome;
    private String crm;
    private Double custoDaConsulta;
    private AreaDeEspecialidade areaDeEspecialidade;
    List<LocalDateTime> agendaOcupada;

    public Medico(AreaDeEspecialidade areaDeEspecialidade, String crm, Double custoDaConsulta, String nome) {
        this.agendaOcupada = new ArrayList<>();
        this.areaDeEspecialidade = areaDeEspecialidade;
        this.crm = crm;
        this.custoDaConsulta = custoDaConsulta;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public AreaDeEspecialidade getAreaDeEspecialidade() {
        return areaDeEspecialidade;
    }

    public void adicionarAgendaOcupada(LocalDateTime agendaOcupada) {
        this.agendaOcupada.add(agendaOcupada);
    }
    public void removerAgendaOcupada(LocalDateTime agendaOcupada) {
        this.agendaOcupada.remove(agendaOcupada);
    }
    public String mostrarInformacoes()
    {
        return "Nome : " + nome + "\nCusto da consulta: " + custoDaConsulta + "\nArea de especialidade" + areaDeEspecialidade + "\nCrm: " + crm;
    }
    public boolean isDisponivel(LocalDateTime horario) {
        return !this.agendaOcupada.contains(horario);
    }

}
