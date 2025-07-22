package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Builder {

    protected final XJustizProperty xjustizProperty;

    protected final NachrichtenProperty nachrichtenProperty;

    protected Object createCodeGDSClass(XoevCodeGDS codeGDS, String codelistValueKey) {

        Object code = null;

        switch (codeGDS) {
            case XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP:
                CodeGDSAnschriftstyp anschriftTyp = new CodeGDSAnschriftstyp();
                anschriftTyp.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                anschriftTyp.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP.getDescriptor()).getCurrentVersion());
                code = anschriftTyp;
                break;
            case CODE_GDS_SACHGEBIET_TYP_3:
                CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
                sachgebiet.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                sachgebiet.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_SACHGEBIET_TYP_3.getDescriptor()).getCurrentVersion());
                code = sachgebiet;
                break;
            case CODE_GDS_GERICHTE_TYP_3:
                CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
                gerichtInstanzBehoerde.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                gerichtInstanzBehoerde.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3.getDescriptor()).getCurrentVersion());
                code = gerichtInstanzBehoerde;
                break;
            case CODE_GDS_ROLLENBEZEICHNUNG_TYP_3:
                CodeGDSRollenbezeichnungTyp3 rollenbez = new CodeGDSRollenbezeichnungTyp3();
                rollenbez.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                rollenbez.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_ROLLENBEZEICHNUNG_TYP_3.getDescriptor()).getCurrentVersion());
                code = rollenbez;
                break;
            case CODE_GDS_STAATEN_TYP_3:
                CodeGDSStaatenTyp3 staatenTyp = new CodeGDSStaatenTyp3();
                staatenTyp.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                staatenTyp.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_STAATEN_TYP_3.getDescriptor()).getCurrentVersion());
                code = staatenTyp;
                break;
            case CODE_GDS_EREIGNIS_TYP_3:
                CodeGDSEreignisTyp3 ereignis = new CodeGDSEreignisTyp3();
                ereignis.setListVersionID(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).getCurrentVersion());
                ereignis.setCode(xjustizProperty.getCodelisten().get(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3.getDescriptor()).currentCodelistValueWithKey(codelistValueKey));
                code = ereignis;
                break;
        }
        return code;
    }
}
