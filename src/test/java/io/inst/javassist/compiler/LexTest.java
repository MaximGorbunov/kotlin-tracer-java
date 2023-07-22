package io.inst.javassist.compiler;

import io.inst.javassist.compiler.Lex;
import io.inst.javassist.compiler.TokenId;

public class LexTest implements TokenId {
    public static void main(String[] args) {
	Lex lex = new Lex(args[0]);
	int t;
	while ((t = lex.get()) > 0) {
	    System.out.print(t);
	    if (t == Identifier || t == StringL)
		System.out.print("(" + lex.getString() + ") ");
	    else if (t == CharConstant || t == IntConstant)
		System.out.print("(" + lex.getLong() + ") ");
	    else if (t == DoubleConstant)
		System.out.print("(" + lex.getDouble() + ") ");
	    else
		System.out.print(" ");
	}

	System.out.println(" ");
    }
}
