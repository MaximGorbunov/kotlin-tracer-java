package io.inst.javassist.compiler;

import io.inst.javassist.compiler.CompileError;
import io.inst.javassist.compiler.Lex;
import io.inst.javassist.compiler.Parser;
import io.inst.javassist.compiler.SymbolTable;
import io.inst.javassist.compiler.TokenId;
import io.inst.javassist.compiler.ast.Stmnt;

public class ParseTest implements TokenId {
    public static void main(String[] args) throws CompileError {
	Parser p = new Parser(new Lex(args[0]));
	SymbolTable stb = new SymbolTable();
	// MethodDecl s = (MethodDecl)p.parseMember(stb);
	Stmnt s = p.parseStatement(stb);
	System.out.println(s == null ? "null" : s.toString());
    }
}
