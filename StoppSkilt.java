package com.example.carracinggame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.example.carracinggame.CarRacinGame.VINDU_BREDDE;
import static com.example.carracinggame.CarRacinGame.VINDU_HØYDE;

public class StoppSkilt extends Figur{
    protected Group skiltGruppe;
    final public static int RADIUS = (VINDU_BREDDE / 12);

    public StoppSkilt(double xPos, double yPos) {
        super(xPos, yPos);
        skiltGruppe = lagSkilt();
    }

    public StoppSkilt() {
      this(Math.random() * VINDU_BREDDE, 0);
    }

    /**
     *
     * @return gruppe som representerer stoppskilt
     */
    public Group lagSkilt() {
        //tegner sirkel
        Circle sirkel = new Circle(xPos, yPos, RADIUS, Color.RED);
        Text tekst = new Text(xPos-RADIUS+(RADIUS/5), yPos+(RADIUS/5), "STOP");
        tekst.setFont(new Font("Oswald", RADIUS/1.5));
        tekst.setFill(Color.rgb(255, 255, 255));

        Group gruppe = new Group();
        gruppe.getChildren().addAll(sirkel, tekst);

        return gruppe;
    }

    /**
     *
     * @return skiltet sin radius
     */
    @Override
    public double getRadius() {
        return RADIUS;
    }

    /**
     *
     * @return gruppe som representerer stoppskilt
     */
    @Override
    public Group getFigur() {
        return skiltGruppe;
    }

}
