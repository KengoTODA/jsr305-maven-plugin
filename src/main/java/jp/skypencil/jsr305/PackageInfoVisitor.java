package jp.skypencil.jsr305;

import javax.annotation.ParametersAreNonnullByDefault;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

@ParametersAreNonnullByDefault
final class PackageInfoVisitor extends ClassVisitor {
	private static final String TARGET_DESC = Type.getDescriptor(ParametersAreNonnullByDefault.class);
	private boolean parameterIsNonnullByDefault;

	PackageInfoVisitor(int api, ClassWriter writer) {
		super(api, writer);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (desc.equals(TARGET_DESC)) {
			this.parameterIsNonnullByDefault = true;
		}
		return super.visitAnnotation(desc, visible);
	}

	PackageInfo getInfo() {
		return new PackageInfo(parameterIsNonnullByDefault);
	}
}
