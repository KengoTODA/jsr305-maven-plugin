package jp.skypencil.jsr305;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.google.common.io.Files;

public class PackageInfoVisitorTest {

	@Test
	public void testWithAnnotation() throws IOException {
		assertThat(visit("jp.skypencil.jsr305.nullable.package_annotated_with_nonnull").isNonnullByDefault(), is(true));
	}

	@Test
	public void testWithoutAnnotation() throws IOException {
		assertThat(visit("jp.skypencil.jsr305.nullable.package_not_annotated").isNonnullByDefault(), is(false));
	}

	private PackageInfo visit(String packageName) throws IOException {
		File packageDirectory = new File("target/test-classes", packageName.replace('.', File.separatorChar));
		File packageInfo = new File(packageDirectory, "package-info.class");
		byte[] binary = Files.toByteArray(packageInfo);

		ClassReader reader = new ClassReader(binary);
		ClassWriter writer = new ClassWriter(0);
		PackageInfoVisitor visitor = new PackageInfoVisitor(Opcodes.V1_6, writer);
		reader.accept(visitor, 0);
		return visitor.getInfo();
	}
}
