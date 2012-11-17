package pl.edu.pjwstk.jps.ast.datastore.util;

public final class ClassNameUtils {
	private ClassNameUtils() {}
	
	public static String getName(Class<?> clazz) {
		String name = clazz.getSimpleName();
		return Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}
	
}
