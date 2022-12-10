package ru.itmo.sdcourse.hw6.visitor;

import ru.itmo.sdcourse.hw6.token.Brace;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PrintVisitor implements TokenVisitor {
    private final PrintStream printStream;
    private boolean start = true;

    public PrintVisitor(PrintStream printStream) {
        this.printStream = printStream;
    }

    private void handleStart() {
        if (start)
            start = false;
        else
            printStream.print(" ");
    }

    @Override
    public void visit(NumberToken token) {
        handleStart();
        double value = token.value();
        if (Math.abs(value - Math.round(value)) < 1e-8)
            printStream.print(Math.round(value));
        else
            printStream.print(value);
    }

    @Override
    public void visit(Brace token) {
        handleStart();
        switch (token.type()) {
            case LEFT -> printStream.print("(");
            case RIGHT -> printStream.print(")");
        }
    }

    @Override
    public void visit(Operation token) {
        handleStart();
        switch (token.type()) {
            case SUM -> printStream.print("+");
            case SUBTRACT -> printStream.print("-");
            case MULTIPLY -> printStream.print("*");
            case DIVIDE -> printStream.print("/");
        }
    }

    public static PrintVisitor of(OutputStream outputStream) {
        return new PrintVisitor(new PrintStream(outputStream));
    }
}
