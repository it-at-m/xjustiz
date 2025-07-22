package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.xoev.codelisten.*;
import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.*;
import org.springframework.stereotype.Component;


@Component
public class GrunddatenBuilder extends Builder {

    private final IncrementCounters incrementCounters = new IncrementCounters();

    public GrunddatenBuilder(XJustizProperty xjustizProperty, NachrichtenProperty nachrichtenProperty) {
        super(xjustizProperty, nachrichtenProperty);
    }

    public TypeGDSGrunddaten build(GrunddatenContent grunddatenContent) {

        incrementCounters.reset();

        /*
           The organization can be defined statically once in the properties or transferred dynamically with the natural persons.
        */
        if (nachrichtenProperty.isOrganisationConfiguredInApplicationProperties())
            grunddatenContent.getBeteiligungen().ifPresent(b -> b.add(generateBeteiligungOrganisation()));

        /**
         * Grunddaten
         * - Verfahrensdaten
         *      - Instanzdaten
         *          - Sachgebiet
         *          - AuswahlInstanzbehoerde
         * - Beteiligung
         *  - Rolle
         *      - Rollennummer
         *      - Rollenbezeichnung
         *  - Beteiligter
         *      - Beteiligtennummer
         *      - AuswahlBeteiligter
         */

        TypeGDSGrunddaten grunddaten = new TypeGDSGrunddaten();

        TypeGDSInstanzdaten instanzGericht = new TypeGDSInstanzdaten();
        TypeGDSBehoerde behoerde = new TypeGDSBehoerde();

        TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten = new TypeGDSGrunddaten.Verfahrensdaten();
        TypeGDSInstanzdaten instanzSachgebiet = new TypeGDSInstanzdaten();
        instanzSachgebiet.setAuswahlInstanzbehoerde(behoerde);

        instanzSachgebiet.setSachgebiet((CodeGDSSachgebietTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3, XoevCodeGDSSachgebietTyp3.OWI_SACHEN.getDescriptor()));
        verfahrensdaten.getInstanzdatens().add(instanzSachgebiet);
        grunddaten.setVerfahrensdaten(verfahrensdaten);

        instanzGericht.setAuswahlInstanzbehoerde(behoerde);
        instanzGericht.getAuswahlInstanzbehoerde().setGericht((CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3, XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));
        verfahrensdaten.getInstanzdatens().add(instanzGericht);

        grunddatenContent.getBeteiligungen().ifPresent(beteiligungen -> beteiligungen.forEach(beteiligung -> verfahrensdaten.getBeteiligungs().add(beteiligungBuilder(beteiligung))));

        return grunddaten;
    }

    private TypeGDSBeteiligung beteiligungBuilder(Beteiligung beteiligungContent) {

        TypeGDSBeteiligung xjustizBeteiligung = new TypeGDSBeteiligung();

        beteiligungContent.getRollen().ifPresent(rollen -> {
            rollen.forEach(r -> {
                TypeGDSBeteiligung.Rolle rolle = new TypeGDSBeteiligung.Rolle();
                rolle.setRollennummer(String.valueOf(incrementCounters.incrementEntireXmlRollennummer()));
                rolle.setNr(incrementCounters.incrementRollenbezeichnungCounter(r.getRollenbezeichnung()));
                rolle.setRollenbezeichnung((CodeGDSRollenbezeichnungTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3, r.getRollenbezeichnung()));
                xjustizBeteiligung.getRolles().add(rolle);
            });
        });

        beteiligungContent.getBeteiligter().ifPresent(b -> {

            if (b.getOrganisation().isPresent())
                xjustizBeteiligung.setBeteiligter(beteiligteOrganisation(b));
            else
                xjustizBeteiligung.setBeteiligter(beteiligtePerson(b));

        });
        return xjustizBeteiligung;
    }

