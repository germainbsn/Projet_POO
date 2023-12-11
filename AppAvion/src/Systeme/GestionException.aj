package Systeme;

import Model.Droit;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public aspect GestionException {


    pointcut allMethodsException(): execution(* Main.*(..));

    before() :allMethodsException() {


    }

    after() throwing(InputMismatchException e) : allMethodsException() {
        System.err.println("Attention, vous n'avez pas rentrer les informations dans le bon format. Retour...");
        Main.scanner = new Scanner(System.in);
        if (Main.clientConnecte == null) {
            Main.afficherMenuPrincipal();
        }
        else{
            Main.afficherMenuConnecte();
        }
    }

    after() throwing(DateTimeParseException e) : allMethodsException() {
        System.err.println("Attention, vous n'avez pas rentrer la date dans le bon format. Retour...");
        Main.scanner = new Scanner(System.in);
        Main.afficherMenuConnecte();
    }
}
