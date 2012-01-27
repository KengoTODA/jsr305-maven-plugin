package jp.skypencil.jsr305.negative;


import javax.annotation.Nonnegative;

import jp.skypencil.jsr305.OrdinalBuilder;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public final class NegativeCheckMethodVisitor extends MethodVisitor {
	private final NegativeCheckStrategyFactory factory;
	private final boolean isStaticMethod;
	private final Type[] argumentTypes;
	private final Class<? extends Throwable> exception;

	public NegativeCheckMethodVisitor(int api, MethodVisitor inner, boolean isStatic, Type[] argumentTypes, Setting setting) {
		super(api, inner);
		this.factory = setting.getLevel().createFactory();
		this.isStaticMethod = isStatic;
		this.argumentTypes = argumentTypes;
		this.exception = setting.getException();
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
		NegativeCheckStrategy strategy = factory.build();
		for (int index : strategy.getParamIndexForNegativeCheck()) {
			final int localVarIndex = isStaticMethod ? index : (index + 1);

			switch (argumentTypes[index].getSort()) {
				case Type.BYTE:
				case Type.SHORT:
				case Type.CHAR:
				case Type.INT:
					injectCheckLogics(index, localVarIndex, Opcodes.ILOAD);
					break;
				case Type.LONG:
					injectCheckLogics(index, localVarIndex, Opcodes.LLOAD);
					break;
				case Type.FLOAT:
					injectCheckLogics(index, localVarIndex, Opcodes.FLOAD);
					break;
				case Type.DOUBLE:
					injectCheckLogics(index, localVarIndex, Opcodes.DLOAD);
					break;
				default:
					injectCheckLogics(index, localVarIndex, Opcodes.ALOAD);
					break;
			}
		}
	}

	private void injectCheckLogics(@Nonnegative final int  index, @Nonnegative final int localVarIndex, @Nonnegative final int opcodeToLoad) {
		Label afterCheck = new Label();
		if (opcodeToLoad == Opcodes.ALOAD) {
			visitVarInsn(Opcodes.ALOAD, localVarIndex);
			visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(Number.class));
			visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(Number.class), "floatValue", Type.getMethodDescriptor(Type.FLOAT_TYPE));
		} else {
			visitVarInsn(opcodeToLoad, localVarIndex);
		}
		switch (opcodeToLoad) {
			case Opcodes.LLOAD:
				visitInsn(Opcodes.LCONST_0);
				visitInsn(Opcodes.LCMP);
				break;
			case Opcodes.DLOAD:
				visitInsn(Opcodes.DCONST_0);
				visitInsn(Opcodes.DCMPL);
				break;
			case Opcodes.ALOAD:
			case Opcodes.FLOAD:
				visitInsn(Opcodes.FCONST_0);
				visitInsn(Opcodes.FCMPL);
				break;
		}
		visitJumpInsn(Opcodes.IFGE, afterCheck);

		visitTypeInsn(Opcodes.NEW, Type.getInternalName(exception));
		visitInsn(Opcodes.DUP);
		visitLdcInsn("you cannot give a null value to " + OrdinalBuilder.valueOf(index) + " parameter (" + argumentTypes[index].getClassName() + "), because " + factory.getReason());
		visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(exception), "<init>", "(Ljava/lang/String;)V");

		visitInsn(Opcodes.ATHROW);
		visitLabel(afterCheck);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		super.visitMaxs(Math.max(4, maxStack), maxLocals);
	}
}
