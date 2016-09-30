package com.cdvcloud.rms.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestAnn {

	public static void main(String[] args) {
		 Test test2=new Test();
		Class<? extends Test>clz=test2.getClass();
		Annotation [] annotions=clz.getAnnotations();
		for (Annotation annotation : annotions) {
			MyAnnotation myannotation=(MyAnnotation) annotation;
			System.out.println(myannotation.descib());
		}
		try {
			Method m=test2.getClass().getMethod("addTest",String.class);
			annotions=m.getAnnotations();
			for (Annotation annotation : annotions) {
				MyAnnotation myannotation=(MyAnnotation) annotation;
				System.out.println(myannotation.descib());
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
	}

}
