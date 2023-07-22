package test5;

import io.inst.javassist.ClassPool;
import io.inst.javassist.CtClass;
import io.inst.javassist.CtMethod;
import java.util.Random;

public class JIRA241 {
    public int run() {
        test(this);
        return 10;
    }

    public static void test(Object o) {
        //part 1
        if (o == null) {
            return;
        }

        //part 2
        int oper = new Random().nextInt();
        switch (oper) {
        case 1:
            break;
        }
    }

    public static void main(String[] args) throws Exception {
        ClassPool pool  = ClassPool.getDefault();
        CtClass cc = pool.get("test5.JIRA241");
        CtMethod testMethod = cc.getMethod("test", "(Ljava/lang/Object;)V");
        testMethod.insertAfter("System.out.println(\"inserted!\");");
        cc.writeFile();
    }
}
