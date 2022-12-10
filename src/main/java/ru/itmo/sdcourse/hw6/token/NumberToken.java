package ru.itmo.sdcourse.hw6.token;

import ru.itmo.sdcourse.hw6.visitor.TokenVisitor;

public record NumberToken(double value) implements Token {
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
