
# Eckdaten
Buildtool: Gradle
Framework: Spring mit SLF4J fürs Loggins
Compilertool: JavaCC21

Das programmierte Tool besteht aus:
- generiertem Lexer, Parser
- Syntaxanalyse-Teil bzw. der Grammatik
- Semantischer Analyse mittels Visitor-Pattern
- Interpreter ebenfalls im Visitor-Pattern enthalten

Pfade nach /de/compilerbau/NewAwkCompiler: 
- /generated: JavaCC Generationen (werden nicht mehr benutzt)
- /javacc21: JavaCC21 Generationen (werden genutzt) 
- /testfiles: Testdateien
- /visitors: 2 Visitoren (Dumpvisitor für einefache Ausgabe des AST, SymbolTableBuilderVisitor für
semantische Analyse und Interpreterlogik)

Weiteres: 
- Loggingeinstellungen sind in den application.properties anpassbar.
- Das Logging verwendet 3 Level (INFO, WARN und ERROR)
- Ein logging-ERROR bedeutet in der Regel einen Fehler in der semantischen Analyse, 
- WARN wurde sehr sparsam eingesetzt
- INFO wurde für alle möglichen Statusinformationen während der Verarbeitung eingesetzt, der SymbolTableBuilderVisitor
besitzt eine printEnter/printExit Methode, und jeder Token ist ein Node, sodass man damit durch die Impl der toString-Methoden
die jeweiligen Werte in den AST-Nodes leichter nachvollziehen kann. Wurdern Sie sich also nicht über sehr viele logs mit "Enter: Node...etc." 
(hier wurde die leere Node betreten, dann wird meist irgendwann "children.accept()" aufgerufen und weiter in den Baum hinabgestiegen), später findet man
"Exit: Node...etc" und kann die ausgewerteten Ausdrücke betrachten.     
- Um println-Funktionalität zu testen nehmen sie bitte einen ausreichend komplexen String, da er sich zwischen die Logging-Ausgaben einordnet 

### Startklasse Spring: NewAwkCompilerApplication.java 
In dieser Klassen können Sie die Testdateien nach belieben einspeisen. (bitte dafür Zeile "NewAwkParser.main(arguments);" anpassen, 
ein Vorbereiteter Input für alle und für 1 Datei liegt vor)
Leider kann nämlich die Ast-Struktur bisher nur beim Lesen einer enzelnen Datei in JavaCC21 ausgegeben werden, diese werden sie bei allen 29 Testdateien gemeinsam größtenteils nicht sehen. 
Die Testdaten liegen geordnet unter "de/compilerbau/NewAwkCompiler/testfiles" als txt-Dateien.
Es ist gewollt, dass die Dateien mit dem Bennenungsschema "Test_Fail_..." fehlschlagen und 
"Test_Success_..." erfolgreich kompiliert und interpretiert werden.

###Anwendung Ausführen
- Führen Sie zum Anwendungsstart die gradle-Task: "bootRun" aus.
- Vorher muss, wenn sie den Code aktualisieren sollten javacc21 neue Dateien erzeugen,
dazu stelle ich die gradle-Task "execJavaCC21" bereit 

### Jar Erstellen
- die gradle-Task: bootJar ausführen


