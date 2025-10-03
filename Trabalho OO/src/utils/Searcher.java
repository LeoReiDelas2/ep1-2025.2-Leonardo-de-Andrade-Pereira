package utils;

import entities.Medico;
import entities.Paciente;

import java.util.List;

public class Searcher
{
    static public Medico getMedico(String crm, List<Medico> medicos)
    {
        for(Medico medico : medicos){
            if(medico.getCrm().equals(crm)){
                return medico;
            }
        }
        return null;
    }
    static public Paciente getPaciente(String cpf, List<Paciente> pacientes)
    {
        for(Paciente paciente : pacientes){
            if(paciente.getCpf().equals(cpf)){
                return paciente;
            }
        }
        return null;
    }
}
