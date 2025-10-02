package entities;

public class PacienteEspecial extends Paciente
{
    private PlanoDeSaude planoDeSaude;

    public PacienteEspecial(String nome, String cpf, int idade, PlanoDeSaude planoDeSaude) {
        super(nome, cpf, idade);
        this.planoDeSaude = planoDeSaude;
    }
}
