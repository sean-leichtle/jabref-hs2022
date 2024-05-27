# Testcoverage

## 1.Testabdeckung insgesamt

Die Abbildung 1 bietet einen Auszug aus dem jacocoTestReport. Aufgrund der besseren Übersicht
sind nicht alle Klassen abgebildet. Der gesamte JacocoReport ist in [diesem Dokument](https://github.com/mwb28/jabref/blob/testplan/docs/sweng/jacocoTestReport.csv ) einsehbar.

![Testabedeckung_insgesamt](images/testReportImages/jacocoTestReport_Overview.png)

**Abb. 1** Übersicht Testabdeckung

Von insgesamt 302161 ausführbaren CodeZeilen sind 159084 mit Tests aufgerufen worden.
Dies sind 47 % der gesamten Codemenge (ersichtlich in der Spalte "Missed Instruction" und anschliessend "Cov." [zweite Spalte von links]).

Von den Verzweigungen (Branches, typischerweise if/else und switch-Anweisungen) sind durch die Tests
50 % abgedeckt (ersichtlich in der Spalte "Missed Branches" und anschliessend "Cov.").

Die weiteren Spalten betreffen die zyklomatische Komplexität (Spalte "Cxty"), einzelne Zeilen ("Lines")
Methoden ("Methods") und Klassen ("Classes"), auf die wir nicht weiter eingehen werden.

## 2. Testabdeckung der Erweiterung Abstract Translator

Bei der Analyse beschränken wir uns auf die Klassen, die wir neu implementiert oder angepasst haben.
Die untenstehende Tabelle bietet einen Überblick über diese Testabdeckung.
Herauszulesen ist hierbei, dass von insgesamt 553 ausführbaren Codezeilen 22 % durch unsere Unittests
abgedeckt werden (119 Codezeilen). Es scheint offensichtlich, dass nicht alle dieser 553 Codezeilen von
uns geschrieben wurden.
![jacocoTestReport_TranslatorJabRef_Overview.png](images/testReportImages/jacocoTestReport_TranslatorJabRef_Overview.png)

**Abb. 2** Übersicht Testabdeckung der JabRef Translator Erweiterung

### 2.1. AbstractAreaEditor

![jacocoTestReport_AbstractAreaEditor.png](images/testReportImages/jacocoTestReport_AbstractAreaEditor.png)

**Abb. 3** Testabdeckung AbstractAreaEditor

Aufgrund von Abhängigkeiten mit dem Framework JavaFx und GUI- Komponenten sind hier
keine Unittests vorhanden.

### 2.2. EditorMenus

![jacocoTestReport_EditorMenus.png](images/testReportImages/jacocoTestReport_EditorMenus.png)
**Abb. 4** Testabdeckung EditorMenus

Auch hier wird aufgrund der GUI- Komponente auf Unittests verzichtet.

### 2.3. TranslateAbstractAction

![jacocoTestReport_TranslateAbstractAction.png](images/testReportImages/jacocoTestReport_TranslateAbstractAction.png)

**Abb. 5** Testabdeckung EditorMenus

Auch hier wird aufgrund der GUI-Komponente auf Unittests verzichtet.

### 2.4. Translator

![jacocoTestReport_Translator.png](images/testReportImages/jacocoTestReport_Translator.png)
**Abb. 6** Testabdeckung Translator

Wie bereits im Testplan erwähnt wird die Methode `translate` zu 100%
mit den Unittests abgedeckt.

### 2.5. LangDetector

![jacocoTestReport_LangDetector.png](images/testReportImages/jacocoTestReport_LangDetector.png)

**Abb. 7** Testabdeckung LangDetector

Die Codezeilen in der Klasse `LangDetector` werden zu 91% durch die Unittests abgedeckt.
