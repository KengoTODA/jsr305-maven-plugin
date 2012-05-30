package jp.skypencil.jsr305;

import javax.annotation.ParametersAreNonnullByDefault;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

@ParametersAreNonnullByDefault
final class PackageInfoVisitor extends ClassVisitor {
	private boolean parameterIsNonnullByDefault;

	PackageInfoVisitor(int api, ClassWriter writer) {
		super(api, writer);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		System.out.println(desc);
		return super.visitAnnotation(desc, visible);
	}

	boolean isNonnullByDefault() {
		return parameterIsNonnullByDefault;
	}

	PackageInfo getInfo() {
		return new PackageInfo();
	}
}
