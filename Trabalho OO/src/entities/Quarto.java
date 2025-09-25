package entities;

public class Quarto
{
    private Integer numero;
    private Boolean isOcupado;

    public Quarto(Boolean isOcupado, Integer numero) {
        this.isOcupado = false;
        this.numero = numero;
    }

    public Boolean getOcupado() {
        return isOcupado;
    }

    public void ocupar(){
        this.isOcupado = true;
    }
    public void desocupar(){
        this.isOcupado = false;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

}
