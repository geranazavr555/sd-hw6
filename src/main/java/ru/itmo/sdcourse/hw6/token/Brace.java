package ru.itmo.sdcourse.hw6.token;

import ru.itmo.sdcourse.hw6.visitor.TokenVisitor;

public record Brace(Brace.Type type) implements Token {
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public enum Type implements Token.Type {
        LEFT, RIGHT
    }
}
