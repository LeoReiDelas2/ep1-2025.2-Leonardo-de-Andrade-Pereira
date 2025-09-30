package Services;

import entities.*;
import utils.*;
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

    public Hospital() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.internacoes = new ArrayList<>();
        this.planoDeSaude = new ArrayList<>();
        this.quartos = new ArrayList<>();
    }

    static public void cadastrarPlanoDeSaude(Scanner scanner)
    {
            System.out.println("Bem-vindo ao modo de cadastro do Plano de Saúde: ");
            System.out.print("Ele será do tipo especial? (y/n)");
            Character especial = scanner.next().charAt(0);
            System.out.print("Cadastre um n");



    }


}
