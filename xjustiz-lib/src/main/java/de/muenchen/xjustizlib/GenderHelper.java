package de.muenchen.xjustizlib;

import de.muenchen.xjustizlib.xoev.codelisten.XoevGeschlecht;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenderHelper {

    /**
     *
     * @param gender as String (W,M,S,D)
     * @return XoevGeschlecht
     */
    public static XoevGeschlecht supplyXoevGeschlecht(String gender) {

        switch (gender.trim().toUpperCase()) {

        case "M":
            return XoevGeschlecht.MAENNLICH;
        case "W":
            return XoevGeschlecht.WEIBLICH;
        case "D":
            return XoevGeschlecht.DIVERS;
        case "S":
            return XoevGeschlecht.SAECHLICH;
        default:
            return XoevGeschlecht.UNBEKANNT;

        }
    }

}
