package com.example.carracinggame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Poengtavle {
    protected ArrayList<Spiller> poengliste;
    private static final String FIL_NAVN = "HøyestePoeng.ser";

    public Poengtavle() {
        this.poengliste = lastInnPoeng();
    }

    /**
     *
     * @return Liste med spillere
     */
    //leser inn poeng fra fil, visst filen eksister
    private ArrayList<Spiller> lastInnPoeng() {
        File fil = new File(FIL_NAVN);
        if(!fil.exists()) {
            return new ArrayList<Spiller>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fil))) {
             return (ArrayList<Spiller>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            return new ArrayList<Spiller>();
        }
    }

    /**
     *
     * @param spiller nåværende spiller
     */
    //metode for å legge til poeng
    public void leggTilPoeng(Spiller spiller) {
        poengliste.add(spiller);
        System.out.println(spiller.getAntSekSpilt());

        Collections.sort(poengliste);

        lagrePoeng();
    }

    private void lagrePoeng() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FIL_NAVN))) {
            oos.writeObject(poengliste);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return tekst-streng med top-5 liste
     */
    public String getTopFem() {
        String ut = "Top fem poengsummer: " + '\n';
        if(poengliste.isEmpty()) {
            ut = "Ingen poengsummer lagret";
        }
        else {
            int nr = 1;
            Collections.sort(poengliste);
            for(int i=poengliste.size()-1;i>=poengliste.size()-5;i--) {
                ut += (nr++) + ". " + poengliste.get(i).getBrukernavn() + " " +
                        String.format("%.2f", poengliste.get(i).getAntSekSpilt()) + '\n';
            }
        }
        return ut;
    }
}
