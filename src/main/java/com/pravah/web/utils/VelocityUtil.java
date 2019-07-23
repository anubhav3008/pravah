package com.pravah.web.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

@Service
public class VelocityUtil {

    TimeUtil timeUtil= new TimeUtil();
    public String transform(ObjectNode data, String file, Map<String, String> images){
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        Template t = velocityEngine.getTemplate(file);
        VelocityContext context = new VelocityContext();
        context.put("data",data);
        context.put("timeUtil",timeUtil );
        for(Map.Entry<String,String> image : images.entrySet()){
            context.put(image.getKey(),image.getValue());
        }
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        return writer.toString();
    }
}
