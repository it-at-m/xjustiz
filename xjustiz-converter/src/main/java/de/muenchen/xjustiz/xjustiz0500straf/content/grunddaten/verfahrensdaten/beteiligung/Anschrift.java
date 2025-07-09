package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.*;

@Getter
@Setter
public class Anschrift {

    protected String anschriftsTyp;
    protected String listVersionID;
    private String strasse;
    private String hausnummer;
    protected String plz;
    protected String ort;
    protected String anschriftenzusatz;
    protected String postfachnummer;
    protected String wohnungsgeber;
    protected String staat;
    protected String ortsbeschreibung;

}
