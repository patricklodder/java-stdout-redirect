package eu.plddr.hacks.stdout;

import java.io.OutputStream;
import java.io.PrintStream;

public class ConditionalStreamMultiplexer {

    private static final PrintStream originalStdOut = System.out;

    private final PrintStream printStream;

    protected ConditionalStreamMultiplexer(OutputStream outputStream) {
        this.printStream = new PrintStream(outputStream);
    }

    public void assign() {
        System.setOut(printStream);
    }

    protected OutputStream getOriginalStdOut() {
        return originalStdOut;
    }

}
