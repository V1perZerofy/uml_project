# Dungeon Adventure

Ein kleines textbasiertes Konsolenspiel in Java. Der Spieler erkundet einen Dungeon aus mehreren Räumen, kämpft gegen Monster, handelt mit Merchants und sammelt Gegenstände. 
Ziel ist es, möglichst lange zu überleben – das Spiel endet, wenn der Spieler stirbt oder es per `quit` beendet wird.

Das Projekt entstand im Rahmen der Ausbildung/SEPE (UML-Modellierung + OOP-Implementierung).

## Features

- Raum-für-Raum-Erkundung mit frei verknüpfbaren Ausgängen
- Rundenbasiertes Kampfsystem (Spieler vs. Monster)
- Handelssystem mit einem Merchant (Items kaufen, Gold verwalten)
- Aufsammeln und Einsetzen von Gegenständen (z. B. HealingPotion)
- Offene Erkundung ohne festes Spielende – beendet durch den Tod des Spielers oder per `quit`

## Voraussetzungen

- JDK 17 oder neuer
- Eine Konsole / ein Terminal

## Starten

Voraussetzung ist ein installiertes JDK (`javac` und `java` im PATH) sowie ein UTF-8-fähiges
Terminal. Das Spiel besteht aus `Game.java` (Einstiegspunkt) und dem Paket `dungeonadventure/`.
Das Flag `-encoding UTF-8` ist nötig, weil die Oberfläche Unicode-Zeichen (Rahmen, Balken)
verwendet.

### Windows

Per Skript:

```bat
run.bat
```

Oder manuell (z. B. in der Windows-Eingabeaufforderung oder Windows Terminal):

```bat
chcp 65001
javac -encoding UTF-8 -d out Game.java dungeonadventure\*.java
java -cp out Game
```

`chcp 65001` stellt die Konsole auf UTF-8 um, damit Rahmen- und Balkenzeichen korrekt
angezeigt werden.

### macOS / Linux

Per Skript:

```bash
chmod +x run.sh
./run.sh
```

Oder manuell:

```bash
javac -encoding UTF-8 -d out Game.java dungeonadventure/*.java
java -cp out Game
```

## Spielablauf

Gesteuert wird über einfache Textbefehle in der Konsole. Oben wird der aktuelle Raum
angezeigt, darunter ein Log der letzten Ereignisse und eine Statuszeile mit HP-Balken,
Gold und Anzahl der Items.

### Befehle

| Befehl | Wirkung |
|--------|---------|
| `go <richtung>` | In die angegebene Richtung gehen (z. B. `go north`) |
| `fight` | Gegen das Monster im Raum kämpfen (eine Runde pro Eingabe) |
| `take` | Den Gegenstand im Raum aufheben |
| `talk` | Mit dem Merchant sprechen – zeigt Sortiment und Preise |
| `buy <item>` | Einen Gegenstand beim Merchant kaufen (z. B. `buy Potion`) |
| `use <item>` | Einen Gegenstand benutzen (z. B. `use Potion` zum Heilen) |
| `inventory` / `inv` | Inventar und Gold anzeigen |
| `look` | Den aktuellen Raum erneut beschreiben |
| `quit` / `exit` | Das Spiel beenden |

Item-Namen sind nicht case-sensitiv – `buy potion` funktioniert genauso wie `buy Potion`.

### Hinweise

- **Kampf** läuft rundenweise ab: Pro `fight` schlägt zuerst der Spieler zu, danach das
  Monster. Sinkt die HP des Spielers unter 50 und er besitzt einen Heiltrank, trinkt er
  automatisch einen.
- **Beim Verlassen eines Raums** füllt der Merchant sein Sortiment wieder auf, und ein
  besiegtes Monster erwacht beim nächsten Betreten erneut.
- Das Spiel endet, wenn der Spieler stirbt oder `quit` eingegeben wird.

## Projektstruktur

```
dungeonadventure/
├── ConsoleUI.java     # Zuständig für die "grafische" Terminaloberfläche
├── GameEngine.java    # Spielsteuerung: Game-Loop, Eingaben, Kampfauflösung
├── Entity.java        # abstrakte Oberklasse (Name, Health)
├── IFightable.java    # Interface für kampffähige Entitäten
├── Player.java        # Spielfigur (Inventar, Gold)
├── Monster.java       # Gegner (Loot, Schwierigkeit)
├── NPC.java           # abstrakte Oberklasse für Nicht-Spieler-Figuren
├── Merchant.java      # Händler (Sortiment, Handel)
├── Item.java          # abstrakte Oberklasse für Gegenstände
├── HealingPotion.java # konkreter Gegenstand (Heilung)
└── Room.java          # Raum (Inhalt, Ausgänge)
Game.java              # Einstiegspunkt, startet die GameEngine
```

## Designentscheidungen

- **Abstraktion über zwei Wege:** `Entity` bündelt gemeinsame Eigenschaften aller Figuren (Name, Health), während `IFightable` getrennt davon nur die Kampffähigkeit definiert. Dadurch erbt der `Merchant` zwar von `Entity`, ist aber bewusst **nicht** kampffähig – nur `Player` und `Monster` implementieren `IFightable`.
- **Kampf-Logik im GameEngine (Mediator):** `attack()` / `defend()` liegen auf den Entitäten, die Auflösung eines Kampfes (`resolveCombat`, `calculateDamage`, `checkFlee`) aber zentral in der `GameEngine`. So bleibt die Spielregel-Logik an einer Stelle und die Entitäten schlank.
- **Erweiterbarkeit durch Vererbung:** Neue Item- oder Monster-Typen werden als Unterklasse von `Item` bzw. `Entity` ergänzt. Dank Polymorphie (`use()`, `attack()`/`defend()`) muss bestehender Code dafür nicht angepasst werden.
- **Aggregation statt Komposition bei Raum-Inhalten:** Ein `Room` hält `Monster`, `Merchant` und `Item`, die unabhängig vom Raum existieren und zwischen Spielobjekten weitergereicht werden können (z. B. Loot vom Monster zum Player).

## Autoren

Douglas Grande, Niels Heiden, Maximilian Drescher
