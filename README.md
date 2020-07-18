# Compilerbau

## 1. Lexikalische Analyse lex, jlex, javaCC

1.1 Erkennen von "Grundsymbolen", genannt Token im als Zeichenfolge
vorliegenden Programm (oder anderen Texten)

1.2 Umwandlung des Quellprogramms in eine Folge von Token

Token in gängigen Programmiersprachen sind z.B.
– Schlüsselwörter wie class, program, end, if, for, array, int, float
– Bezeichner (z.B. i, j, file_name, student)
– Literale (z.B. "Hallo World", 1.0, -4711)
– Vergleichsoperatoren (≥, >, <, ≤, …)
– Trennzeichen ({, }, (, ), [, ], ; …)
– …


## 2. Syntax-Analyse javaCC, yacc

2.1 Überprüfen der Struktur eines Programms (oder anderer Texte)

2.2 Das in Form einer Folge von Token vorliegende Programm wird
zunächst (meist implizit) in einen Syntaxbaum transformiert

2.3 Aus dem Syntaxbaum wird dann ein abstrakter Syntaxbaum (AST)
erzeugt


## 3. Semantische Analyse

3.1 Sammeln weiterer Informationen die,
-  in der Syntaxanalyse nicht erfasste Korrektheitsaspekte betreffen
- für die folgende Codeerzeugung benötigt werden

3.2 Zur semantischen Analyse gehören
– Typüberprüfung
– Auflösen überladener Operationen / Methoden
– Typanpassungsoperationen


## 4. Fehlerbehandlung

## 5. Codeerzeugung/Interpreter llvm, asm, Interpreter (AST, Zwischencode), C++, Java, Java-Script

– Stackmaschinen (Bspl. Java-Bytecode)

- Zwischencode wird nach und nach “näher“ an den endgültigen Code
gebracht -> Mit einem Zwischencode (Bspl. llvm)

Optimierungen
– Beseitigung überflüssiger temporärer Variablen
– Beseitigung überflüssiger Sprungbefehle
– Analyse der Schleifenstruktur des Programms → In innersten
Schleifen verwendete Variablen schnellen Registern zuordnen
– Schleife umsetzen : n-faches Kopieren der Anweisungsfolge im
Schleifenkörper








