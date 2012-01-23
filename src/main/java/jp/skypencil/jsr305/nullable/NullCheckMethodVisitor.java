package jp.skypencil.jsr305.nullable;

import javax.annotation.Nonnegative;

import jp.skypencil.jsr305.Setting;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.google.common.annotations.VisibleForTesting;

public class NullCheckMethodVisitor extends MethodVisitor {

	private final NullCheckStrategyFactory factory;
	private final boolean isStaticMethod;
	private final Type[] argumentTypes;
	private Class<? extends Throwable> exception;

	public NullCheckMethodVisitor(int api, MethodVisitor inner, boolean isStatic, Type[] argumentTypes, Setting setting) {
		super(api, inner);
		this.factory = setting.getNullCheckLevel().createFactory(argumentTypes.length);
		this.isStaticMethod = isStatic;
		this.argumentTypes = argumentTypes;
		this.exception = setting.getExceptionForNullCheck();
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
			visitLdcInsn("you cannot give a null value to " + createOrdinal(index) + " parameter (" + argumentTypes[index].getClassName() + "), because " + factory.getReason());
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

	@VisibleForTesting
	String createOrdinal(@Nonnegative int index) {
		assert index >= 0;
		switch (index) {
			case 1: return "1st";
			case 2: return "2nd";
			case 3: return "3rd";
			default: return index + "th";
		}
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(Math.max(3, maxStack), maxLocals);
	}
}
