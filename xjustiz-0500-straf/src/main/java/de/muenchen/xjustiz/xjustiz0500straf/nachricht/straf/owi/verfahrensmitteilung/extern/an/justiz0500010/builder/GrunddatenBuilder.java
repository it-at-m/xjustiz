package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSAnschriftstyp;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSGeschlecht;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSRollenbezeichnungTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSSachgebietTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSStaatenTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSAktenzeichen;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSAnschrift;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSBankverbindung;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSBehoerde;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSBeteiligter;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSBeteiligung;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSGeburt;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSInstanzdaten;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSNameNatuerlichePerson;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSNatuerlichePerson;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSOrganisation;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSOrtsangabe;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSRefBeteiligtennummer;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.GrunddatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung.Anschrift;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung.Beteiligter;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung.Rolle;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.instanzdaten.Aktenzeichen;
import de.muenchen.xjustizlib.xoev.XJustizProperty;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSAnschriftstypen;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSGerichteTyp3;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSRollenbezeichnungTyp3;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSSachgebietTyp3;
import org.springframework.stereotype.Component;

@Component
public class GrunddatenBuilder extends Builder {

    private final IncrementCounters incrementCounters = new IncrementCounters();

    public GrunddatenBuilder(final XJustizProperty xjustizProperty, final NachrichtenProperty nachrichtenProperty) {
        super(xjustizProperty, nachrichtenProperty);
    }

