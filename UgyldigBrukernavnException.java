package com.example.carracinggame;

public class UgyldigBrukernavnException extends RuntimeException{
    /**
     *
     * @param melding error-melding
     */
    public UgyldigBrukernavnException(String melding) {
        super(melding);
    }
}
