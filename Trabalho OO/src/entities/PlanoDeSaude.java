package entities;
import utils.Searcher;

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

    public PlanoDeSaude(Map<Especialidade, Double> coberturaDescontos, Double economia, Boolean especial, String nome) {
        this.coberturaDescontos = coberturaDescontos;
        this.economia = economia;
        this.especial = especial;
        this.nome = nome;
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
    @Override
    public String toString() {
        StringBuilder coberturas = new StringBuilder();
        if (coberturaDescontos != null && !coberturaDescontos.isEmpty()) {
            for (Map.Entry<Especialidade, Double> entry : coberturaDescontos.entrySet()) {
                coberturas.append("\n    - ")
                        .append(entry.getKey().getNome())
                        .append(": ")
                        .append(String.format("%.0f%%", entry.getValue() * 100))
                        .append(" de desconto");
            }
        } else {
            coberturas.append("\n    Nenhuma cobertura cadastrada");
        }
        return "--- Plano de Saúde ---\n" +
                "  Nome: " + nome + "\n" +
                "  Tipo: " + (especial ? "Especial (Internação Curta Gratuita)" : "Padrão") + "\n" +
                String.format("  Economia Total: R$ %.2f\n", economia) +
                "  Coberturas:" + coberturas.toString() + "\n" +
                "----------------------";
    }
    public String objectToString() {
        StringBuilder coberturasStr = new StringBuilder();
        if (coberturaDescontos != null && !coberturaDescontos.isEmpty()) {
            boolean primeiro = true;
            for (Map.Entry<Especialidade, Double> entry : coberturaDescontos.entrySet()) {
                if (!primeiro) {
                    coberturasStr.append("|");
                }
                coberturasStr.append(entry.getKey().getNome())
                        .append(":")
                        .append(entry.getValue());
                primeiro = false;
            }
        }
        return String.join(";",
                this.nome,
                String.valueOf(this.especial),
                this.economia.toString(),
                coberturasStr.toString()
        );
    }
    public static PlanoDeSaude fromString(String linha, List<Especialidade> todasEspecialidades) {
        try {
            linha = linha.replace("@", "").trim();
            String[] dados = linha.split(";", -1);
            if (dados.length < 3) {
                System.err.println("Erro: Linha com formato inválido (poucos campos): " + linha);
                return null;
            }
            String nome = dados[0].trim();
            boolean especial = Boolean.parseBoolean(dados[1].trim());
            double economia = Double.parseDouble(dados[2].trim());
            Map<Especialidade, Double> coberturaDescontos = new HashMap<>();
            if (dados.length > 3 && !dados[3].trim().isEmpty()) {
                String[] coberturas = dados[3].split("\\|");
                for (String cobertura : coberturas) {
                    if (!cobertura.trim().isEmpty()) {
                        String[] partes = cobertura.split(":");
                        if (partes.length == 2) {
                            String nomeEspecialidade = partes[0].trim();
                            double desconto = Double.parseDouble(partes[1].trim());

                            Especialidade especialidade = Searcher.buscarEspecialidadePorNome(
                                    nomeEspecialidade,
                                    todasEspecialidades
                            );
                            if (especialidade != null) {
                                coberturaDescontos.put(especialidade, desconto);
                            } else {
                                System.err.println("Aviso: Especialidade '" + nomeEspecialidade +
                                        "' não encontrada para o plano '" + nome + "'");
                            }
                        }
                    }
                }
            }
            PlanoDeSaude plano = new PlanoDeSaude(coberturaDescontos, economia, especial, nome);
            return plano;
        } catch (Exception e) {
            System.err.println("Erro ao converter linha para PlanoDeSaude: " + linha);
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
