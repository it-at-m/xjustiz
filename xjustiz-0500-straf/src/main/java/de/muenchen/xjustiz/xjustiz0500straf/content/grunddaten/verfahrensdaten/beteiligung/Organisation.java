package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Organisation extends Anschriften {

    private String bezeichnungAktuell;
    private String iban;
}
