package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.Tatort;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
public class FachdatenContent {

    private Double geldbusse;
    private Double auslagen;

    private LocalDateTime datumUhrzeit;

    private LocalDateTime anfangsDatumUhrzeit;

    private LocalDateTime endeDatumUhrzeit;

    private final List<Tatort> tatorte = new ArrayList<>();

}
