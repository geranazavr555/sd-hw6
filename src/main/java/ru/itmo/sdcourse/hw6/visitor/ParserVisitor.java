package ru.itmo.sdcourse.hw6.visitor;

import ru.itmo.sdcourse.hw6.token.Brace;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;
import ru.itmo.sdcourse.hw6.token.Token;

import java.util.ArrayList;
import java.util.List;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> output = new ArrayList<>();
    private final List<Token> stack = new ArrayList<>();
    private RuntimeException delayedException;

    @Override
    public void visit(NumberToken token) {
        if (delayedException != null)
            return;

        output.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (delayedException != null)
            return;

        switch (token.type()) {
            case LEFT -> {
                stack.add(token);
            }
            case RIGHT -> {
                while (true) {
                    if (stack.isEmpty()) {
                        throwDelayedException("Missing parenthesis");
                        return;
                    }

                    Token topToken = stack.remove(stack.size() - 1);
                    if (!(topToken instanceof Brace))
                        output.add(topToken);
                    else
                        break;
                }
            }
        }
    }

    @Override
    public void visit(Operation token) {
        if (delayedException != null)
            return;

        while (!stack.isEmpty()) {
            Token topToken = stack.get(stack.size() - 1);
            if (topToken instanceof Operation topOp) {
                if (topOp.type().getPriority() <= token.type().getPriority()) {
                    output.add(stack.remove(stack.size() - 1));
                } else
                    break;
            } else
                break;
        }
        stack.add(token);
    }

    public List<Token> getOutput() {
        if (delayedException != null)
            throw delayedException;

        while (!stack.isEmpty()) {
            Token topToken = stack.remove(stack.size() - 1);
            if (topToken instanceof Brace) {
                throwDelayedException("Missing parenthesis");
            }
            output.add(topToken);
        }

        if (delayedException != null)
            throw delayedException;

        return output;
    }

    private void throwDelayedException(String message) {
        delayedException = new RuntimeException(message);
    }
}
