package entities;

import java.util.List;

public class PacienteEspecial extends Paciente
{
    private PlanoDeSaude planoDeSaude;

    public PacienteEspecial(String nome, String cpf, int idade, PlanoDeSaude planoDeSaude) {
        super(nome, cpf, idade);
        this.planoDeSaude = planoDeSaude;
    }

    public PacienteEspecial(String nome, String cpf, int idade, List<Consultas> historico, List<Internacao> internacoes, PlanoDeSaude planoDeSaude) {
        super(nome, cpf, idade, historico, internacoes);
        this.planoDeSaude = planoDeSaude;
    }

    public PlanoDeSaude getPlanoDeSaude() {
        return planoDeSaude;
    }

    public void setPlanoDeSaude(PlanoDeSaude planoDeSaude) {
        this.planoDeSaude = planoDeSaude;
    }
    @Override
    public String toString() {
        return "--- Informações do Paciente Especial ---\n" +
                "  Nome: " + getNome() + "\n" +
                "  CPF: " + getCpf() + "\n" +
                "  Idade: " + getIdade() + " anos\n" +
                "  Plano de Saúde: " + planoDeSaude.getNome() + "\n" +
                "  Internações: " + (getInternacoes() != null ? getInternacoes().size() : 0) + "\n" +
                "  Consultas realizadas: " + (getHistorico() != null ? getHistorico().size() : 0) + "\n" +
                "-----------------------------------------";
    }
}
