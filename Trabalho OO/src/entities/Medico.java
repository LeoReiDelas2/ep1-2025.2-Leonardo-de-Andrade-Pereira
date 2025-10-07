package entities;
import Services.Hospital;
import utils.Searcher;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class Medico {
    private String nome;
    private String crm;
    private Double custoDaConsulta;
    private List<LocalDateTime> agendaOcupada;
    private List<Especialidade> especialidades;

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
    public List<Especialidade> getEspecialidades1() {
        return especialidades;
    }
    public String objectToString()
    {
        return nome + ";" + crm + ";" + custoDaConsulta + ";" + concatenarEspecialidades(especialidades) + ";" + concatenarHorarios(agendaOcupada);
    }
    public String concatenarEspecialidades(List<Especialidade> especialidades)
    {
        if (especialidades == null || especialidades.isEmpty()) {
            return "";
        }
        String dados = "";
        for (Especialidade especialidade : especialidades)
        {
            String dadoespecialidade = especialidade.getNome() + " ";
            dados += dadoespecialidade;
        }
        if (!dados.isEmpty()) {
            StringBuilder dadosformatado = new StringBuilder(dados);
            dadosformatado.deleteCharAt(dados.length() - 1);
            return dadosformatado.toString();
        }
        return dados;
    }
    public String concatenarHorarios(List<LocalDateTime> horarios) {
        if (horarios == null || horarios.isEmpty()) {
            return "";
        }
        String dados = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
        for (LocalDateTime horario : horarios) {
            String horarioFormatado = horario.format(formatter).trim();
            dados += horarioFormatado + " ";
        }
        if (!dados.isEmpty()) {
            StringBuilder dadosformatado = new StringBuilder(dados);
            dadosformatado.deleteCharAt(dados.length() - 1);
            return dadosformatado.toString();
        }
        return dados;
    }
    public static Medico fromString(String linha) {
        try {
            String[] dados = linha.split(";");
            String nome = dados[0];
            String crm = dados[1];
            double custo = Double.parseDouble(dados[2]);
            Medico medico = new Medico(crm, custo, nome);
            if (dados.length > 3 && !dados[3].isBlank()) {
                String[] nomesEspecialidades = dados[3].split(" ");
                for (String nomeEsp : nomesEspecialidades) {
                    Especialidade especialidade = Hospital.getOrCreateEspecialidadePorNome(nomeEsp);
                    medico.adicionarEspecialidade(especialidade);
                    Hospital.addEspecialidade(especialidade);
                }
            }
            if (dados.length > 4 && !dados[4].trim().isBlank()) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
        StringBuilder especialidadesStr = new StringBuilder();
        if (especialidades != null && !especialidades.isEmpty()) {
            for (int i = 0; i < especialidades.size(); i++) {
                especialidadesStr.append(especialidades.get(i).getNome());
                if (i < especialidades.size() - 1) {
                    especialidadesStr.append(", ");
                }
            }
        } else {
            especialidadesStr.append("Nenhuma");
        }
        return "--- Informações do Médico ---\n" +
                "  Nome: " + nome + "\n" +
                "  CRM: " + crm + "\n" +
                String.format("  Custo da Consulta: R$ %.2f\n", custoDaConsulta) +
                "  Especialidades: " + especialidadesStr.toString() + "\n" +
                "  Horários Ocupados: " + (agendaOcupada != null ? agendaOcupada.size() : 0) + "\n" +
                "------------------------------";
    }
}
