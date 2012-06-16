/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nitin.threadlocal;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author nitinv
 */
public class StringStatic {
    private static final GenericApplicationContext context = new GenericApplicationContext();
    static {
        final XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        xmlBeanDefinitionReader.loadBeanDefinitions(new Resource[] {new ClassPathResource("threadlocal.xml")});
        context.refresh();
    }
    private StringStatic() {}

    public static GenericApplicationContext getContext() {
        return context;
    }

    public static Object getBean(final String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(final Class<T> beanInterface) {
        return beanInterface.cast( context.getBean(beanInterface.getName()) );
    }

}
