package jp.skypencil.jsr305.nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;

import jp.skypencil.jsr305.OrdinalBuilder;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class NullCheckMethodVisitor extends MethodVisitor {
	private static final String DESC_PRMS_NONNULL_BY_DEFAULT = Type.getDescriptor(ParametersAreNonnullByDefault.class);
	private static final String DESC_PRMS_NULLABLE_BY_DEFAULT = Type.getDescriptor(ParametersAreNullableByDefault.class);
	private final NullCheckStrategyFactory factory;
	private final boolean isStaticMethod;
	private final Type[] argumentTypes;
	private final Class<? extends Throwable> exception;
	private final NullCheckLevel level;

	public NullCheckMethodVisitor(int api, MethodVisitor inner, boolean isStatic, Type[] argumentTypes, Setting setting, boolean nonnullByDefault, boolean nullableByDefault) {
		super(api, inner);
		this.level = setting.getLevel();
		this.isStaticMethod = isStatic;
		this.argumentTypes = argumentTypes;
		this.exception = setting.getException();

		this.factory = level.createFactory(argumentTypes.length);
		if (nonnullByDefault) {
			this.factory.markAsNonnullByDefault();
		} else if (nullableByDefault) {
			this.factory.markAsNullableByDefault();
		}
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (desc.equals(DESC_PRMS_NONNULL_BY_DEFAULT)) {
			this.factory.markAsNonnullByDefault();
		} else if (desc.equals(DESC_PRMS_NULLABLE_BY_DEFAULT)) {
			this.factory.markAsNullableByDefault();
		}
		return super.visitAnnotation(desc, visible);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {
		factory.add(parameter, desc);
		return super.visitParameterAnnotation(parameter, desc, visible);
	}

	@Override
	public void visitCode() {
		super.visitCode();
		NullCheckStrategy strategy = factory.build();
		for (int index : strategy.getParamIndexForNullCheck()) {
			if (!isReference(argumentTypes[index])) {
				// null check works for reference type only.
				continue;
			}

			final int localVarIndex = isStaticMethod ? index : (index + 1);
			Label afterCheck = new Label();
			visitVarInsn(Opcodes.ALOAD, localVarIndex);
			visitJumpInsn(Opcodes.IFNONNULL, afterCheck);

			visitTypeInsn(Opcodes.NEW, Type.getInternalName(exception));
			visitInsn(Opcodes.DUP);
			visitLdcInsn("you cannot give a null value to " + OrdinalBuilder.valueOf(index) + " parameter (" + argumentTypes[index].getClassName() + "), because " + factory.getReason());
			visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(exception), "<init>", "(Ljava/lang/String;)V");

			visitInsn(Opcodes.ATHROW);
			visitLabel(afterCheck);
		}
	}

	private boolean isReference(Type type) {
		boolean isPrimitive =
				type.equals(Type.INT_TYPE) || type.equals(Type.LONG_TYPE) || type.equals(Type.FLOAT_TYPE) || type.equals(Type.DOUBLE_TYPE) ||
				type.equals(Type.BOOLEAN_TYPE) || type.equals(Type.BYTE_TYPE) || type.equals(Type.SHORT_TYPE) || type.equals(Type.CHAR_TYPE);
		return !isPrimitive;
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(Math.max(3, maxStack), maxLocals);
	}
}
