package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.codelisten.XoevCodeGDS;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Builder {

    protected final NachrichtenProperty nachrichtenProperty;

    protected Object createCodeGDSClass(XoevCodeGDS codeGDS, String codelistValueKey) {

        Object returnCode = null;

        switch (codeGDS) {
            case XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP:
                CodeGDSAnschriftstyp anschriftTyp = new CodeGDSAnschriftstyp();
                anschriftTyp.setCode(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                anschriftTyp.setListVersionID(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).getCurrentVersion());
                returnCode = anschriftTyp;
                break;
            case CODE_GDS_SACHGEBIET_TYP_3:
                CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
                sachgebiet.setCode(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                sachgebiet.setListVersionID(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).getCurrentVersion());
                returnCode = sachgebiet;
                break;
            case CODE_GDS_GERICHTE_TYP_3:
                CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
                gerichtInstanzBehoerde.setCode(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                gerichtInstanzBehoerde.setListVersionID(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).getCurrentVersion());
                returnCode = gerichtInstanzBehoerde;
                break;
            case CODE_GDS_ROLLENBEZEICHNUNG_TYP_3:
                CodeGDSRollenbezeichnungTyp3 rollenbez = new CodeGDSRollenbezeichnungTyp3();
                rollenbez.setCode(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                rollenbez.setListVersionID(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor()).getCurrentVersion());
                returnCode = rollenbez;
                break;
            case CODE_GDS_STAATEN_TYP_3:
                CodeGDSStaatenTyp3 staatenTyp = new CodeGDSStaatenTyp3();
                staatenTyp.setCode(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                staatenTyp.setListVersionID(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).getCurrentVersion());
                returnCode = staatenTyp;
                break;
            case CODE_GDS_EREIGNIS_TYP_3:
                CodeGDSEreignisTyp3 ereignis = new CodeGDSEreignisTyp3();
                ereignis.setListVersionID(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).getCurrentVersion());
                ereignis.setCode(nachrichtenProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                returnCode = ereignis;
                break;
        }
        return returnCode;
    }
}
