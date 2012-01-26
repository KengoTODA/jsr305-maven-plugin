package jp.skypencil.jsr305.regex;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import javax.annotation.MatchesPattern;

import jp.skypencil.jsr305.MavenJSR305ClassVisitor;
import jp.skypencil.jsr305.Scope;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.google.common.io.Resources;

public class FlagTest {
	public void normal(@MatchesPattern(value = "..") String argument) {}
	public void multiline(@MatchesPattern(value = "..", flags=Pattern.DOTALL) String argument) {}

	@Test
	public void test() throws Throwable {
		String innerClassName = "jp/skypencil/jsr305/regex/FlagTest";
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(Scope.PRIVATE);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod("normal", String.class);
		assertThat(isChecked(instance, method, ""), is(true));
		assertThat(isChecked(instance, method, "ab"), is(false));
		assertThat(isChecked(instance, method, "\r\n"), is(true));
		method = clazz.getDeclaredMethod("multiline", String.class);
		assertThat(isChecked(instance, method, ""), is(true));
		assertThat(isChecked(instance, method, "ab"), is(false));
		assertThat(isChecked(instance, method, "\r\n"), is(false));
	}

	private boolean isChecked(Object instance, Method method, String argument) throws IllegalArgumentException, IllegalAccessException {
		method.setAccessible(true);
		try {
			method.invoke(instance, new Object[] { argument });
			return false;
		} catch (InvocationTargetException expected) {
			assertThat(expected.getTargetException(), is(IllegalArgumentException.class));
			return true;
		}
	}
	private static class OwnClassLoader extends ClassLoader {
		public Class<?> defineClass(String name, byte[] b) {
			return defineClass(name, b, 0, b.length);
		}
	}
}
