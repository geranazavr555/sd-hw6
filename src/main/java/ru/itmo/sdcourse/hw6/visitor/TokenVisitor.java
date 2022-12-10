package ru.itmo.sdcourse.hw6.visitor;

import ru.itmo.sdcourse.hw6.token.Brace;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