    public TypeGDSGrunddaten build(final GrunddatenContent grunddatenContent) {

        incrementCounters.reset();

        /*
         * The organization can be defined statically once in the properties or transferred dynamically with
         * the natural persons.
         */
        if (nachrichtenProperty.isOrganisationConfiguredInApplicationProperties()) {
            grunddatenContent.getBeteiligungen().ifPresent(b -> b.add(createApplicant()));
        }

        /**
         * Grunddaten
         * - Verfahrensdaten
         * - Instanzdaten
         * - Sachgebiet
         * - AuswahlInstanzbehoerde
         * - Beteiligung
         * - Rolle
         * - Rollennummer
         * - Rollenbezeichnung
         * - Beteiligter
         * - Beteiligtennummer
         * - AuswahlBeteiligter
         */

        final TypeGDSGrunddaten grunddaten = new TypeGDSGrunddaten();

        final TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten = new TypeGDSGrunddaten.Verfahrensdaten();

        grunddatenContent.getInstanzdaten().ifPresent(instanzen -> instanzen.forEach((type, fileNumber) -> {

            switch (type) {
            case BETEILIGTER: {
                handleBeteiligter(fileNumber, verfahrensdaten);
                break;
            }
            case GERICHT: {
                handleGericht(fileNumber, verfahrensdaten);
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown instance type: " + type);
            }
        }));

        grunddaten.setVerfahrensdaten(verfahrensdaten);

        grunddatenContent.getBeteiligungen()
                .ifPresent(beteiligungen -> beteiligungen.forEach(beteiligung -> verfahrensdaten.getBeteiligung().add(beteiligungBuilder(beteiligung))));

        return grunddaten;
    }

    private void handleGericht(final Aktenzeichen fileNumber, final TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten) {
        final TypeGDSInstanzdaten instanzGericht = new TypeGDSInstanzdaten();
        final TypeGDSBehoerde behoerde = new TypeGDSBehoerde();

        instanzGericht.setInstanznummer("1");
        instanzGericht.setAuswahlInstanzbehoerde(behoerde);

        instanzGericht.setSachgebiet(
                (CodeGDSSachgebietTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3,
                        XoevCodeGDSSachgebietTyp3.OWI_SACHEN.getDescriptor()));

        instanzGericht.getAuswahlInstanzbehoerde().setGericht(
                (CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3,
                        XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));

        final TypeGDSAktenzeichen aktenzeichen = new TypeGDSAktenzeichen();
        final TypeGDSAktenzeichen.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSAktenzeichen.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenFreitext(fileNumber.getFreitext());
        aktenzeichen.setAuswahlAktenzeichen(auswahlAktenzeichen);
        instanzGericht.setAktenzeichen(aktenzeichen);

        verfahrensdaten.getInstanzdaten().add(instanzGericht);
    }

    private static void handleBeteiligter(final Aktenzeichen fileNumber, final TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten) {
        final TypeGDSInstanzdaten instanzSachgebietBeteiligter = new TypeGDSInstanzdaten();
        instanzSachgebietBeteiligter.setInstanznummer("0");
        final TypeGDSBehoerde abteilung = new TypeGDSBehoerde();
        final TypeGDSRefBeteiligtennummer beteiligtenNummer = new TypeGDSRefBeteiligtennummer();
        beteiligtenNummer.setRefBeteiligtennummer("2");
        abteilung.setBeteiligter(beteiligtenNummer);
        instanzSachgebietBeteiligter.setAuswahlInstanzbehoerde(abteilung);

        final TypeGDSAktenzeichen aktenzeichen = new TypeGDSAktenzeichen();
        final TypeGDSAktenzeichen.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSAktenzeichen.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenFreitext(fileNumber.getFreitext());
        aktenzeichen.setAuswahlAktenzeichen(auswahlAktenzeichen);
        instanzSachgebietBeteiligter.setAktenzeichen(aktenzeichen);

        verfahrensdaten.getInstanzdaten().add(instanzSachgebietBeteiligter);
    }

    private TypeGDSBeteiligung beteiligungBuilder(final Beteiligung beteiligungContent) {

        final TypeGDSBeteiligung xjustizBeteiligung = new TypeGDSBeteiligung();

        beteiligungContent.getRollen().ifPresent(rollen -> {
            rollen.forEach(r -> {
                final TypeGDSBeteiligung.Rolle rolle = new TypeGDSBeteiligung.Rolle();
                rolle.setRollennummer(String.valueOf(incrementCounters.incrementEntireXmlRollennummer()));
                rolle.setNr(incrementCounters.incrementRollenbezeichnungCounter(r.getRollenbezeichnung()));
                rolle.setRollenbezeichnung(
                        (CodeGDSRollenbezeichnungTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3, r.getRollenbezeichnung()));
                xjustizBeteiligung.getRolle().add(rolle);
            });
        });

        beteiligungContent.getBeteiligter().ifPresent(b -> {

            if (b.getOrganisation().isPresent()) {
                xjustizBeteiligung.setBeteiligter(beteiligteOrganisation(b));
            } else {
                xjustizBeteiligung.setBeteiligter(beteiligtePerson(b));
            }

        });
        return xjustizBeteiligung;
    }

    private TypeGDSBeteiligter beteiligteOrganisation(final Beteiligter b) {

        final TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(String.valueOf(incrementCounters.incrementBeteiligtenNummer()));

        b.getOrganisation().ifPresent(o -> {
            final TypeGDSOrganisation organisation = new TypeGDSOrganisation();

            beteiligter.setAuswahlBeteiligter(new TypeGDSBeteiligter.AuswahlBeteiligter());

            final TypeGDSOrganisation.Bezeichnung bezeichnung = new TypeGDSOrganisation.Bezeichnung();
            bezeichnung.setBezeichnungAktuell(o.getBezeichnungAktuell());
            organisation.setBezeichnung(bezeichnung);

            o.getAnschriftenList().ifPresent(anschriften -> {
                anschriften.forEach(a -> {
                    final TypeGDSAnschrift anschrift = new TypeGDSAnschrift();
                    anschrift.setAnschriftstyp((CodeGDSAnschriftstyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP, a.getAnschriftstyp()));
                    anschrift.setStrasse(a.getStrasse());
                    anschrift.setHausnummer(a.getHausnummer());
                    anschrift.setPostleitzahl(a.getPlz());
                    anschrift.setOrt(a.getOrt());
                    organisation.getAnschrift().add(anschrift);
                });
            });

            final TypeGDSBankverbindung bankverbindung = new TypeGDSBankverbindung();
            bankverbindung.setIban(o.getIban());
            organisation.getBankverbindung().add(bankverbindung);

            beteiligter.getAuswahlBeteiligter().setOrganisation(organisation);
        });

        return beteiligter.getAuswahlBeteiligter() != null ? beteiligter : null;
    }

    private TypeGDSBeteiligter beteiligtePerson(final Beteiligter b) {

        final TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(String.valueOf(incrementCounters.incrementBeteiligtenNummer()));

        b.getNatuerlichePerson().ifPresent(p -> {

            beteiligter.setAuswahlBeteiligter(new TypeGDSBeteiligter.AuswahlBeteiligter());

            final TypeGDSNatuerlichePerson person = new TypeGDSNatuerlichePerson();

            p.getVollerName().ifPresent(vn -> {
                final TypeGDSNameNatuerlichePerson name = new TypeGDSNameNatuerlichePerson();
                name.setVorname(vn.getVorname());
                name.setNachname(vn.getNachname());
                name.setTitel(vn.getTitel());
                name.setNamensvorsatz(vn.getNamensvorsatz());
                name.setGeburtsname(vn.getGeburtsname());
                person.setVollerName(name);
            });

            p.getGeburt().ifPresent(g -> {
                final TypeGDSGeburt geburt = new TypeGDSGeburt();
                geburt.setGeburtsdatum(g.getGeburtsdatum());
                final TypeGDSOrtsangabe ortsangabe = new TypeGDSOrtsangabe();
                ortsangabe.setOrt(g.getGeburtsort());
                geburt.setGeburtsort(ortsangabe);
                person.setGeburt(geburt);
            });

            p.getGeschlecht().ifPresent(g -> {
                final CodeGDSGeschlecht geschlecht = new CodeGDSGeschlecht();
                geschlecht.setCode(g.getDescriptor());
                person.setGeschlecht(geschlecht);
            });

            p.getAnschriftenList().ifPresent(anschriften -> {
                anschriften.forEach(a -> {
                    final TypeGDSAnschrift anschrift = new TypeGDSAnschrift();
                    anschrift.setPostfachnummer(a.getPostfachnummer());
                    anschrift.setStrasse(a.getStrasse());
                    anschrift.setHausnummer(a.getHausnummer());
                    anschrift.setPostleitzahl(a.getPlz());
                    anschrift.setOrt(a.getOrt());
                    anschrift.setWohnungsgeber(a.getWohnungsgeber());
                    anschrift.setStaat((CodeGDSStaatenTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3, a.getStaat()));
                    anschrift.getAnschriftenzusatz().add(a.getAnschriftenzusatz());
                    person.getAnschrift().add(anschrift);

                });
            });
            beteiligter.getAuswahlBeteiligter().setNatuerlichePerson(person);
        });
        return beteiligter.getAuswahlBeteiligter() != null ? beteiligter : null;
    }

    private Beteiligung createApplicant() {

        final Beteiligung beteiligung = new Beteiligung();

        final Rolle rolle = new Rolle();
        rolle.setRollenbezeichnung(XoevCodeGDSRollenbezeichnungTyp3.BUSSGELDEMPFAENGER.getDescriptor());
        beteiligung.addRolle(rolle);

        beteiligung.generateBeteiligter().generateOrganisation()
                .setBezeichnungAktuell(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAktuell());

        final Anschrift anschrift = new Anschrift();
        anschrift.setStrasse(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftStrasse());
        anschrift
                .setHausnummer(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftHausnummer());
        anschrift.setPlz(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftPlz());
        anschrift.setOrt(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftOrt());
        anschrift.setAnschriftstyp(XoevCodeGDSAnschriftstypen.DIENST_GESCHAEFTSANSCHRIFT.getDescriptor());
        beteiligung.generateBeteiligter().generateOrganisation().addAnschrift(anschrift);

        beteiligung.generateBeteiligter().generateOrganisation()
                .setIban(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungBankverbindung());

        return beteiligung;
    }

}
