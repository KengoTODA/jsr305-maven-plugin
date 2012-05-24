package jp.skypencil.jsr305.nullable;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
public class NonnullByDefaultTest {
	private final NullCheckLevel level;
	private final Scope settingScope = Scope.PUBLIC;
	private final Scope targetScope = Scope.PUBLIC;

	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{NullCheckLevel.STRICT},
				{NullCheckLevel.PERMISSIVE}
		});
	}

	public NonnullByDefaultTest(NullCheckLevel level) {
		this.level = level;
	}

	@Test
	public void testNotAnnotated() throws Throwable {
		test("jp/skypencil/jsr305/nullable/NonnullByDefaultClass", IllegalArgumentException.class);
	}

	private void test(String innerClassName, Class<? extends Throwable> exception) throws Throwable {
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(this.settingScope , this.level, exception);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, setting, null, null), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod(createMethodNameWith(Nonnull.class), Object.class);
		assertThat(isInjected(instance, method, exception), is(true));
		method = clazz.getDeclaredMethod(createMethodNameWith(Nullable.class), Object.class);
		assertThat(isInjected(instance, method, exception), is(false));
		method = clazz.getDeclaredMethod(createMethodNameWith(null), Object.class);
		assertThat(isInjected(instance, method, exception), is(true));
	}

	private boolean isInjected(Object instance, Method method, Class<? extends Throwable> exception) throws IllegalArgumentException, IllegalAccessException {
		method.setAccessible(true);
		try {
			method.invoke(instance, new Object[] { null });
			return false;
		} catch (InvocationTargetException expected) {
			assertThat(expected.getTargetException(), is(exception));
			return true;
		}
	}

	private String createMethodNameWith(Class<?> annotationClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(this.targetScope.toString().toLowerCase());
		if (annotationClass == null) {
			builder.append("Normal");
		} else if (annotationClass.equals(Nullable.class)) {
			builder.append("Nullable");
		} else if (annotationClass.equals(Nonnull.class)) {
			builder.append("Nonnull");
		} else {
			throw new AssertionError();
		}
		return builder.append("Method").toString();
	}

	private static class OwnClassLoader extends ClassLoader {
		public Class<?> defineClass(String name, byte[] b) {
			return defineClass(name, b, 0, b.length);
		}
	}
}
