# Release-Notes

## 12.2.2026
### Update
- Hebung der xJustiz Version 3.5.1 auf Version 3.6.2
- Änderung des Modul Namen ohne konkrete Version im Namen, stattdessen x-x-x

## 11.2.2026
### Refactoring
- Modularisierung der Artefaktstruktur. Aufteilung in Compilierung xJustiz-Klassen, XML-Generierung und ggf. Dokument Builder in einzelne Maven Module.

## 27.01.2026
### Aenderung
- xjustiz0500Straf-Builder: Tatzeitraum-Datum und -Uhrzeit fuer Anfang/Ende optional (minOccurs="0").

## 24.12.2025
### Refactoring
- xJustiz Dokumentenerstellung in eigenes Maven Modul verschoben.
- XML Generierung parameterisiert.

## 18.12.2026
### Aenderung
- Standalone=true entfernt.
- Namespaces geaendert (xsi:schemaLocation, xmlns:xsi, din91379, tns).
- SchemaLocation hinzugefuegt.

## 5.12.2025
### Hinzugefuegt
- Namespaces (xoev-code, xoev-lc, xsi, tns) werden bei der XML Generierung beruecksichtigt.
- Kostendokument, Verwerfung

## 4.11.2025
### Hinzugefuegt
- Erlassdatum, Rechtskraftdatum.
- Spotless aktiviert.

## 30.10.2025
### Build-Infrastruktur
- Maven-release eingerichtet.

## 29.10.2025
### Hinzugefuegt
- Auslagen, Geldbusse
- Verfahrensdaten Instansdaten erweitert.

## 1.10.2025
### Aenderung
- Ruecksetzten der xJustiz Version 3.6.2 auf 3.5.1.

## 13.08.2025
### Hinzugefuegt
- Realisierung xjustiz_0500_straf_3_6.xsd nachricht.straf.owi.verfahrensmitteilung.externAnJustiz.0500010.