    /\        | |
   /  \  _   _| |_ ___  _ __   ___  _ __ ___   ___  ___
  / /\ \| | | | __/ _ \| '_ \ / _ \| '_ ` _ \ / _ \/ __|
 / ____ \ |_| | || (_) | | | | (_) | | | | | |  __/\__ \
/_/    \_\__,_|\__\___/|_| |_|\___/|_| |_| |_|\___||___/

  _____                              _         _
 / ____|                            | |       | |
| |  __  _____      ____ _  ___  ___| |__  ___| |__   __ _ _   _ ___
| | |_ |/ _ \ \ /\ / / _` |/ _ \/ __| '_ \/ __| '_ \ / _` | | | / __|
| |__| |  __/\ V  V / (_| |  __/ (__| | | \__ \ | | | (_| | |_| \__ \
 \_____|\___| \_/\_/ \__,_|\___|\___|_| |_|___/_| |_|\__,_|\__,_|___/


Erstellt von Jannik Best, Paul Grundmann, Benjamin Junker, Nick Teschner


Ausführen
1. Auführen /src/gewaechshaus/fxGUI/FXGUI.java
     Gui sollte starten
2.1 Wenn die Simulation schrittweise ausgeführt werden soll:
    Simulationsschritt-Button anklicken
2.2 Wenn die Simulation automatisch ablaufen soll:
    Den Takt in ms im Feld neben "Timer Periodendauer setzen"-Button eintragen
    Entweder den Button Simulationsuhr starten klicken oder "Timer Periodendauer setzen" klicken
2. Button "Timer Periodendauer setzen" drücken
     Pflanzen sollten nun wachsen
3. Button "Reife Pflanzen ernten" drücken
     Roboter sollten nun die Pflanzen ernten

Es kann auch vor dem Starten der Simulation ein oder mehere Aufträge hinzugefügt werden.
Diese werden dann nacheinander abgearbeitet.

Unit- & Integrations- & Systemtests
Die Unit Tests befinden sich unter src_test