package com.sergey.prykhodko;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;

public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException {
        Tomcat tomcat = getTomcat();


        tomcat.start();
        tomcat.getServer().await();

    }

    private static Tomcat getTomcat() throws ServletException {
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()){
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/",
                new File(webappDirLocation).getAbsolutePath());

        WebResourceRoot resources = new StandardRoot(ctx);

        File additionWebInfClasses = new File("target/classes");

        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));

        ctx.setResources(resources);
        return tomcat;
    }
}
