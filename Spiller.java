package com.example.carracinggame;


import java.io.Serializable;

public class Spiller implements Comparable<Spiller>, Serializable {
    //instansvariabler
    private String brukernavn;
    private double antSekSpilt;

    //konstruktør
    public Spiller(String brukernavn, double antSekSpilt) {
        setBrukernavn(brukernavn);
        setAntSekSpilt(antSekSpilt);
    }

    //konstruktør uten sekund
    public Spiller(String brukernavn) {
        this(brukernavn, 0.0);
    }

    /**
     *
     * @param antSekSpilt Antall sekunder spiller har overlevd
     */
    public void setAntSekSpilt(double antSekSpilt) {
        //kan ikke være mindre enn 0
        if(antSekSpilt < 0) {
            throw new UgyldigAntSekundException("Ant sekund kan ikke være mindre enn 0");
        }
        this.antSekSpilt = antSekSpilt;
    }

    /**
     *
     * @return antall sekunder
     */
    public double getAntSekSpilt() {
        return antSekSpilt;
    }

    /**
     *
     * @return spilleren sitt bruker navn
     */
    public String getBrukernavn() {
        return brukernavn;
    }

    /**
     *
     * @param brukernavn spilleren sitt brukernavn
     */
    //set metoder for brukernavn med unntakshåndtering
    public void setBrukernavn(String brukernavn) {
        //kan ikke være lengre enn 8 tegn
        if(brukernavn.length() > 20) {
            throw new UgyldigBrukernavnException("Brukernavn kan ikke ha mer enn 12 bokstaver");
        }
        if(brukernavn.length() < 3) {
            throw new UgyldigBrukernavnException("Brukernavn må minst ha tre bokstaver");
        }
        this.brukernavn = brukernavn;
    }

    //toString

    /**
     *
     * @return spiller tekst-streng
     */
    @Override
    public String toString() {
        return brukernavn + " : " + antSekSpilt;
    }


    /**
     *
     * @param s objektet som blir sammenlignet
     * @return
     */
    @Override
    public int compareTo(Spiller s) {
        if(antSekSpilt < s.antSekSpilt) {
            return -1;
        } else if (antSekSpilt > s.antSekSpilt) {
            return 1;
        }
        return 0;
    }
}
