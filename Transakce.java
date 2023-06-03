package org.example;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Transakce
{
    private int day, month, year;
    private double amount;

    public Transakce(int day, int month, int year, double amount)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.amount = amount;
    }

    public int[] getDatum()
    {
        return new int[]{day, month, year};
    }

    public void setDatum(int day, int month, int year)
    {
        this.year = year;
        this.month = month;
        this.year = year;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public void save(DataOutput d) throws IOException
    {
        d.writeInt(day);
        d.writeInt(month);
        d.writeInt(year);
        d.writeDouble(amount);

    }
    public static Transakce read(DataInput di) throws IOException{
        return new Transakce(di.readInt(),di.readInt(),di.readInt(),di.readDouble());
    }

    @Override
    public String toString() {
        return "Transakce{" + "den=" + day + ", mesic=" + month + ", rok=" + year + ", castka=" + amount + '}';
    }
}
