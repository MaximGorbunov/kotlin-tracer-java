package io.inst.javassist.compiler;

import io.inst.javassist.ClassPool;
import io.inst.javassist.CtClass;
import io.inst.javassist.CtConstructor;
import io.inst.javassist.CtMember;
import io.inst.javassist.CtMethod;
import io.inst.javassist.bytecode.Bytecode;
import io.inst.javassist.compiler.Javac;
import io.inst.javassist.compiler.TokenId;
import java.io.*;

public class CodeTest implements TokenId {
    public static void main(String[] args) throws Exception {
	ClassPool loader = ClassPool.getDefault();

	CtClass c = loader.get(args[0]);

	String line
	    = new BufferedReader(new InputStreamReader(System.in)).readLine();
	Bytecode b = new Bytecode(c.getClassFile().getConstPool(), 0, 0);

	Javac jc = new Javac(b, c);
	CtMember obj = jc.compile(line);
	if (obj instanceof CtMethod)
	    c.addMethod((CtMethod)obj);
	else
	    c.addConstructor((CtConstructor)obj);

	c.writeFile();
    }
}
