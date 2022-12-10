package ru.itmo.sdcourse.hw6.tokenizer;

import ru.itmo.sdcourse.hw6.token.Token;

interface State<T extends Token> {
    void process(char ch);

    T createToken();

    boolean isCharAcceptable(char ch);

    void reset();

    boolean needFinalize(Character ch);
}
