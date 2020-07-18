# NewAwk

- Ein NewAwk-Programm besteht aus Funktionen/Methoden
und Variablendeklarationen

Beispiel:
int main(String[] args) {
int x = 5;
return x;
}
int y = 7;

- Es werden keine Objekte oder Klassen definiert (wenn Sie
wollen, dürfen Sie aber …)

## Datentypen:
– int, double, char, boolean, String
– Multidimensionale Felder auf diesen Datentypen

## Operatoren:
- Operatoren auf int, double, char: +, -, *, /, %
- Operatoren auf boolean: &&, ||, !
- Vergleichsoperationen: ==, >=, !=, <=, <, >
- Zuordnung: =

## Strings:
- Operator + wie gewohnt: Bsp. “Hello“ + 5 == “Hello5“
- Methode length() wie gewohnt: Bsp. “Hello“.length() = 5
- Methoden isInt(), isDouble(), etc., die überprüfen, ob die
  Zeichenfolge ein Integer etc. ist
- Methoden toInt(), toDouble(), etc., die aus der Zeichenkette ein
  Integer,… machen
- Alle Datentypen sollen implizit in einen String umwandelbar sein

## Operator @{ … }:
Der String wird gemäß den Regeln in den
  geschweiften Klammern zerlegt.
- Ziel des Operators: Zerlegen des Strings in die definierten Token und dabei
 Ausführung der Aktion
- Ein vereinfachter Reg. Ausdruck soll hierbei wenigstens zulassen, dass
man Stringliterale und die Negation von Stringliteralen, sowie Bereiche von
Zeichen, Zeilenanfang, Zeilenende und die Negation davon erkennt (hier
bleibt Ihnen freie Hand, wie Sie die Syntax wählen)
- Eine Aktion ist einfach wieder NewAwk-Skriptcode in “{“ “}“-Klammern
eingeschlossen (kann auch weggelassen werden), das einen Wert zurück
gibt (ohne Aktion wird einfach der gefundene String zurückgegeben).
Alle Aktionen müssen den gleichen Typ zurückgeben.
- Innerhalb der Aktion hat man eine Variable this, die auf das gefundene
Lexem zeigt. Wird eine leere Return-Anweisung verwendet, so wird kein
Wert in das Ergebnisfeld aufgenommen.

- Beispiel:
  “1 2 3 4 5 a“@{
  :Integer: { return this; }
  !:Integer: { return ; }
  }
  Gibt das folgende Feld von Strings zurück:
  “1“, “2“, “3“, “4“, “5“
  Ändert man die erste Aktion in { return 1; } um, so
  erhält man ein Feld von Integern:
  1, 1, 1, 1, 1
  
## Felder:
- Konstante length() wie gewohnt: Bsp. Wenn x ein Feld der Länge 5 ist,
  so gilt x.length = 5
- Feldzugriff über Index (0,1,2,…) wie gewohnt
- Feldauswahl: Hat bei Feldzugriff ( [ausdruck] ) der Ausdruck den Typ
  boolean, so wird das Teilfeld zurückgegeben, für das der Ausdruck wahr
  wird. Der Ausdruck kann dabei mit i[k] auf den aktuellen Index, und mit
  this auf den Inhalt zugreifen. Das k bezeichnet hierbei für welche
  Dimension wir den Index haben wollen
- Bsp. 1: Wir haben das Feld x mit den Zahlen 1,2,3,4,5
  - Dann gibt x[2] die Zahl 3 zurück und
  – x[ i[0]%2 == 0 ] gibt das Feld mit den Elementen 1,3,5 zurück
- Bsp. 2: Wir haben ein zweidimensionales Feld
  y = { { 1,2,3 }, { 4,5,6 }, { 7,8,9 } }
  – y[i[0] % 2 == 1][i[0] == i[1]] gibt in den ungeraden Zeilen eindimensionale
  Felder mit dem Diagonalelement zurück, also { {5} }
  

# NewAwk Umgebung

Letztendlich wollen wir das compilierte oder interpretierte
NewAwk-Programm mit ein oder mehr Dateien aufrufen, z.B.

meinNewAwkProgramm dies.txt das.txt jenes.txt

- Der Inhalt (als String) dieser Dateien soll über das String-Array System.File
in dem NewAwk zugreifbar sein. In obigen Beispiel enthält System.File[0]
den Inhalt der Datei dies.txt, System.File[1] den Inhalt von das.txt und
System.File[2] den Inhalt von jenes.txt.
- Mit den Methoden System.print() bzw. System.println() soll man Strings auf
der Konsole ausgeben können (wie bei Java mit System.out.print()/println())
- Mit den Methoden System.next(), System.nextInt(), etc. soll man Eingaben
von der Konsole einlesen können (wie mit Scanner-Objekten in Java)
