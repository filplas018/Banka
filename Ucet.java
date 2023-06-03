package org.example;

import java.io.*;

public class Ucet
{
    private File file;


    public Ucet(File file)
    {
        this.file = file;
    } //ucet ma svuj file

    public void saveTransaction(Transakce transaction) throws IOException
    {
        try ( DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(file, true)))) {
            transaction.save(dos);
        }
    }
    public static Ucet getInstance(File dir, int accNum) throws IOException {

        File file = new File(dir, String.format("ucet%d.dat", accNum));
        if (!file.exists()) {
            file.createNewFile();
        }
        return new Ucet(file);
    }

    public String getStatement() throws IOException {
        StringBuilder sb = new StringBuilder();
        try ( DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
            boolean konec = false;

            while (!konec) {
                try {
                    Transakce tr = Transakce.read(dis);
                    sb.append(tr.toString());
                    sb.append(String.format("%n"));
                } catch (EOFException e) {
                    konec = true;
                }
            }

        }
        return sb.toString();
    }


    public String getBillance() throws IOException
    {
        StringBuilder sb = new StringBuilder();
        double ballance = 0;
        double expenses = 0;
        double income = 0;
        try ( DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
            boolean konec = false;

            while (!konec) {
                try {
                    Transakce tr = Transakce.read(dis);
                    double amount =tr.getAmount();
                    if(amount > 0){
                        income+=amount;
                    }
                    else{
                        expenses += amount;
                    }
                    ballance = income + expenses;
                } catch (EOFException e){
                    konec = true;
                }
            }
        }
        return String.format("Zůstatek: %f Přijmy: %f Výdaje: %f",ballance, income,expenses);
    }
    public String getBillance(int month, int year) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        double ballance = 0;
        double expenses = 0;
        double income = 0;
        try ( DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
            boolean end = false;

            while (!end) {
                try {
                    Transakce tr = Transakce.read(dis);
                    if(tr.getDatum()[1] == month && tr.getDatum()[2] == year){
                        double amount =tr.getAmount();
                        if(amount > 0){
                            income+=amount;
                        }
                        else{
                            expenses += amount;
                        }
                        ballance = income + expenses;
                    }

                } catch (EOFException e){
                    end = true;
                }
            }
        }
        return String.format("Obdobi: %d %d | Zůstatek: %f | Přijmy: %f | Výdaje: %f",month,year,ballance, income,expenses);
    }

}
