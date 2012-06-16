package edu.nitin.webservices.data;

/**
 * Created by IntelliJ IDEA.
 * User: nitinv
 * Date: 3/15/11
 * Time: 8:57 AM
 * To change this template use File | Settings | File Templates.
 */

public class AClass implements AInterface {

    public AClass2 getValue() {
        return new AClass2();
    }

    public AClass getSelf(final boolean neW) {
        return new AClass();
    }

    public void setValue(final AClass2 aClass2) {
    }
}
