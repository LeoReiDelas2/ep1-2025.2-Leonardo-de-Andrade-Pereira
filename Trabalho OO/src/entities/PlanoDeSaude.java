package entities;

import enums.AreaDeEspecialidade;

import java.util.ArrayList;
import java.util.List;

public class PlanoDeSaude
{
    private Boolean especial;
    private Double economia;
    private List<Enum> areaDeEspecialidade = new ArrayList<>();
    private List<Double> descontos = new ArrayList<>();

    public PlanoDeSaude(Boolean especial, List<Enum> areaDeEspecialidade, List<Double> descontos) {
        this.economia = 0.0;
        this.especial = especial;
        this.areaDeEspecialidade = areaDeEspecialidade;
        this.descontos = descontos;
    }

    public PlanoDeSaude(Double economia, Boolean especial, List<Enum> areaDeEspecialidade, List<Double> descontos) {
        this.economia = economia;
        this.especial = especial;
        this.areaDeEspecialidade = areaDeEspecialidade;
        this.descontos = descontos;
    }

    public Double getEconomia() {
        return economia;
    }

    public Boolean getEspecial() {
        return especial;
    }

    public void aumentarEconomia(Double economia){
        this.economia += economia;
    }
    public void diminuirEconomia(Double economia){
        this.economia -= economia;
    }

}
