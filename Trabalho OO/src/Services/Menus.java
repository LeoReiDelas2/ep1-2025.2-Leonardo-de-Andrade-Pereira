package Services;

import utils.InputHandler;

import java.util.Scanner;

public class Menus
{
    public void menuCadastros(Scanner scanner, Hospital Hospital)
    {
        System.out.print("Seja bem-vindo ao menu de cadastro!\n");
        Integer escolha = InputHandler.digitarIntIntervalo("" +
                "Digite 1 para cadastrar um paciente;\n" +
                "Digite 2 para cadastrar um médico;\n" +
                "Digite 3 para cadastrar um plano de saúde;\n" +
                "Digite 0 para voltar ao menu anterior.", scanner, 0, 3);
        switch (escolha) {
            case 0:
                return;
            case 2:
                Hospital.cadastrarMedico(scanner);
                break;
            case 3:
                Hospital.cadastrarPlanoDeSaude(scanner);
                break;
            }
    }
}
