package ru.itmo.sdcourse.hw6;

import ru.itmo.sdcourse.hw6.token.Token;
import ru.itmo.sdcourse.hw6.tokenizer.Tokenizer;
import ru.itmo.sdcourse.hw6.visitor.CalcVisitor;
import ru.itmo.sdcourse.hw6.visitor.ParserVisitor;
import ru.itmo.sdcourse.hw6.visitor.PrintVisitor;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final InputStream in;
    private final PrintStream out;
    private final PrintStream err;

    public Main(InputStream in, PrintStream out, PrintStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    private void handleException(Exception e) {
        out.println("Error: " + e.getMessage());
        e.printStackTrace(err);
    }

    private boolean iteration() {
        try {
            Scanner scanner = new Scanner(new InputStreamReader(in));
            if (!scanner.hasNextLine())
                return false;

            String line = scanner.nextLine();

            if ("exit".equalsIgnoreCase(line))
                return false;

            ParserVisitor parserVisitor = new ParserVisitor();
            Tokenizer.of(line).tokenize().forEach(token -> token.accept(parserVisitor));
            List<Token> rpnTokens = parserVisitor.getOutput();

            out.print("RPN: ");
            PrintVisitor printVisitor = PrintVisitor.of(out);
            rpnTokens.forEach(token -> token.accept(printVisitor));
            out.println();

            CalcVisitor calcVisitor = new CalcVisitor();
            rpnTokens.forEach(token -> token.accept(calcVisitor));
            out.println("Value: " + calcVisitor.getResult());
        } catch (Exception e) {
            handleException(e);
        }
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main(System.in, System.out, System.err);
        //noinspection StatementWithEmptyBody
        while (main.iteration()) {}
    }
}
