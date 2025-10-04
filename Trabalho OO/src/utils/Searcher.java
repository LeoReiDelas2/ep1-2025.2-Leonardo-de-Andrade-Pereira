package utils;

import Services.Hospital;
import entities.Consultas;
import entities.Medico;
import entities.Paciente;

import java.time.LocalDateTime;
import java.util.List;

public class Searcher
{
    static public Medico getMedicoBusca(String crm, List<Medico> medicos)
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
    static public Consultas  getConsultas(String cpf, String crm, LocalDateTime data)
    {
        Medico medico =  getMedicoBusca(crm, Hospital.getMedicos());
        Paciente paciente = getPaciente(cpf, Hospital.getPacientes());
        List<Consultas> consultas = Hospital.getHistoricu();
        for (Consultas consulta : consultas)
        {
            if(!consulta.getMedico().getCrm().equals(medico.getCrm())){
                continue;
            }
            if(!consulta.getPaciente().getCpf().equals(paciente.getCpf()))
            {
                continue;
            }
            if(!consulta.getDataHora().equals(data))
            {
                continue;
            }
            return consulta;
        }
        return null;
    }
}
