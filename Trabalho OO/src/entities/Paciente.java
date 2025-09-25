package entities;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String nome;
    private Long cpf;
    private int idade;
    private List<Internacao> internacoes =  new ArrayList<>();
    private List<Consultas> historico =  new ArrayList<>();

    public Paciente(String nome, Long cpf, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
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
}
