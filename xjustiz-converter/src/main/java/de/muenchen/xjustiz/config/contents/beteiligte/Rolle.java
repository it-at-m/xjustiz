package de.muenchen.xjustiz.config.contents.beteiligte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rolle {

    private BigInteger laufendeNummer;
    private String rollennummer;
    private String rollenbezeichnung;

}
