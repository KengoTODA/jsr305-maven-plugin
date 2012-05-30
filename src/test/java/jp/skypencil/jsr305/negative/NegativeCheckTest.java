package jp.skypencil.jsr305.negative;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnegative;

import jp.skypencil.jsr305.MavenJSR305ClassVisitor;
import jp.skypencil.jsr305.PackageInfo;
import jp.skypencil.jsr305.Scope;
import jp.skypencil.jsr305.negative.NegativeCheckLevel;
import jp.skypencil.jsr305.negative.Setting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.google.common.io.Resources;

@RunWith(Parameterized.class)
public abstract class NegativeCheckTest {
	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{NegativeCheckLevel.PERMISSIVE, Scope.NONE, Scope.PUBLIC, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.NONE, Scope.PROTECTED, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.NONE, Scope.DEFAULT, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.NONE, Scope.PRIVATE, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.PUBLIC, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.PROTECTED, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.DEFAULT, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.PRIVATE, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.PUBLIC, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.PROTECTED, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.DEFAULT, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.PRIVATE, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.PUBLIC, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.PROTECTED, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.DEFAULT, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.PRIVATE, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.PUBLIC, true},
				{NegativeCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.PROTECTED, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.DEFAULT, false},
				{NegativeCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.PRIVATE, false},
		});
	}

	private final NegativeCheckLevel level;
	private final Scope settingScope;
	private final Scope targetScope;
	private final boolean expected;

	public NegativeCheckTest(NegativeCheckLevel level, Scope settingScope, Scope targetScope, boolean expected) {
		this.level = level;
		this.settingScope = settingScope;
		this.targetScope = targetScope;
		this.expected = expected;
	}

	@Test
	public void testForNonStaticMethod() throws Throwable {
		test("jp/skypencil/jsr305/negative/EmptyClass", IllegalArgumentException.class);
	}

	@Test
	public void testForStaticMethod() throws Throwable {
		test("jp/skypencil/jsr305/negative/EmptyStaticClass", IllegalArgumentException.class);
	}

	@Test
	public void testForNonStaticMethodWithRuntimeException() throws Throwable {
		test("jp/skypencil/jsr305/negative/EmptyClass", NullPointerException.class);
	}

	@Test
	public void testForStaticMethodWithRuntimeException() throws Throwable {
		test("jp/skypencil/jsr305/negative/EmptyStaticClass", NullPointerException.class);
	}

	private void test(String innerClassName, Class<? extends Throwable> exception) throws Throwable {
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(this.settingScope, this.level, exception);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, setting, null, new PackageInfo(false)), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod(createMethodNameWith(Nonnegative.class), getType());
		assertThat(isInjected(instance, method, exception), is(expected));
		method = clazz.getDeclaredMethod(createMethodNameWith(null), getType());
		assertThat(isInjected(instance, method, exception), is(false));
	}

	protected abstract boolean isInjected(Object instance, Method method, Class<? extends Throwable> exception) throws IllegalArgumentException, IllegalAccessException;
	protected abstract Class<?> getType();
	protected abstract String getTypeName();

	private String createMethodNameWith(Class<?> annotationClass) {
		StringBuilder builder = new StringBuilder();
		builder.append(this.targetScope.toString().toLowerCase());
		if (annotationClass == null) {
			builder.append("Normal");
		} else if (annotationClass.equals(Nonnegative.class)) {
			builder.append("Nonnegative");
		} else {
			throw new AssertionError();
		}
		builder.append(getTypeName());
		return builder.append("Method").toString();
	}

	private static class OwnClassLoader extends ClassLoader {
		public Class<?> defineClass(String name, byte[] b) {
			return defineClass(name, b, 0, b.length);
		}
	}
}
