package entities;
import Services.Hospital;
import utils.Searcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class Medico {
    private String nome;
    private String crm;
    private Double custoDaConsulta;
    private List<LocalDateTime> agendaOcupada;
    private static List<Especialidade> especialidades;

    public Medico(String crm, Double custoDaConsulta, String nome) {
        this.agendaOcupada = new ArrayList<>();
        this.crm = crm;
        this.custoDaConsulta = custoDaConsulta;
        this.nome = nome;
        this.especialidades = new ArrayList<>();
    }

    public Medico(List<LocalDateTime> agendaOcupada, String crm, Double custoDaConsulta, List<Especialidade> especialidades, String nome) {
        this.agendaOcupada = agendaOcupada;
        this.crm = crm;
        this.custoDaConsulta = custoDaConsulta;
        this.especialidades = especialidades;
        this.nome = nome;
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
    public static List<Especialidade> getEspecialidades1() {
        return especialidades;
    }
    public String objectToString()
    {
        return nome + ";" + crm + ";" + custoDaConsulta + ";" + concatenarEspecialidades(especialidades) + ";" + concatenarHorarios(agendaOcupada);
    }
    public String concatenarEspecialidades(List<Especialidade> especialidades)
    {
        String dados = "";
        for ( Especialidade especialidade : especialidades)
        {
            String dadoespecialidade = especialidade.getNome() + " ";
            dados += dadoespecialidade;
        }
        return dados;
    }
    public String concatenarHorarios(List<LocalDateTime> horarios) {
        StringBuilder dados = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
        for (LocalDateTime horario : horarios) {
            String horarioFormatado = horario.format(formatter);
            dados.append(horarioFormatado).append(" ");
        }
        return dados.toString().trim();
    }
    public static Medico fromString(String linha) {
        try {
            String[] dados = linha.split(";");
            String nome = dados[0];
            String crm = dados[1];
            double custo = Double.parseDouble(dados[2]);
            Medico medico = new Medico(nome, custo, crm);
            if (dados.length > 3 && !dados[3].isBlank()) {
                String[] nomesEspecialidades = dados[3].split(" ");
                for (String nomeEsp : nomesEspecialidades) {
                    Especialidade espEncontrada = Searcher.buscarEspecialidadePorNome(nomeEsp, getEspecialidades1());
                    if (espEncontrada != null) {
                        medico.adicionarEspecialidade(espEncontrada);
                    }
                }
            }
            if (dados.length > 4 && !dados[4].isBlank()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
                String[] datasString = dados[4].split(" ");
                for (String dataStr : datasString) {
                    try {
                        LocalDateTime data = LocalDateTime.parse(dataStr, formatter);
                        medico.adicionarAgendaOcupada(data);
                    } catch (Exception e) {
                        System.err.println("Aviso: Formato de data inválido na agenda do médico: " + dataStr);
                    }
                }
            }
            return medico;
        } catch (Exception e) {
            System.err.println("Erro ao converter linha para Medico: " + linha + " | Erro: " + e.getMessage());
            return null;
        }
    }
    @Override
    public String toString() {
        return "Nome: " + nome + "\nCrm: " + crm + "\nCusto da consulta: " + custoDaConsulta;
    }
}
