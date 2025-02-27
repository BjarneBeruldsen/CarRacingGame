package com.example.carracinggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class CarRacinGame extends Application {
    //bredde og høyde på vindu/scene
    public final static int VINDU_BREDDE = 400;
    public final static int VINDU_HØYDE = 600;

    //globale variabler
    private BorderPane hovedPanel = new BorderPane();
    private Pane spillPanel = new Pane();
    private GridPane inputPanel = new GridPane();
    private StackPane overskriftsPanel = new StackPane();
    private Bil bil = new Bil();
    private StoppSkilt stoppskilt = new StoppSkilt();
    private TextField tfBrukernavn;
    private ColorPicker cpBilFarge;
    private Button btStart;
    private boolean startet = false;
    private Timeline tidslinje = new Timeline();
    private double totSekunder = 0.0;
    private double hinderSekunder = 0.0;
    private double bakgrunnSekunder = 0.0;
    private double spawnTid = 4.0;
    private KeyFrame kf;
    private double hinderHastighet = 5;
    private int antHinder = 1;
    private Text tSekunder = new Text();
    private StackPane sekunderPanel;
    private MidtLinje midtLinje = new MidtLinje();
    private int antBakObjekter = 1;
    private Spiller spillerNå;
    private TextArea taTopFem;
    private Label feilmelding;

    private ArrayList<Spiller> spillere = new ArrayList<>();
    private ArrayList<Figur> hinder = new ArrayList<>();
    private ArrayList<Figur> bakgrunnObjekter = new ArrayList<>();

    private Poengtavle poeng;


    @Override
    public void start(Stage stage) throws IOException {
        //henter poengtavle
        poeng = new Poengtavle();

        //overskrift
        hovedPanel.setTop(overskriftsPanel = overskriftspanel());

        //input felt for å skrive inn brukernavn og velge farge på bilen
        hovedPanel.setCenter(inputPanel());

        //legger til lytter for å starte spill
        btStart.setOnAction(e -> {
            registrerInput();
        });


        //legger lytter til panel
        spillPanel.setOnKeyPressed(e -> behandleTaster(e));


        //oppretter scene og legger til stage
        Scene scene = new Scene(hovedPanel, VINDU_BREDDE, VINDU_HØYDE);
        stage.setScene(scene);
        stage.setTitle("Car Racing Game");
        stage.show();


    }

    /**
     *
     * @return overskriftspanel
     */
    public StackPane overskriftspanel() {
        StackPane overskriftsPanel = new StackPane();

        //overskrift
        Text overskrift = new Text("Velkommen til Car Racing Game");
        overskrift.setFont(new Font("Times New Roman", 24));
        overskriftsPanel.getChildren().add(overskrift);

        return overskriftsPanel;
    }

    /**
     *
     * @return inputpanel
     */
    public GridPane inputPanel() {
        GridPane inputPanel = new GridPane();

        //sentrerer elementer
        inputPanel.setVgap(5);
        inputPanel.setAlignment(Pos.CENTER);


        //legger til labels, inputfelt, og fargepanel
        taTopFem = new TextArea();
        taTopFem.setPrefColumnCount(8);
        taTopFem.setPrefRowCount(8);
        taTopFem.setEditable(false);
        taTopFem.setText(poeng.getTopFem());

        inputPanel.add(taTopFem, 0, 0);
        inputPanel.add(new Label("Oppgi brukernavn: "), 0, 1);
        inputPanel.add(tfBrukernavn = new TextField(), 0, 2);
        inputPanel.add(new Label("Velg bilfarge: "), 0, 3);
        inputPanel.add(cpBilFarge = new ColorPicker(), 0, 4);
        inputPanel.add(btStart = new Button("Start"), 0, 5);
        inputPanel.add(feilmelding = new Label(), 0, 6);
        feilmelding.setTextFill(Color.RED);

        leggTilStyling(inputPanel);


        return inputPanel;
    }

    /**
     *
     * @return spillpanel
     */
    public Pane spillPanel() {

        leggTilStyling(spillPanel);

        return spillPanel;
    }

    /**
     *
     * @param e testetrykk-event
     */
    public void behandleTaster(KeyEvent e) {
        if(e.getCode() == KeyCode.LEFT) {
            bil.styrBil(-5);
        } else if (e.getCode() == KeyCode.RIGHT) {
            bil.styrBil(5);
        }
    }

    public void sjekkKollisjon() {
        //går igjennom hinder og sjekker om de har treffet bil
        double hinderY = 0.0;
        double hinderX = 0.0;
        double bilY = bil.getYPos();
        double bilX = bil.getXPos();
        double bilBredde = bil.BIL_BREDDE;

        for (Figur h : hinder) {
            hinderY = h.getFigur().getLayoutY();
            hinderX = Math.round(h.getXPos());

            if(hinderY + h.getRadius() == bilY
                    && (hinderX >= bilX)
                    && (hinderX <= bilX + bilBredde)
                    && (bilX < hinderX + h.getRadius()*2)){
                tidslinje.stop();
                spillStatus(false);
            }


        }
    }

    //metode for å legge til styling
    public void leggTilStyling(Pane panel) {
        panel.setStyle("-fx-background-color: #d3d3d3; -fx-border-width: 2px; -fx-border-color: black");
    }

    //metode for å starte spill
    public void spillStatus(boolean status) {
        if(status) {
            hovedPanel.getChildren().remove(inputPanel);
            hovedPanel.getChildren().remove(overskriftsPanel);
            hovedPanel.setCenter(spillPanel = spillPanel());
            spillPanel.requestFocus();
            stoppskilt = new StoppSkilt();
            bil = new Bil();
            startet = true;
            totSekunder = 0.0;
            hinderSekunder = 0.0;
            bakgrunnSekunder = 0.0;
            spawnTid = 4.0;
            antHinder = 1;
            antBakObjekter = 1;
            hinder.clear();
            bakgrunnObjekter.clear();
            spillPanel.getChildren().clear();
            spillPanel.getChildren().add(midtLinje.getFigur());
            spillPanel.getChildren().add(bil.getFigur());
            spillPanel.getChildren().add(stoppskilt.getFigur());
            hinder.add(stoppskilt);
            bakgrunnObjekter.add(midtLinje);
            tidslinje.getKeyFrames().clear();
            lagAnimasjon();
        }
        else {
            registrerPoeng();
            taTopFem.setText(poeng.getTopFem());
            hovedPanel.getChildren().remove(spillPanel);
            hovedPanel.setTop(overskriftsPanel = overskriftspanel());
            hovedPanel.setCenter(inputPanel = inputPanel());
            btStart.setOnAction(e -> {
                registrerInput();
            });

        }
    }

    //metode som oppretter objekt av bruker
    public void registrerInput() {
        String brukernavn = "";
        try {
             brukernavn = tfBrukernavn.getText();
             spillerNå = new Spiller(brukernavn);
        }
        catch (UgyldigBrukernavnException ube) {
            feilmelding.setText("Error fanget: " +  ube.getMessage());
            return;
        }
        spillere.add(spillerNå);
        skrivUtSpillere();
        if(brukernavn.length() > 3 && brukernavn.length() < 20) {
            spillStatus(true);
            bil.setFarge(cpBilFarge.getValue());
        }
    }


    public void registrerPoeng() {
        spillerNå.setAntSekSpilt(totSekunder);
        poeng.leggTilPoeng(spillerNå);
        System.out.println(poeng.getTopFem());
    }

    public void skrivUtSpillere() {
        try {
            //skriver midlertidig ut i konsollet
            System.out.println("SPILLERE: ");
            for (Spiller s : spillere) {
                System.out.println(s.toString());
            }
        }
        catch (NullPointerException npe) {
            System.out.println("Error fanget: " + npe.getMessage());
        }
    }

    public void lagAnimasjon() {
        tidslinje.stop();
        kf = new KeyFrame(Duration.millis(hinderHastighet), e ->{
            flyttHinder();
            flyttBakgrunn();
            sjekkKollisjon();
        });
        tidslinje = new Timeline(kf);
        tidslinje.setCycleCount(Timeline.INDEFINITE);
        tidslinje.setRate(1);
        tidslinje.play();
    }

    public void flyttHinder() {
        double sekunder = 0.0;
        sekunder = tidslinje.getCurrentTime().toSeconds();
        totSekunder += sekunder;
        //viser antall sekunder
        spillPanel.getChildren().remove(tSekunder);
        tSekunder = new Text(VINDU_BREDDE / 2-24, 50, String.format("%.2f", totSekunder));
        tSekunder.setFont(new Font("Times New Roman", 24));
        tSekunder.setFill(Color.GREEN);
        spillPanel.getChildren().add(tSekunder);

        hinderSekunder += sekunder;

        for(Figur f: hinder) {
            double y = f.getFigur().getLayoutY();
            f.getFigur().setLayoutY(y += 1);
        }


        //spawner nytt hinder etter tre sekunder
        if(hinderSekunder >= spawnTid) {
            //oppretter nytt skilt i ny posisjon
            stoppskilt = new StoppSkilt();
            hinder.add(stoppskilt);
            antHinder++;
            spillPanel.getChildren().add(hinder.get(antHinder-1).getFigur());
            //setter hinderSekunder til 0
            hinderSekunder = 0.0;
            if(spawnTid > 1.0) {
                spawnTid -= 0.5;
            }
        }
    }

    public void flyttBakgrunn() {

        double sekunder = tidslinje.getCurrentTime().toSeconds();
        bakgrunnSekunder += sekunder;

        if(bakgrunnObjekter.size() > 0) {
            //henter gamle y
            double y = bakgrunnObjekter.get(antBakObjekter - 1).getFigur().getLayoutY();
            //setter ny y
            bakgrunnObjekter.get(antBakObjekter - 1).getFigur().setLayoutY(y + 1);
        }


        if(bakgrunnSekunder >= 1.68) {
            midtLinje = new MidtLinje();
            bakgrunnObjekter.add(midtLinje);
            spillPanel.getChildren().remove(bakgrunnObjekter.get(antBakObjekter - 1).getFigur());
            antBakObjekter++;
            spillPanel.getChildren().add(0, bakgrunnObjekter.get(antBakObjekter - 1).getFigur());
            bakgrunnSekunder = 0.0;
        }

    }

    public static void main(String[] args) {
        launch();
    }
}