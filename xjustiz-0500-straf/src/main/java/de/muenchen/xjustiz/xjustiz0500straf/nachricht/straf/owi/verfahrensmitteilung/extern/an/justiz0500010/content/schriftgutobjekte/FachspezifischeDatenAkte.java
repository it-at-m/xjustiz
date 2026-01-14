package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte;

import java.util.Optional;
import lombok.Getter;

@Getter
public final class FachspezifischeDatenAkte {
    private final AktenzeichenStrukuriert aktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert;
    private final String freitext;
    private final boolean aktenzeichenArt;

    private FachspezifischeDatenAkte(final Builder builder) {
        this.aktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert = builder.aktenzeichen;
        this.freitext = builder.freitext;
        this.aktenzeichenArt = builder.aktenzeichenArt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AktenzeichenStrukuriert aktenzeichen;
        private String freitext;
        private boolean aktenzeichenArt;

        public Builder choiceAktenzeichen(final AktenzeichenStrukuriert aktenzeichen, final boolean aktenzeichenArt) {
            this.aktenzeichen = aktenzeichen;
            this.aktenzeichenArt = aktenzeichenArt;
            return this;
        }

        public Builder choiceFreitext(final String freitext, final boolean aktenzeichenArt) {
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
