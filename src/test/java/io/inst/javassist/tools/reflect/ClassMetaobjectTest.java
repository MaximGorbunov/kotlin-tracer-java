package io.inst.javassist.tools.reflect;

import io.inst.javassist.tools.reflect.Loader;

/**
 * @author Brett Randall
 */
public class ClassMetaobjectTest {
    public static void main(String[] args) throws Throwable {
        Loader loader = new Loader();
        loader.makeReflective("javassist.tools.reflect.Person",
                              "javassist.tools.reflect.Metaobject",
                              "javassist.tools.reflect.ClassMetaobject");
        loader.run("javassist.tools.reflect.Person", new String[] {});
    }
}
