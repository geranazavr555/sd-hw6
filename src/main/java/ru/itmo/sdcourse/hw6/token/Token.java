package ru.itmo.sdcourse.hw6.token;

import ru.itmo.sdcourse.hw6.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);

    interface Type {
    }
}
