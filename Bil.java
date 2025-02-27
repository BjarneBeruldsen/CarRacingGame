package com.example.carracinggame;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import java.util.List;

import static com.example.carracinggame.CarRacinGame.VINDU_BREDDE;
import static com.example.carracinggame.CarRacinGame.VINDU_HØYDE;

public class Bil extends Figur{
    //finner bredde og lengde på bil
    public final static int BIL_BREDDE = VINDU_BREDDE/6;
    private final static int BIL_HØYDE = VINDU_HØYDE/6;

    //instansvariabler
    protected Color farge;
    protected Group bilGruppe;
    protected Rectangle r;

    /**
     *
     * @param farge Bilens farge.
     */
    public Bil(Color farge) {
        super((VINDU_BREDDE/2)-BIL_BREDDE/2, VINDU_HØYDE-BIL_HØYDE-5);
        this.farge = farge;
        bilGruppe = lagBilGruppe();
    }

    //parameterløs konstruktør
    public Bil() {
        this(Color.rgb(245, 66, 66));
        bilGruppe = lagBilGruppe();
    }

    /**
     *
     * @return En Group som representerer bilen
     */
    //metode for å tegne bil
    public Group lagBilGruppe() {
        //tegner karoseri til bilen
        r = new Rectangle(xPos, yPos, BIL_BREDDE, BIL_HØYDE );

        //setter farge
        r.setFill(farge);

        //tegner bilvindu
        double marg = r.getWidth()/4;
        Rectangle v = new Rectangle(xPos+marg, yPos+marg,
                r.getWidth()/2, r.getHeight()/4);

        //gjør vinduet blått
        v.setFill(Color.rgb(66, 191, 245, 1));

        //Oppretter gruppe
        Group gruppe = new Group();
        gruppe.getChildren().addAll(r, v);

        Double[] hjulXTab = {xPos-5, xPos+BIL_BREDDE, xPos-5, xPos+BIL_BREDDE};
        Double[] hjulYTAb = {yPos+20, yPos+20, yPos+BIL_HØYDE-20, yPos+BIL_HØYDE-20};

        //tegner på to  forhjul
        for(int i=0;i<4;i++){
            Rectangle hjul = new Rectangle(hjulXTab[i], hjulYTAb[i], 5, 20);
            hjul.setFill(Color.rgb(0, 0, 0));
            gruppe.getChildren().add(hjul);
        }


        return gruppe;
    }

    //get metoder

    /**
     *
     * @return En gruppe som representerer bilen
     */
    @Override
    public Group getFigur(){
        return bilGruppe;
    }
    
    //set metoder

    /**
     *
     * @param farge Ny farge for bilen.
     */
    public void setFarge(Color farge) {
        bilGruppe.getChildren().remove(r);
        r.setFill(farge);
        bilGruppe.getChildren().add(0, r);
    }

    /**
     *
     * @param endring mengde endring i X-retning
     */
    //metode for å styre bil
    public void styrBil(double endring) {

        double nyX = bilGruppe.getLayoutX() + endring;

        if(nyX >= (-1*(VINDU_BREDDE - BIL_BREDDE) /2) && nyX <= (VINDU_BREDDE - BIL_BREDDE) /2) {
            bilGruppe.setLayoutX(nyX);
        }
    }

    /**
     *
     * @return bilens x-posisjon
     */
    //ny getY metode som returnerer positiv x til bilgruppe
    @Override
    public double getXPos() {
        return bilGruppe.getLayoutX()+(VINDU_BREDDE - BIL_BREDDE) /2;
    }
}
