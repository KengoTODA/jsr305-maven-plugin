package jp.skypencil.jsr305.negative;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.skypencil.jsr305.MavenJSR305ClassVisitor;
import jp.skypencil.jsr305.Scope;
import junit.framework.Assert;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.google.common.io.Resources;

public class CornerCaseTest {
	@Test
	public void testMethodWithNonNumberArgument() throws Throwable {
		test("jp/skypencil/jsr305/negative/EmptyClass");
	}

	@Test
	public void testStaticMethodWithNonNumberArgument() throws Throwable {
		test("jp/skypencil/jsr305/negative/EmptyStaticClass");
	}

	@Test
	public void testMethodWithNan() throws Throwable {
		testWithNan("jp/skypencil/jsr305/negative/EmptyClass");
	}

	@Test
	public void testStaticMethodWithNan() throws Throwable {
		testWithNan("jp/skypencil/jsr305/negative/EmptyStaticClass");
	}

	@Test
	public void testMethodWithInfinity() throws Throwable {
		testWithInfinity("jp/skypencil/jsr305/negative/EmptyClass");
	}

	@Test
	public void testStaticMethodWithInfinity() throws Throwable {
		testWithInfinity("jp/skypencil/jsr305/negative/EmptyStaticClass");
	}

	@Test
	public void testMethodWithNegativeInfinity() throws Throwable {
		testWithNegativeInfinity("jp/skypencil/jsr305/negative/EmptyClass");
	}

	@Test
	public void testStaticMethodWithNegativeInfinity() throws Throwable {
		testWithNegativeInfinity("jp/skypencil/jsr305/negative/EmptyStaticClass");
	}

	@Test
	public void testAbstractMethod() throws Throwable {
		String innerClassName = "jp/skypencil/jsr305/negative/AbstractClass";
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(Scope.PRIVATE, NegativeCheckLevel.PERMISSIVE, IllegalArgumentException.class);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
	}

	@Test
	public void testInterface() throws Throwable {
		String innerClassName = "jp/skypencil/jsr305/negative/Interface";
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(Scope.PRIVATE, NegativeCheckLevel.PERMISSIVE, IllegalArgumentException.class);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
	}

	private void test(String innerClassName) throws Throwable {
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(Scope.PUBLIC, NegativeCheckLevel.PERMISSIVE, IllegalArgumentException.class);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod("publicMethodWithNonNumber", Object.class);
		try {
			method.invoke(instance, new Object());
			Assert.fail();
		} catch (InvocationTargetException expected) {
			assertThat(expected.getCause(), is(ClassCastException.class));
		}
	}

	private void testWithNan(String innerClassName) throws Throwable {
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(Scope.PUBLIC, NegativeCheckLevel.PERMISSIVE, IllegalArgumentException.class);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod("publicNonnegativeDoubleMethod", double.class);
		try {
			method.invoke(instance, Double.NaN);
			Assert.fail();
		} catch (InvocationTargetException expected) {
			assertThat(expected.getCause(), is(IllegalArgumentException.class));
		}
	}

	private void testWithInfinity(String innerClassName) throws Throwable {
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(Scope.PUBLIC, NegativeCheckLevel.PERMISSIVE, IllegalArgumentException.class);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod("publicNonnegativeDoubleMethod", double.class);
		method.invoke(instance, Double.POSITIVE_INFINITY);
	}

	private void testWithNegativeInfinity(String innerClassName) throws Throwable {
		ClassReader reader = new ClassReader(Resources.toByteArray(Resources.getResource(innerClassName + ".class")));
		ClassWriter writer = new ClassWriter(0);
		Setting setting = new Setting(Scope.PUBLIC, NegativeCheckLevel.PERMISSIVE, IllegalArgumentException.class);
		reader.accept(new MavenJSR305ClassVisitor(Opcodes.V1_6, writer, null, setting), 0);
		byte[] classBinary = writer.toByteArray();

		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary);
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod("publicNonnegativeDoubleMethod", double.class);
		try {
			method.invoke(instance, Double.NEGATIVE_INFINITY);
			Assert.fail();
		} catch (InvocationTargetException expected) {
			assertThat(expected.getCause(), is(IllegalArgumentException.class));
		}
	}

	private static class OwnClassLoader extends ClassLoader {
		public Class<?> defineClass(String name, byte[] b) {
			return defineClass(name, b, 0, b.length);
		}
	}
}
