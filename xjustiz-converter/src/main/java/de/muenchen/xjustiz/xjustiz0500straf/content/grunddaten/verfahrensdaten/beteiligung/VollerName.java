package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class VollerName {

    private String vorname;
    private String nachname;
    private String titel;
    private String namensvorsatz;
    private String geburtsname;

}
