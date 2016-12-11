package eu.plddr.hacks.stdout;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ConditionalStreamMultiplexer extends OutputStream {

    private static final PrintStream originalStdOut = System.out;

    private final List<StreamAndCondition> streamList = new ArrayList<StreamAndCondition>();

    public ConditionalStreamMultiplexer(String stdOutCondition, boolean stdOutNegate) {
        streamList.add(new StreamAndCondition(originalStdOut, stdOutCondition, stdOutNegate));
    }

    public void register() {
        System.setOut(new PrintStream(this));
    }

    public void unregister() {
        System.setOut(originalStdOut);
    }

    public void addConditionalStream(OutputStream stream, String condition, boolean negate) {
        streamList.add(new StreamAndCondition(stream, condition, negate));
    }

    protected OutputStream getOriginalStdOut() {
        return originalStdOut;
    }

    @Override
    public void write(int b) throws IOException {
        for (StreamAndCondition sac : streamList) {
            if (sac.matchesInt())
                sac.getStream().write(b);
        }
    }

    @Override
    public void write(byte b[]) throws IOException {
        for (StreamAndCondition sac : streamList) {
            if (sac.matchesInput(b))
                sac.getStream().write(b);
        }
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        byte[] cmp = new byte[len];

        if (b == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }

        for (int i = 0 ; i < len ; i++) {
            cmp[i] = b[i+off];
        }

        write(cmp);
    }

}
