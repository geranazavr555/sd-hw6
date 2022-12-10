package ru.itmo.sdcourse.hw6.token;

import ru.itmo.sdcourse.hw6.visitor.TokenVisitor;

public record Operation(Operation.Type type) implements Token {
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public enum Type implements Token.Type {
        SUM(1), SUBTRACT(1), MULTIPLY(0), DIVIDE(0);

        private final int priority;

        Type(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }
}
