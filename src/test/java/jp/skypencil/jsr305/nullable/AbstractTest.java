package jp.skypencil.jsr305.nullable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jp.skypencil.jsr305.MavenJSR305PackageVisitor;
import jp.skypencil.jsr305.Scope;

import org.apache.maven.plugin.logging.SystemStreamLog;

import com.google.common.io.Files;

abstract class AbstractTest {
	private final NullCheckLevel level;
	private final Scope settingScope;
	private final Scope targetScope;
	private final boolean nonnullIsTarget;
	private final boolean defaultIsTarget;
	private final File copiedFiles = Files.createTempDir();

	AbstractTest(NullCheckLevel level, Scope settingScope, Scope targetScope, boolean nonnullIsTarget, boolean defaultIsTarget) throws IOException {
		this.level = level;
		this.settingScope = settingScope;
		this.targetScope = targetScope;
		this.nonnullIsTarget = nonnullIsTarget;
		this.defaultIsTarget = defaultIsTarget;
		recursiveCopy(new File("target/test-classes"), copiedFiles);
	}

	private void recursiveCopy(File source, File targetDirectory) throws IOException {
		for (File child : source.listFiles()) {
			File target = new File(targetDirectory, child.getName());
			if (child.isDirectory()) {
				target.mkdir();
				recursiveCopy(child, target);
			} else {
				Files.copy(child, target);
			}
		}
	}

	protected void test(String innerClassName, Class<? extends Throwable> exception) throws Throwable {
		Setting setting = new Setting(this.settingScope, this.level, exception);
		new MavenJSR305PackageVisitor(setting, null, null).visitPackage(copiedFiles, new SystemStreamLog());

		File injectedClassFile = new File(copiedFiles, innerClassName + ".class");
		byte[] classBinary = Files.toByteArray(injectedClassFile);
		Class<?> clazz = new OwnClassLoader().defineClass(innerClassName.replaceAll("/", "."), classBinary );
		Object instance = clazz.newInstance();

		Method method = clazz.getDeclaredMethod(createMethodNameWith(Nonnull.class), Object.class);
		assertThat(isInjected(instance, method, exception), is(nonnullIsTarget));
		method = clazz.getDeclaredMethod(createMethodNameWith(Nullable.class), Object.class);
		assertThat(isInjected(instance, method, exception), is(false));
		method = clazz.getDeclaredMethod(createMethodNameWith(null), Object.class);
		assertThat(isInjected(instance, method, exception), is(defaultIsTarget));
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
