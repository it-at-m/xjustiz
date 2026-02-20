package de.muenchen.xjustizlib;

import java.io.Serial;

public class XJustizLibException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7252707611253927400L;

    public XJustizLibException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public XJustizLibException(final String message) {
        super(message);
    }

}
