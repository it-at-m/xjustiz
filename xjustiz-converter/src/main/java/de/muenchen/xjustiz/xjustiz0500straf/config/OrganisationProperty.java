package de.muenchen.xjustiz.xjustiz0500straf.config;

import lombok.Data;

import java.util.Map;

@Data
public class OrganisationProperty {

    private String bezeichnungAktuell;
    private String bezeichnungAnschriftStrasse;
    private String bezeichnungAnschriftHausnummer;
    private String bezeichnungAnschriftPlz;
    private String bezeichnungAnschriftOrt;
    private String bezeichnungBankverbindung;

    private Map<String, CodelistenProperty> codelisten;
}
