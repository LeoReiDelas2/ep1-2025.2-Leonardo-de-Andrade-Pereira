package application;

import java.util.Scanner;
import Services.*;
import enums.*;
import utils.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menus Menus = new Menus();
        Hospital hospital = new Hospital();
        Scanner sc = new Scanner(System.in);
        hospital.carregarEspecialidadesPadrao();
        int escolha;
        do{
            escolha = Menus.exibirMenuPrincipal(sc, hospital);
        }while(escolha !=0);
        sc.close();
        System.exit(0);
    }
}