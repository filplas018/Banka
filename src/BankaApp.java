package org.example;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankaApp
{
    private static final Scanner sc = new Scanner(System.in);
    private static File dir;
    private static Ucet account;

    public static void main(String[] args)
    {
        boolean end;
        try {


            do {
                int sel;
                if(dir != null && account != null){
                    printMenu();

                    sel = getOp();
                    end = handleSelection(sel);
                }
                else{
                    printStartupMenu();
                    sel = getOp();
                    end = handleStartupSelection(sel);
                }

            } while (!end);
        } catch (Exception ex) {
            System.out.println("Neocekavana chyba v programu, program bude ukoncen");
            ex.printStackTrace();
        } catch (Error err) {
            System.out.println("Chyba JVM, nelze pokracovat");
            err.printStackTrace();
        }
        System.out.println("Loucim se");
    }
    private static int getOp() {
        int num;
        try {
            System.out.print("Zadejte cislo ulohy: ");
            num = sc.nextInt();
        } catch (InputMismatchException ex) {
            num = -1;
        }
        sc.nextLine();
        return num;
    }
    private static boolean handleStartupSelection(int sel){
        switch(sel){
            case 0 ->
            {
                return true;
            }
            case 1 ->{
                getDir();
            }
            case 2 ->{
                accountSel();
            }
            default ->
                    System.out.println();
        }
        return false;
    }
    private static boolean handleSelection(int sel) throws IOException
    {
        switch (sel) {
            case 0 -> {
                return true;
            }
            case 1 ->
                    accountSel();
            case 2 ->
                    accountStatement();
            case 3 ->
                    accountBillance();
            case 4 ->
                    accountBillanceInMonth();
            case 5 ->
                    writeTransaction();
            case 6 ->
                    getDir();
            default ->
                    System.out.println();
        }
        return false;
    }
    private static void printStartupMenu(){
        System.out.println("______________________");
        System.out.println("[Pocatecni nastaveni]");
        System.out.println("1. Vyber adresare");
        System.out.println("2. Vyber uctu");
        System.out.println("0. Konec programu");
        System.out.println("______________________");
    }
    private static void printMenu() {
        System.out.println("______________________");
            System.out.println("1. Vyber uctu");
            System.out.println("2. Vypis pohybu na uctu");
            System.out.println("3. Vypis bilance");
            System.out.println("4. Vypis bilance v mesici");
            System.out.println("5. Zaznam transakce");
            System.out.println("6. Zmena adresare");
            System.out.println("0. Konec programu");
        System.out.println("______________________");
    }
    private static void getDir() {
        System.out.print("Zadej cestu k adresari: ");
        File file = new File(sc.nextLine().trim());
        while (!file.isDirectory()) {
            System.out.print("Zadej cestu k adresari: ");
            file = new File(sc.nextLine().trim());
        }
        dir = file;
    }
    private static void accountSel(){
        System.out.println("Zadejte cislo uctu: ");
        try {
            int cislo = sc.nextInt();
            Ucet acc = Ucet.getInstance(dir, cislo);
            account = acc;
        } catch (IOException ex) {
            System.out.println("Nebylo mozne vytvorit soubor uctu pro zadane cislo");
        } catch (IllegalArgumentException ex) {
            System.out.println("Zadano cislo nesplnujici pozadavky na cislo uctu");
        } catch (InputMismatchException ex) {
            System.out.println("Chybne zadane cislo");
        }
    }
    private static void accountStatement() throws IOException
    {
        String vypis = account.getStatement();
        System.out.println(vypis);
    }
    private static void accountBillanceInMonth() throws IOException
    {
        try{
            System.out.println("Zadejte mesic:");
            int m = sc.nextInt();
            System.out.println("Zadejte rok:");
            int y = sc.nextInt();
            String bilance = account.getBillance(m,y);
            System.out.println(bilance);
        }catch (InputMismatchException ex) {
            System.out.println("Chybne zadane cislo");
        }

    }
    private static void accountBillance() throws IOException
    {
        String bilance = account.getBillance();
        System.out.println(bilance);
    }
    private static void writeTransaction() throws IOException {

        try{
            System.out.println("Zadejte den:");
            int d = sc.nextInt();
            System.out.println("Zadejte mesic:");
            int m = sc.nextInt();
            System.out.println("Zadejte rok:");
            int y = sc.nextInt();
            System.out.println("Zadejte castku:");
            double amount = sc.nextDouble();
            Transakce tr = new Transakce(d, m, y, amount);
            account.saveTransaction(tr);

        }catch (InputMismatchException ex) {
            System.out.println("Chybne zadane cislo");
        }
    }
}
