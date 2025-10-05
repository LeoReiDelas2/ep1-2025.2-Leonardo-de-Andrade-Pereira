package entities;

import utils.Searcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

    public Internacao(boolean ativa, Double custototal, LocalDateTime dataEntrada, LocalDateTime dataSaida, Paciente paciente, Quarto quarto, Medico responsavel) {
        this.ativa = ativa;
        this.custototal = custototal;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
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

    public Quarto getQuarto() {
        return quarto;
    }

    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String dataSaidaFormatada;
        if (dataSaida == null) {
            dataSaidaFormatada = "Ainda Internado";
        } else {
            dataSaidaFormatada = dataSaida.format(formatter);
        }
        return "--- Resumo da Internação ---\n" +
                "  Paciente: " + paciente.getNome() + "\n" +
                "  Médico Resp.: " + responsavel.getNome() + "\n" +
                "  Quarto: " + quarto.getNumero() + "\n" +
                "  Data de Entrada: " + dataEntrada.format(formatter) + "\n" +
                "  Data de Saída: " + dataSaidaFormatada + "\n" +
                String.format("  Custo Total: R$ %.2f\n", custototal) +
                "  Status: " + (isAtiva() ? "Ativa" : "Finalizada") + "\n" +
                "----------------------------";
    }
    public String objectToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
        String dataEntradaStr = this.dataEntrada.format(formatter);
        String dataSaidaStr = (this.dataSaida == null) ? "" : this.dataSaida.format(formatter);
        return String.join(";",
                this.paciente.getCpf(),
                this.responsavel.getCrm(),
                dataEntradaStr,
                dataSaidaStr,
                this.quarto.getNumero().toString(),
                String.valueOf(this.ativa),
                this.custototal.toString()
        );
    }
    public static Internacao fromString(String linha,
                                        List<Paciente> todosPacientes,
                                        List<Medico> todosMedicos) {
        try {
            linha = linha.replace("@", "").trim();
            String[] dados = linha.split(";", -1);
            if (dados.length < 7) {
                System.err.println("Erro: Linha com formato inválido (poucos campos): " + linha);
                return null;
            }
            String pacienteCpf = dados[0].trim();
            String medicoCrm = dados[1].trim();
            String dataEntradaStr = dados[2].trim();
            String dataSaidaStr = dados[3].trim();
            String numeroQuartoStr = dados[4].trim();
            String ativaStr = dados[5].trim();
            String custoTotalStr = dados[6].trim();
            Paciente paciente = Searcher.getPaciente(pacienteCpf, todosPacientes);
            if (paciente == null) {
                System.err.println("Aviso: Paciente com CPF " + pacienteCpf + " não encontrado.");
                return null;
            }
            Medico medico = Searcher.getMedicoBusca(medicoCrm, todosMedicos);
            if (medico == null) {
                System.err.println("Aviso: Médico com CRM " + medicoCrm + " não encontrado.");
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
            LocalDateTime dataEntrada = LocalDateTime.parse(dataEntradaStr, formatter);
            LocalDateTime dataSaida = dataSaidaStr.isBlank() ? null : LocalDateTime.parse(dataSaidaStr, formatter);
            Integer numeroQuarto = Integer.parseInt(numeroQuartoStr);
            Quarto quarto = new Quarto(numeroQuarto);
            boolean ativa = Boolean.parseBoolean(ativaStr);
            if (ativa) {
                quarto.ocupar();
            }
            Double custoTotal = Double.parseDouble(custoTotalStr);
            Internacao internacao = new Internacao(ativa,custoTotal, dataEntrada, dataSaida, paciente, quarto, medico);
            return internacao;
        } catch (Exception e) {
            System.err.println("Erro ao converter linha para Internacao: " + linha);
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
