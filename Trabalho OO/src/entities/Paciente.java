package entities;

public class Paciente {
    private String nome;
    private int cpf;
    private int idade;
    public String historico;
    public String internacoes;

    public Paciente(String nome, int cpf, int idade, String historico, String internacoes) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.historico = historico;
        this.internacoes = internacoes;
    }
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInternacoes() {
        return internacoes;
    }

    public void setInternacoes(String internacoes) {
        this.internacoes = internacoes;
    }

    public int getCpf() {
        return cpf;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
