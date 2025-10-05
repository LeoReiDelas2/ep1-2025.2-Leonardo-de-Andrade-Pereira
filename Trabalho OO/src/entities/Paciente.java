package entities;

import Services.Hospital;
import utils.Searcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String nome;
    private String cpf;
    private int idade;
    private List<Internacao> internacoes =  new ArrayList<>();
    private List<Consultas> historico =  new ArrayList<>();

    public Paciente(String nome, String cpf, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public Paciente(String nome, String cpf, int idade, List<Consultas> historico, List<Internacao> internacoes) {
        this.cpf = cpf;
        this.historico = historico;
        this.idade = idade;
        this.internacoes = internacoes;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionarInternacao(Internacao internacao)
    {
        if (this.internacoes == null) {
            this.internacoes = new ArrayList<>();
        }
        this.internacoes.add(internacao);

    }

    public List<Internacao> getInternacoes() {
        return this.internacoes;
    }

    public void setInternacoes(List<Internacao> internacoes) {
        this.internacoes = internacoes;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nCPF: " + cpf + "\nIdade: " + idade;
    }
    public String concatenarInternacoes(List<Internacao> internacoes)
    {
        String dados = "";
        for ( Internacao internacao : internacoes)
        {
            String dadointernacao = internacao.getResponsavel().getCrm() + " " + internacao.getDataEntrada() + " ";
            dados += dadointernacao;
        }
        return dados;
    }
    public String concaternarHistorico(List<Consultas> historico)
    {
        String dados = "";
        for (Consultas consulta : historico)
        {
            String dadoconsulta = consulta.getMedico().getCrm() + " " + consulta.getDataHora() + " ";
            dados += dadoconsulta;
        }
        return dados;
    }
    public String objectToString()
    {
        return nome + ";" + cpf + ";" + idade + ";" + concatenarInternacoes(internacoes) + ";" + concaternarHistorico(historico);
    }
    public static Paciente fromString(String string)
    {
        String dados[] = string.split(";");
        List<Consultas> historico = new ArrayList<>();
        String stringconsultas[] = dados[4].split(" ");
        if (!(stringconsultas == null || stringconsultas.length <=1))
        {
            for (int i = 0; i < stringconsultas.length; i+=2)
            {
                LocalDateTime data = LocalDateTime.parse(stringconsultas[i+1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Consultas consulta = Searcher.getConsultas(dados[1], stringconsultas[i], data);
                historico.add(consulta);
            }
        }
        List<Internacao> internacao = Hospital.buscarInternacaoAtivaPorPaciente(dados[2]);
        Paciente paciente = new Paciente(dados[0], dados[1], Integer.parseInt(dados[2]), historico, internacao);
        return paciente;
    }
}
