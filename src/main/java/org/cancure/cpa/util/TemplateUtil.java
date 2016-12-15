package org.cancure.cpa.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtil {

	public static String process(String templateString, Map<String, Object> values) throws Exception {
		
		StringWriter output;
		try {
			Template template = new Template("temp", new StringReader(templateString),
			               new Configuration());
			
			output = new StringWriter();
			template.process(values, output);
			return output.toString();
			
		} catch (IOException | TemplateException e) {
			throw new Exception(e);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> values = new HashMap<>();
		values.put("user", "Dantis");
		
		String output = TemplateUtil.process("Hello ${user} test", values);
		System.out.println(output);
	}
	
}
