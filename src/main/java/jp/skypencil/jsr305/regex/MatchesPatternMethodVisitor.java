package jp.skypencil.jsr305.regex;

import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.MatchesPattern;
import javax.annotation.Nonnegative;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

public class MatchesPatternMethodVisitor extends MethodVisitor {
	private static final String TARGET_DESCRIPTOR = Type.getDescriptor(MatchesPattern.class);
	private static final String INTERNAL_NAME_PATTERN = Type.getInternalName(Pattern.class);
	private static final Type TYPE_STRING = Type.getType(String.class);
	private static final String METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, TYPE_STRING, Type.getType(CharSequence.class));

	private final boolean isStatic;
	private final Type[] argumentTypes;
	private final Map<Integer, MatchesPatternAnnotationVisitor> map = Maps.newHashMap();
	private final String internalNameOfException;

	public MatchesPatternMethodVisitor(int api, MethodVisitor inner, boolean isStatic, Type[] argumentTypes, Setting setting) {
		super(api, inner);
		this.isStatic = isStatic;
		this.argumentTypes = argumentTypes;
		this.internalNameOfException = Type.getInternalName(setting.getException());
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {
		AnnotationVisitor annotationVisitor = super.visitParameterAnnotation(parameter, desc, visible);
		if (TARGET_DESCRIPTOR.equals(desc)) {
			MatchesPatternAnnotationVisitor originalVisitor = new MatchesPatternAnnotationVisitor(this.api, annotationVisitor);
			map.put(Integer.valueOf(parameter), originalVisitor);
			annotationVisitor = originalVisitor;
		}
		return annotationVisitor;
	}

	@Override
	public void visitCode() {
		super.visitCode();
		for (Map.Entry<Integer, MatchesPatternAnnotationVisitor> entry : map.entrySet()) {
			final int parameter = entry.getKey();
			if (!argumentTypes[parameter].equals(TYPE_STRING)) {
				// matches pattern works for String only.
				continue;
			}
			Pattern pattern = entry.getValue().getPattern();
			final int localVarIndex = isStatic ? parameter : (parameter + 1);

			Label afterCheck = new Label();
			visitLdcInsn(pattern.pattern());
			visitVarInsn(Opcodes.ALOAD, localVarIndex);
			visitMethodInsn(Opcodes.INVOKESTATIC, INTERNAL_NAME_PATTERN, "matches", METHOD_DESCRIPTOR);
			visitJumpInsn(Opcodes.IFNE, afterCheck);

			visitTypeInsn(Opcodes.NEW, internalNameOfException);
			visitInsn(Opcodes.DUP);
			visitLdcInsn("given String for " + createOrdinal(parameter) + " parameter doesn't match the pattern (" + pattern.pattern() + ")");
			visitMethodInsn(Opcodes.INVOKESPECIAL, internalNameOfException, "<init>", "(Ljava/lang/String;)V");

			visitInsn(Opcodes.ATHROW);
			visitLabel(afterCheck);
		}
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
