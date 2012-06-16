/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.threadlocal;

/**
 *
 * @author nitinv
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                {
                    final AppThreadContext context = StringStatic.getBean(AppThreadContext.class);
                    System.out.println(context.getObject("ABC"));
                    context.setObject("ABC", "abc");
                }

                {
                    final AppThreadContext context = StringStatic.getBean(AppThreadContext.class);
                    System.out.println(context.getObject("ABC"));
                }
            } finally {
                final AppThreadContext context = StringStatic.getBean(AppThreadContext.class);
                context.clear();
            }
        }

    }
}
