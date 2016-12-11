package eu.plddr.hacks.stdout;

import java.io.OutputStream;

public class StreamAndCondition {

    private final OutputStream stream;
    private final String condition;
    private final boolean negate;

    public StreamAndCondition(OutputStream stream, String condition, boolean negate) {
        this.stream = stream;
        this.condition = condition;
        this.negate = negate;
    }

    public boolean matchesInput(byte[] data) {
        if (condition == null)
            return !negate;

        return (data.length != 0) && ((new String(data)).matches(condition)) ^ negate;
    }

    public boolean matchesInt() {
        return condition != null || !negate;

    }

    public OutputStream getStream() {
        return stream;
    }
}
