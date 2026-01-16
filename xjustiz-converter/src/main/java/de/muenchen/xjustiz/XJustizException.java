package de.muenchen.xjustiz;

import java.io.Serial;

public class XJustizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7252707611253927400L;

    public XJustizException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public XJustizException(final String message) {
        super(message);
    }

}
