package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;
import lombok.Getter;
import java.util.Optional;

@Getter
public class FachspezifischeDatenAkte {
    private final AktenzeichenStrukuriert aktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert;
    private final String freitext;
    private final boolean aktenzeichenArt;

    private FachspezifischeDatenAkte(Builder builder) {
        this.aktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert = builder.aktenzeichen;
        this.freitext = builder.freitext;
        this.aktenzeichenArt = builder.aktenzeichenArt;
    }

    public static class Builder {
        private AktenzeichenStrukuriert aktenzeichen;
        private String freitext;
        private boolean aktenzeichenArt;

        public Builder choiceAktenzeichen(AktenzeichenStrukuriert aktenzeichen, boolean aktenzeichenArt) {
            this.aktenzeichen = aktenzeichen;
            this.aktenzeichenArt = aktenzeichenArt;
            return this;
        }

        public Builder choiceFreitext(String freitext, boolean aktenzeichenArt) {
            this.freitext = freitext;
            this.aktenzeichenArt = aktenzeichenArt;
            return this;
        }

        public FachspezifischeDatenAkte build() {
            if ((aktenzeichen != null && freitext != null) || (aktenzeichen == null && freitext == null)) {
                throw new IllegalStateException("Entweder aktenzeichen oder freitext muss gesetzt sein, aber nicht beide.");
            }
            return new FachspezifischeDatenAkte(this);
        }
    }

    public Optional<AktenzeichenStrukuriert> getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert() {
        return Optional.ofNullable(aktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert);
    }

    public Optional<String> getFreitext() {
        return Optional.ofNullable(freitext);
    }

}
