/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nitin.springframework;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**s
 *
 * @author nitinv
 */
public class LoadBeans {

    public static void loadA() {

        final GenericApplicationContext context = new GenericApplicationContext();
        final XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("loadBean.xml"));

        
        System.out.println("A.....  " + context.getBean("A") + " " + context.getBean("A").getClass());
        System.out.println("a.....  " + context.getBean("a") + " " + context.getBean("a").getClass());

    }
}
