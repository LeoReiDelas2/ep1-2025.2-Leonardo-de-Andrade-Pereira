package entities;

public class PlanoDeSaude
{
    private Double taxaDeDesconto;
    private Boolean especial;
    private Double economia;

    public PlanoDeSaude(Boolean especial, Double taxaDeDesconto) {
        this.economia = 0.0;
        this.especial = especial;
        this.taxaDeDesconto = taxaDeDesconto;
    }

    public PlanoDeSaude(Double economia, Boolean especial, Double taxaDeDesconto) {
        this.economia = economia;
        this.especial = especial;
        this.taxaDeDesconto = taxaDeDesconto;
    }

    public Double getEconomia() {
        return economia;
    }

    public Boolean getEspecial() {
        return especial;
    }

    public Double getTaxaDeDesconto() {
        return taxaDeDesconto;
    }

    public void setTaxaDeDesconto(Double taxaDeDesconto) {
        this.taxaDeDesconto = taxaDeDesconto;
    }
    public void aumentarEconomia(Double economia){
        this.economia += economia;
    }
    public void diminuirEconomia(Double economia){
        this.economia -= economia;
    }

}
