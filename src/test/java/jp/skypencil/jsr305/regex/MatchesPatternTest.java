package jp.skypencil.jsr305.regex;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import jp.skypencil.jsr305.MavenJSR305ClassVisitor;
import jp.skypencil.jsr305.Scope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.google.common.io.Resources;

@RunWith(Parameterized.class)
public class MatchesPatternTest {
	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{Scope.PRIVATE, Scope.PRIVATE, true},
				{Scope.PRIVATE, Scope.DEFAULT, true},
				{Scope.PRIVATE, Scope.PROTECTED, true},
				{Scope.PRIVATE, Scope.PUBLIC, true},
				{Scope.DEFAULT, Scope.PRIVATE, false},
				{Scope.DEFAULT, Scope.DEFAULT, true},
				{Scope.DEFAULT, Scope.PROTECTED, true},
				{Scope.DEFAULT, Scope.PUBLIC, true},
				{Scope.PROTECTED, Scope.PRIVATE, false},
				{Scope.PROTECTED, Scope.DEFAULT, false},
				{Scope.PROTECTED, Scope.PROTECTED, true},
				{Scope.PROTECTED, Scope.PUBLIC, true},
				{Scope.PUBLIC, Scope.PRIVATE, false},
				{Scope.PUBLIC, Scope.DEFAULT, false},
				{Scope.PUBLIC, Scope.PROTECTED, false},
				{Scope.PUBLIC, Scope.PUBLIC, true},
				{Scope.NONE, Scope.PRIVATE, false},
				{Scope.NONE, Scope.DEFAULT, false},
				{Scope.NONE, Scope.PROTECTED, false},
				{Scope.NONE, Scope.PUBLIC, false},
		});
	}

	private final Scope settingScope;
	private final Scope targetScope;
	private final boolean expected;

	public MatchesPatternTest(Scope settingScope, Scope targetScope, boolean expected) {
		this.settingScope = settingScope;
		this.targetScope = targetScope;
		this.expected = expected;
	}

	@Test
	public void testForStaticMethod() throws Throwable {
		test("jp/skypencil/jsr305/regex/EmptyStaticClass");
	}

	@Test
	public void testForNonStaticMethod() throws Throwable {
		test("jp/skypencil/jsr305/regex/EmptyClass");
	}

	private void test(String innerClassName) throws Throwable {
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(this.settingScope);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod(createMethodName("Normal"), String.class);
		assertThat(isChecked(instance, method, ""), is(false));
		method = clazz.getDeclaredMethod(createMethodName("Num"), String.class);
		assertThat(isChecked(instance, method, "abc"), is(expected));
		assertThat(isChecked(instance, method, "123"), is(false));
		method = clazz.getDeclaredMethod(createMethodName("Alphabet"), String.class);
		assertThat(isChecked(instance, method, "abc"), is(false));
		assertThat(isChecked(instance, method, "123"), is(expected));
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

	private String createMethodName(String type) {
		StringBuilder builder = new StringBuilder();
		return builder.append(this.targetScope.toString().toLowerCase()).append(type).append("Method").toString();
	}

	private static class OwnClassLoader extends ClassLoader {
		public Class<?> defineClass(String name, byte[] b) {
			return defineClass(name, b, 0, b.length);
		}
	}
}
