package com.example.carracinggame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


import static com.example.carracinggame.CarRacinGame.VINDU_BREDDE;
import static com.example.carracinggame.CarRacinGame.VINDU_HØYDE;

public class MidtLinje extends Figur{
    protected Group linjeGruppe;

    public MidtLinje() {
        super(VINDU_BREDDE / 2, -VINDU_HØYDE);
        linjeGruppe = new Group();
        lagMidlinjer();
    }

    private void lagMidlinjer() {
        double mellomrom = VINDU_HØYDE/7;

        //tegner første linje
        Line linje = new Line(xPos, yPos, xPos, yPos + VINDU_HØYDE / 7);
        linje.setStrokeWidth(VINDU_BREDDE/100);
        linje.setStroke(Color.YELLOW);
        linjeGruppe.getChildren().add(linje);

        //legger til 5 midlinjer med mellomrom
        for(int i=0;i<8;i++) {
            double startY = linje.getEndY()+mellomrom;
            double sluttY = startY + mellomrom;


            linje = new Line(xPos, startY, xPos,  sluttY);
            linje.setStrokeWidth(VINDU_BREDDE/100);
            linje.setStroke(Color.YELLOW);
            linjeGruppe.getChildren().add(linje);
        }
    }


    /**
     *
     * @return gruppe av midlinjer
     */
    @Override
    public Group getFigur() {
        return linjeGruppe;
    }
}
