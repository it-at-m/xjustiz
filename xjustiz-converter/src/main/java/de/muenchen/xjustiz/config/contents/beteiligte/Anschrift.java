package de.muenchen.xjustiz.config.contents.beteiligte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anschrift {

    protected String anschriftsTyp;
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
