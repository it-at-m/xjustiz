package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xoev.CodelistenProperty;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDS;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XJustizBuilder {

    protected final XJustizProperty xjustizProperty;

    protected Object createCodeGDSClass(final XoevCodeGDS codeGDS, final String codelistValueKey) {

        Object code = null;

        switch (codeGDS) {
        case XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP:
            final CodeGDSAnschriftstyp anschriftTyp = new CodeGDSAnschriftstyp();
            anschriftTyp.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            anschriftTyp.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).getCurrentVersion());
            code = anschriftTyp;
            break;
        case CODE_GDS_SACHGEBIET_TYP_3:
            final CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
            sachgebiet.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            sachgebiet.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).getCurrentVersion());
            code = sachgebiet;
            break;
        case CODE_GDS_GERICHTE_TYP_3:
            final CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
            gerichtInstanzBehoerde.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            gerichtInstanzBehoerde
                    .setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).getCurrentVersion());
            code = gerichtInstanzBehoerde;
            break;
        case CODE_GDS_ROLLENBEZEICHNUNG_TYP_3:
            final CodeGDSRollenbezeichnungTyp3 rollenbez = new CodeGDSRollenbezeichnungTyp3();
            rollenbez.setCode(getCodelistenProperty(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor())
                    .currentCodelistValueWithKey(codelistValueKey));
            rollenbez.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor()).getCurrentVersion());
            code = rollenbez;
            break;
        case CODE_GDS_STAATEN_TYP_3:
            final CodeGDSStaatenTyp3 staatenTyp = new CodeGDSStaatenTyp3();
            staatenTyp.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            staatenTyp.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).getCurrentVersion());
            code = staatenTyp;
            break;
        case CODE_GDS_EREIGNIS_TYP_3:
            final CodeGDSEreignisTyp3 ereignis = new CodeGDSEreignisTyp3();
            ereignis.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).getCurrentVersion());
            ereignis.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            code = ereignis;
            break;
        case CODE_GDS_DOKUMENTKLASSE:
            final CodeGDSDokumentklasseTyp3 dokumentklasse = new CodeGDSDokumentklasseTyp3();
            dokumentklasse.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_DOKUMENTKLASSE.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            dokumentklasse.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_DOKUMENTKLASSE.getDescriptor()).getCurrentVersion());
            code = dokumentklasse;
            break;
        case CODE_GDS_BESTANDTEILTYP:
            final CodeGDSBestandteiltyp bestandteiltyp = new CodeGDSBestandteiltyp();
            bestandteiltyp.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            bestandteiltyp.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP.getDescriptor()).getCurrentVersion());
            code = bestandteiltyp;
            break;
        case CODE_GDS_AKTENTYP:
            final CodeGDSAktentyp aktentyp = new CodeGDSAktentyp();
            aktentyp.setCode(getCodelistenProperty(XoevCodeGDS.CODE_GDS_AKTENTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            aktentyp.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_AKTENTYP.getDescriptor()).getCurrentVersion());
            code = aktentyp;
            break;
        case CODE_GDS_AKTENZEICHENART:
            final CodeGDSAktenzeichenart aktenzeichenart = new CodeGDSAktenzeichenart();
            aktenzeichenart.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_AKTENZEICHENART.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            aktenzeichenart.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_AKTENZEICHENART.getDescriptor()).getCurrentVersion());
            code = aktenzeichenart;
            break;
        case CODE_GDS_REGISTERZEICHEN:
            final CodeGDSRegisterzeichenTyp3 registerzeichen = new CodeGDSRegisterzeichenTyp3();
            registerzeichen.setCode(
                    getCodelistenProperty(XoevCodeGDS.CODE_GDS_REGISTERZEICHEN.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            registerzeichen.setListVersionID(getCodelistenProperty(XoevCodeGDS.CODE_GDS_REGISTERZEICHEN.getDescriptor()).getCurrentVersion());
            code = registerzeichen;
            break;

        }
        return code;
    }

    private CodelistenProperty getCodelistenProperty(final String key) {
        final Optional<CodelistenProperty> property = Optional.ofNullable(xjustizProperty.getCodelisten().get(key));
        return property.orElseThrow(() -> new RuntimeException("Could not find code listen property with key: " + key));
    }

}
