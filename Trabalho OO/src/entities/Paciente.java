package entities;

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
}
