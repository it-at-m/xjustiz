package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDS;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XJustizBuilder {

    protected final XJustizProperty xjustizProperty;

    protected Object createCodeGDSClass(XoevCodeGDS codeGDS, String codelistValueKey) {

        Object code = null;

        switch (codeGDS) {
        case XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP:
            CodeGDSAnschriftstyp anschriftTyp = new CodeGDSAnschriftstyp();
            anschriftTyp.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            anschriftTyp.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).getCurrentVersion());
            code = anschriftTyp;
            break;
        case CODE_GDS_SACHGEBIET_TYP_3:
            CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
            sachgebiet.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            sachgebiet.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).getCurrentVersion());
            code = sachgebiet;
            break;
        case CODE_GDS_GERICHTE_TYP_3:
            CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
            gerichtInstanzBehoerde.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            gerichtInstanzBehoerde
                    .setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).getCurrentVersion());
            code = gerichtInstanzBehoerde;
            break;
        case CODE_GDS_ROLLENBEZEICHNUNG_TYP_3:
            CodeGDSRollenbezeichnungTyp3 rollenbez = new CodeGDSRollenbezeichnungTyp3();
            rollenbez.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor())
                    .currentCodelistValueWithKey(codelistValueKey));
            rollenbez.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor()).getCurrentVersion());
            code = rollenbez;
            break;
        case CODE_GDS_STAATEN_TYP_3:
            CodeGDSStaatenTyp3 staatenTyp = new CodeGDSStaatenTyp3();
            staatenTyp.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            staatenTyp.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).getCurrentVersion());
            code = staatenTyp;
            break;
        case CODE_GDS_EREIGNIS_TYP_3:
            CodeGDSEreignisTyp3 ereignis = new CodeGDSEreignisTyp3();
            ereignis.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).getCurrentVersion());
            ereignis.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            code = ereignis;
            break;
        case CODE_GDS_DOKUMENTKLASSE:
            CodeGDSDokumentklasseTyp3 dokumentklasse = new CodeGDSDokumentklasseTyp3();
            dokumentklasse.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_DOKUMENTKLASSE.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            dokumentklasse.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_DOKUMENTKLASSE.getDescriptor()).getCurrentVersion());
            code = dokumentklasse;
            break;
        case CODE_GDS_BESTANDTEILTYP:
            CodeGDSBestandteiltyp bestandteiltyp = new CodeGDSBestandteiltyp();
            bestandteiltyp.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            bestandteiltyp.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP.getDescriptor()).getCurrentVersion());
            code = bestandteiltyp;
            break;
        case CODE_GDS_AKTENTYP:
            CodeGDSAktentyp aktentyp = new CodeGDSAktentyp();
            aktentyp.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_AKTENTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            aktentyp.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_AKTENTYP.getDescriptor()).getCurrentVersion());
            code = aktentyp;
            break;
        case CODE_GDS_AKTENZEICHENART:
            CodeGDSAktenzeichenart aktenzeichenart = new CodeGDSAktenzeichenart();
            aktenzeichenart.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_AKTENZEICHENART.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            aktenzeichenart.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_AKTENZEICHENART.getDescriptor()).getCurrentVersion());
            code = aktenzeichenart;
            break;
        case CODE_GDS_REGISTERZEICHEN:
            CodeGDSRegisterzeichenTyp3 registerzeichen = new CodeGDSRegisterzeichenTyp3();
            registerzeichen.setCode(
                    xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_REGISTERZEICHEN.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
            registerzeichen.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_REGISTERZEICHEN.getDescriptor()).getCurrentVersion());
            code = registerzeichen;
            break;

        }
        return code;
    }

}
