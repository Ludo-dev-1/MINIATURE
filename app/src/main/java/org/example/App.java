package org.example;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class App {
    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        File webContentFolder = new File("src/main/webapp");

        Context ctx = tomcat.addWebapp("", webContentFolder.getAbsolutePath());
        ctx.setReloadable(true);

        File classesFolder = new File("build/classes/java/main");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(
                resources,
                "/WEB-INF/classes",
                classesFolder.getAbsolutePath(),
                "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
        
    }
}