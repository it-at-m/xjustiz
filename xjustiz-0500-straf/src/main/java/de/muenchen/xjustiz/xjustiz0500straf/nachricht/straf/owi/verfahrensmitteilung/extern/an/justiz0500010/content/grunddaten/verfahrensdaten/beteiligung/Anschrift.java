package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.*;

@Data
public class Anschrift {

    private String strasse;
    private String hausnummer;
    protected String plz;
    protected String ort;
    protected String anschriftenzusatz;
    protected String postfachnummer;
    protected String wohnungsgeber;
    protected String staat;
    protected String ortsbeschreibung;
    private String anschriftstyp;

}
