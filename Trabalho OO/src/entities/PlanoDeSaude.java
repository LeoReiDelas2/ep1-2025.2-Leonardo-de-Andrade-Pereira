package entities;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PlanoDeSaude
{
    private String nome;
    private Boolean especial;
    private Double economia;
    private Map<Especialidade, Double> coberturaDescontos;

    public PlanoDeSaude(String nome, Boolean especial) {
        this.economia = 0.0;
        this.especial = especial;
        this.coberturaDescontos = new HashMap<>();
        this.nome  = nome;
    }
    public void adicionarCobertura(Especialidade especialidade, double desconto) {
        if (desconto >= 0.0 && desconto <= 1.0) {
            this.coberturaDescontos.put(especialidade, desconto);
            System.out.println("Cobertura adicionada: " + especialidade.getNome() + " com " + (desconto * 100) + "% de desconto.");
        } else {
            System.out.println("Erro: Desconto inválido. Deve ser entre 0.0 e 1.0.");
        }
    }

    public PlanoDeSaude(Double economia, Boolean especial, List<Enum> areaDeEspecialidade, List<Double> descontos) {
        this.economia = economia;
        this.especial = especial;
    }

    public Double getEconomia() {
        return economia;
    }

    public Boolean getEspecial() {
        return especial;
    }

    public void registrarEconomia(double valorEconomizado) {
        if (valorEconomizado > 0) {
            this.economia += valorEconomizado;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDescontoPara(Especialidade especialidade) {
        return this.coberturaDescontos.getOrDefault(especialidade, 0.0);
    }

}
