package net.sharkfw.apps.fb.util.facebook;

import java.io.IOException;

public class FBSerializeException extends Throwable {
    public FBSerializeException(IOException ex) {
        super(ex);
    }

    public FBSerializeException(String message) {
        super(message);
    }
}
