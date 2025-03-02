# CarRacingGame
Bilspill med fokus på implementering av Binary I/O, egendefinerte exceptions og animasjon. 


## Videre beskrivelse: 
Programmet "Car Racing Game" er et bilspill laget med Java og JavaFX. Her er en beskrivelse av hovedfunksjonene i programmet:

### Hovedpaneler og scener: 
Programmet bruker ulike paneler som BorderPane, Pane, GridPane, og StackPane for å arrangere GUI-elementene.

### Brukerinput:
Spilleren kan oppgi brukernavn og velge farge på bilen gjennom inputfelt og fargevelger.

### Spillmekanikk:
-Bilen styres ved å trykke på venstre og høyre piltaster.
-Hindringer og bakgrunnsobjekter flyttes nedover skjermen kontinuerlig.
-Kollisjon mellom bilen og hindringer blir sjekket, og spillet stopper ved treff.

### Animasjon: 
En Timeline brukes til å animere spillet ved å oppdatere posisjonen til hindringer og bakgrunnsobjekter.

### Poengtavle:
Poengene lagres og vises for de fem beste spillerne.

Hovedklassen CarRacinGame inneholder metoder for å starte spillet, håndtere brukerinput, oppdatere GUI, og sjekke spillstatus.
