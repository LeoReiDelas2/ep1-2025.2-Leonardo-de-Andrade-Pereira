package Services;

import entities.*;

import java.util.ArrayList;
import java.util.List;

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

}
