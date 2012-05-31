package jp.skypencil.jsr305;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

@ParametersAreNonnullByDefault
public final class PackageInfoVisitor extends ClassVisitor {
	private static final String NONNULL_DESC = Type.getDescriptor(ParametersAreNonnullByDefault.class);
	private static final String NULLABLE_DESC = Type.getDescriptor(ParametersAreNullableByDefault.class);
	private boolean parameterIsNonnullByDefault;
	private boolean parameterIsNullableByDefault;

	public PackageInfoVisitor(int api, ClassWriter writer) {
		super(api, writer);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (desc.equals(NONNULL_DESC)) {
			this.parameterIsNonnullByDefault = true;
		} else if (desc.equals(NULLABLE_DESC)) {
			this.parameterIsNullableByDefault = true;
		}
		return super.visitAnnotation(desc, visible);
	}

	public PackageInfo getInfo() {
		return new PackageInfo(parameterIsNonnullByDefault, parameterIsNullableByDefault);
	}
}
