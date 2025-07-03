package de.muenchen.xjustiz.config.contents;

import de.muenchen.xjustiz.config.contents.fachdaten.Tatort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public class Fachdaten {

    @Getter
    @Setter
    private LocalDateTime anfangsDatumUhrzeit;

    @Getter
    @Setter
    private LocalDateTime endeDatumUhrzeit;

    private List<Tatort> tatorte = new ArrayList<>();

    public List<Tatort> getTatorte() {
        return tatorte;
    }

}
