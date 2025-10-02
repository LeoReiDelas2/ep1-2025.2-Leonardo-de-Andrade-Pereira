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
            }
            this.medicos.add(novoMedico);
            System.out.println("\n--- Médico Cadastrado com Sucesso! ---");
            System.out.println(novoMedico);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. O custo da consulta deve ser um número.");
            scanner.nextLine();
        }
    }
    public void cadastrarPaciente(Scanner scanner) {
        try {
            System.out.println("\n--- Cadastro de Novo Paciente ---");
            System.out.print("Digite o nome do paciente: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o CPF do paciente: ");
            String cpf = scanner.nextLine();
            System.out.print("Digite a idade do paciente: ");
            Integer idade = scanner.nextInt();
            scanner.nextLine();
            System.out.print("O paciente possui plano de saúde? (S/N) ");
            String planoSaude = scanner.nextLine();
            Paciente novoPaciente = null;
            int contador = 1;
            if (planoSaude.equalsIgnoreCase("S")){
                if (this.planoDeSaude.isEmpty()) {
                    System.out.println("Não há um plano cadastrado, é necessário cadastrar um plano primeiro." +
                            "\nO paciente será cadastrado como um paciente comum.");
                    novoPaciente = new Paciente(nome, cpf, idade);
                }
                else{
                    for (PlanoDeSaude plano : this.planoDeSaude) {
                        System.out.println(contador + " - " + plano);
                        contador++;
                    }
                    System.out.println("Digite o número do plano do paciente: ");
                    Integer numeroPlano = scanner.nextInt();
                    scanner.nextLine();
                    PlanoDeSaude planoescolhido = this.planoDeSaude.get(numeroPlano-1);
                    novoPaciente = new PacienteEspecial(nome, cpf, idade, planoescolhido);
                }
            }
            else {
                Paciente paciente = new Paciente(nome, cpf, idade);
            }
            this.pacientes.add(novoPaciente);
            System.out.println("\n--- Paciente Cadastrado com Sucesso! ---");
            System.out.println(novoPaciente);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. A idade deve ser um número.");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: Opção de plano de saúde inválida. Tente o cadastro novamente.");
        }

    }
    static public void cadastrarPlanoDeSaude(Scanner scanner)
    {
        System.out.println("-------------------------------" +
                "\nCadastro de Novo Plano de Saúde\n" +
                "-------------------------------");

    }


}
