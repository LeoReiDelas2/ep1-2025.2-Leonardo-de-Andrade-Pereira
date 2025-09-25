package utils;

import entities.Medico;

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
}
