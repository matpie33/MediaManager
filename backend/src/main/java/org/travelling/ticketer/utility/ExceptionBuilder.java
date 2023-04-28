package org.travelling.ticketer.utility;

import java.util.function.Supplier;

public class ExceptionBuilder {

    public static Supplier<IllegalArgumentException> createIllegalArgumentException(String ex){
        return () -> new IllegalArgumentException(ex);
    }

}
