package entities;

import java.time.LocalDateTime;

public class Internacao {
    private Paciente paciente;
    private Medico responsavel;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private Quarto quarto;
    private boolean ativa;
    private Double custototal;

    public Internacao() {
    }

    public Internacao(Paciente paciente, Quarto quarto, Medico responsavel) {
        this.custototal = 0.0;
        this.dataEntrada = LocalDateTime.now();
        this.dataSaida = null;
        this.paciente = paciente;
        this.quarto = quarto;
        this.responsavel = responsavel;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public Medico getResponsavel() {
        return responsavel;
    }

    public Double getCustototal() {
        return custototal;
    }

    public Paciente getPaciente()
    {
        return paciente;
    }

    public void registrarAlta(Double custofinal)
    {
        this.dataSaida = LocalDateTime.now();
        this.custototal = custofinal;
        this.quarto.desocupar();
    }

}
