package jp.skypencil.jsr305;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.maven.plugin.logging.Log;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.google.common.io.Files;

@ParametersAreNonnullByDefault
final class MavenJSR305PackageVisitor {
	private final jp.skypencil.jsr305.nullable.Setting nullCheck;
	private final jp.skypencil.jsr305.negative.Setting negativeCheck;
	private final jp.skypencil.jsr305.regex.Setting regexCheck;

	MavenJSR305PackageVisitor(
			jp.skypencil.jsr305.nullable.Setting nullCheck,
			jp.skypencil.jsr305.negative.Setting negativeCheck,
			jp.skypencil.jsr305.regex.Setting regexCheck) {
		this.nullCheck = nullCheck;
		this.negativeCheck = negativeCheck;
		this.regexCheck = regexCheck;
	}

	void visitPackage(final File directory, final Log log) throws IOException {
		checkNotNull(directory);
		checkNotNull(log);
		checkArgument(directory.isDirectory());

		final PackageInfo info = loadInfo(directory, log);
		for (File child : directory.listFiles()) {
			if (child.isDirectory()) {
				visitPackage(child, log);
			} else if (Files.getFileExtension(child.getName()).equals("class")) {
				visitClass(child, log, info);
			}
		}
	}

	private PackageInfo loadInfo(File directory, Log log) throws IOException {
		File packageInfo = new File(directory, "package-info.class");
		if (!packageInfo.exists() || !packageInfo.isFile()) {
			return new PackageInfo(false);
		}

		byte[] binary = Files.toByteArray(packageInfo);
		int majorVersionOfClassFileFormat = binary[6] << 8 | binary[7];
		int api = apiFor(majorVersionOfClassFileFormat);

		ClassReader reader = new ClassReader(binary);
		ClassWriter writer = new ClassWriter(0);
		PackageInfoVisitor visitor = new PackageInfoVisitor(api, writer);
		reader.accept(visitor, 0);
		return visitor.getInfo();
	}

	private void visitClass(File classFile, Log log, PackageInfo info) throws IOException {
		log.debug("inject to " + classFile.getAbsolutePath());
		byte[] binary = Files.toByteArray(classFile);
		int majorVersionOfClassFileFormat = binary[6] << 8 | binary[7];
		int api = apiFor(majorVersionOfClassFileFormat);

		ClassReader reader = new ClassReader(binary);
		ClassWriter writer = new ClassWriter(0);
		reader.accept(new MavenJSR305ClassVisitor(api, writer, nullCheck, negativeCheck, regexCheck), 0);
		byte[] enhanced = writer.toByteArray();
		Files.write(enhanced, classFile);
	}

	private int apiFor(int majorVersionOfClassFileFormat) {
		switch (majorVersionOfClassFileFormat) {
			case 51: return Opcodes.V1_7;
			case 50: return Opcodes.V1_6;
			case 49: return Opcodes.V1_5;
		}
		throw new RuntimeException("unknown major version: " + majorVersionOfClassFileFormat);
	}
}
