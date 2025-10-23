package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.Tatort;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
public class FachdatenContent {

    @Getter
    @Setter
    private LocalDateTime anfangsDatumUhrzeit;

    @Getter
    @Setter
    private LocalDateTime endeDatumUhrzeit;

    private final List<Tatort> tatorte = new ArrayList<>();

    public List<Tatort> getTatorte() {
        return tatorte;
    }

}