    private TypeGDSBeteiligter beteiligteOrganisation(Beteiligter b) {

        TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(String.valueOf(incrementCounters.incrementBeteiligtenNummer()));

        b.getOrganisation().ifPresent(o -> {
            TypeGDSOrganisation organisation = new TypeGDSOrganisation();

            beteiligter.setAuswahlBeteiligter(new TypeGDSBeteiligter.AuswahlBeteiligter());

            TypeGDSOrganisation.Bezeichnung bezeichnung = new TypeGDSOrganisation.Bezeichnung();
            bezeichnung.setBezeichnungAktuell(o.getBezeichnungAktuell());
            organisation.setBezeichnung(bezeichnung);

            o.getAnschriften().ifPresent(anschriften -> {
                anschriften.forEach(a -> {
                    TypeGDSAnschrift anschrift = new TypeGDSAnschrift();
                    anschrift.setAnschriftstyp((CodeGDSAnschriftstyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP, a.getAnschriftstyp()));
                    anschrift.setStrasse(a.getStrasse());
                    anschrift.setHausnummer(a.getHausnummer());
                    anschrift.setPostleitzahl(a.getPlz());
                    anschrift.setOrt(a.getOrt());
                    organisation.getAnschrifts().add(anschrift);
                });
            });

            TypeGDSBankverbindung bankverbindung = new TypeGDSBankverbindung();
            bankverbindung.setIban(o.getIban());
            organisation.getBankverbindungs().add(bankverbindung);

            beteiligter.getAuswahlBeteiligter().setOrganisation(organisation);
        });

        return beteiligter.getAuswahlBeteiligter() != null ? beteiligter : null;
    }

    private TypeGDSBeteiligter beteiligtePerson(Beteiligter b) {

        TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(String.valueOf(incrementCounters.incrementBeteiligtenNummer()));

        b.getNatuerlichePerson().ifPresent(p -> {

            beteiligter.setAuswahlBeteiligter(new TypeGDSBeteiligter.AuswahlBeteiligter());

            TypeGDSNatuerlichePerson person = new TypeGDSNatuerlichePerson();

            p.getVollerName().ifPresent(vn -> {
                TypeGDSNameNatuerlichePerson name = new TypeGDSNameNatuerlichePerson();
                name.setVorname(vn.getVorname());
                name.setNachname(vn.getNachname());
                name.setTitel(vn.getTitel());
                name.setNamensvorsatz(vn.getNamensvorsatz());
                name.setGeburtsname(vn.getGeburtsname());
                person.setVollerName(name);
            });

            p.getGeburt().ifPresent(g -> {
                TypeGDSGeburt geburt = new TypeGDSGeburt();
                geburt.setGeburtsdatum(g.getGeburtsdatum());
                TypeGDSOrtsangabe ortsangabe = new TypeGDSOrtsangabe();
                ortsangabe.setOrt(g.getGeburtsort());
                geburt.setGeburtsort(ortsangabe);
                person.setGeburt(geburt);
            });

            p.getGeschlecht().ifPresent(g -> {
                CodeGDSGeschlecht geschlecht = new CodeGDSGeschlecht();
                geschlecht.setCode(g.getDescriptor());
                person.setGeschlecht(geschlecht);
            });

            p.getAnschriften().ifPresent(anschriften -> {
                anschriften.forEach(a -> {
                    TypeGDSAnschrift anschrift = new TypeGDSAnschrift();
                    anschrift.setPostfachnummer(a.getPostfachnummer());
                    anschrift.setStrasse(a.getStrasse());
                    anschrift.setHausnummer(a.getHausnummer());
                    anschrift.setPostleitzahl(a.getPlz());
                    anschrift.setOrt(a.getOrt());
                    anschrift.setWohnungsgeber(a.getWohnungsgeber());
                    anschrift.setStaat((CodeGDSStaatenTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3, a.getStaat()));
                    anschrift.getAnschriftenzusatzs().add(a.getAnschriftenzusatz());
                    person.getAnschrifts().add(anschrift);

                });
            });
            beteiligter.getAuswahlBeteiligter().setNatuerlichePerson(person);
        });
        return beteiligter.getAuswahlBeteiligter() != null ? beteiligter : null;
    }

    private Beteiligung generateBeteiligungOrganisation() {

        Beteiligung beteiligungOrganisation = new Beteiligung();

        var rolle = new Rolle();
        rolle.setRollenbezeichnung(XoevCodeGDSRollenbezeichnungTyp3.ANTRAGSTELLER.getDescriptor());
        beteiligungOrganisation.addRolle(rolle);

        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setBezeichnungAktuell(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAktuell());

        var anschrift = new Anschrift();
        anschrift.setStrasse(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftStrasse());
        anschrift.setHausnummer(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftHausnummer());
        anschrift.setPlz(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftPlz());
        anschrift.setOrt(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftOrt());
        anschrift.setAnschriftstyp(XoevCodeGDSAnschriftstypen.DIENST_GESCHAEFTSANSCHRIFT.getDescriptor());
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().addAnschrift(anschrift);

        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setIban(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungBankverbindung());

        return beteiligungOrganisation;
    }

}
