package ru.itmo.sdcourse.hw6.visitor;

import ru.itmo.sdcourse.hw6.token.Brace;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;
import ru.itmo.sdcourse.hw6.token.Token;

import java.util.ArrayList;
import java.util.List;

public class CalcVisitor implements TokenVisitor {
    private final List<Token> stack = new ArrayList<>();
    private RuntimeException delayedException;

    @Override
    public void visit(NumberToken token) {
        if (delayedException != null)
            return;

        stack.add(token);
    }

    @Override
    public void visit(Brace token) {
        throwDelayedException("Parenthesis are not allowed in reversed Polish notation");
    }

    @Override
    public void visit(Operation token) {
        if (delayedException != null)
            return;

        if (stack.size() < 2) {
            throwDelayedException("Incorrect expression");
            return;
        }

        Token op2 = stack.remove(stack.size() - 1);
        Token op1 = stack.remove(stack.size() - 1);
        if (!(op1 instanceof NumberToken) || !(op2 instanceof NumberToken)) {
            throwDelayedException("Incorrect expression");
            return;
        }

        double leftValue = ((NumberToken) op1).value();
        double rightValue = ((NumberToken) op2).value();
        stack.add(new NumberToken(switch (token.type()) {
            case SUM -> leftValue + rightValue;
            case SUBTRACT -> leftValue - rightValue;
            case MULTIPLY -> leftValue * rightValue;
            case DIVIDE -> leftValue / rightValue;
        }));
    }

    public double getResult() {
        if (stack.size() != 1 || !(stack.get(0) instanceof NumberToken)) {
            throwDelayedException("Incorrect expression");
        }

        if (delayedException != null)
            throw delayedException;

        return ((NumberToken) stack.get(0)).value();
    }

    private void throwDelayedException(String message) {
        delayedException = new RuntimeException(message);
    }
}
