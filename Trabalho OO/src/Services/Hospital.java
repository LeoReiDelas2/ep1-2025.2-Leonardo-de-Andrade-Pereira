package Services;

import entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hospital {
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Consultas> consultas;
    private List<Internacao> internacoes;
    private List<PlanoDeSaude> planoDeSaude;
    private List<Quarto> quartos;
    private List<Especialidade> especialidades;

    public Hospital() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.internacoes = new ArrayList<>();
        this.planoDeSaude = new ArrayList<>();
        this.quartos = new ArrayList<>();
        this.especialidades = new ArrayList<>();
    }
    private void carregarEspecialidadesPadrao() {
        this.especialidades.add(new Especialidade("Cardiologia"));
        this.especialidades.add(new Especialidade("Pediatria"));
        this.especialidades.add(new Especialidade("Ortopedia"));
        this.especialidades.add(new Especialidade("Clínica Geral"));
        this.especialidades.add(new Especialidade("Dermatologia"));
    }

    private Especialidade getOrCreateEspecialidadePorNome(String nome) {
        String nomeArrumado = nome.trim();
        for (Especialidade esp : this.especialidades) {
            if (esp.getNome().equalsIgnoreCase(nomeArrumado)) {
                return esp;
            }
        }
        System.out.println("-> Nova especialidade '" + nomeArrumado + "' criada no sistema.");
        Especialidade novaEspecialidade = new Especialidade(nomeArrumado);
        this.especialidades.add(novaEspecialidade);
        return novaEspecialidade;
    }

    public void cadastrarMedico(Scanner scanner) {
        try {
            System.out.println("\n--- Cadastro de Novo Médico ---");
            System.out.print("Digite o nome do médico: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o CRM do médico: ");
            String crm = scanner.nextLine();
            System.out.print("Digite o custo padrão da consulta: ");
            double custo = scanner.nextDouble();
            scanner.nextLine();
            Medico novoMedico = new Medico(crm, custo, nome);

            System.out.println("\n--- Adicionar Especialidades ---");
            while (true) {
                System.out.print("Digite o nome de uma especialidade para adicionar (ou digite 'fim' para terminar): ");
                String nomeEspecialidade = scanner.nextLine();
                if (nomeEspecialidade.equalsIgnoreCase("fim")) {
                    break;
                }
                if (nomeEspecialidade.isBlank()) {
                    System.out.println("Nome da especialidade não pode ser vazio.");
                    continue;
                }
                Especialidade especialidade = getOrCreateEspecialidadePorNome(nomeEspecialidade);
                novoMedico.adicionarEspecialidade(especialidade);
                System.out.println("Especialidade '" + especialidade.getNome() + "' adicionada ao Dr(a). " + nome);
            }
            this.medicos.add(novoMedico);
            System.out.println("\n--- Médico Cadastrado com Sucesso! ---");
            System.out.println(novoMedico);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. O custo da consulta deve ser um número.");
            scanner.nextLine();
        }
    }
    static public void cadastrarPlanoDeSaude(Scanner scanner)
    {
        System.out.println("-------------------------------" +
                "\nCadastro de Novo Plano de Saúde\n" +
                "-------------------------------");

    }


}
