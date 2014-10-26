package com.cisdiinfo.exercise;

import java.lang.reflect.Method;

public class TestInvoke {

	public static void invoke(Class< ? > clz, String methodName, Class< ? >[] paramTypes, Object[] params) {
		try {
			Method method = clz.getDeclaredMethod(methodName, paramTypes);
			method.setAccessible(true);

			method.invoke(clz.newInstance(), params);

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Pet pet = new Pet();
		pet.setName("Tom");
		Class< ? >[] paramTypes = { Food.class };
		Object[] params = { new Food("Apple") };
		invoke(pet.getClass(), "eat", paramTypes, params);
	}
}
