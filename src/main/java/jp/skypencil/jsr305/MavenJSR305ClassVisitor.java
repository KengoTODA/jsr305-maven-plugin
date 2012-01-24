package jp.skypencil.jsr305;

import javax.annotation.Nullable;

import jp.skypencil.jsr305.negative.NegativeCheckMethodVisitor;
import jp.skypencil.jsr305.nullable.NullCheckMethodVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MavenJSR305ClassVisitor extends ClassVisitor {
	private static final String DESCRIPTION_OF_ENUM = Type.getInternalName(Enum.class);
	private static final String NAME_OF_CONSTRCTOR = "<init>";
	private final jp.skypencil.jsr305.nullable.Setting nullCheckSetting;
	private final jp.skypencil.jsr305.negative.Setting nonnegativeCheckSetting;
	private boolean isEnum;

	public MavenJSR305ClassVisitor(int api, ClassVisitor inner,
			@Nullable jp.skypencil.jsr305.nullable.Setting nullCheckSetting,
			@Nullable jp.skypencil.jsr305.negative.Setting nonnegativeCheckSetting) {
		super(api, inner);
		this.nullCheckSetting = nullCheckSetting;
		this.nonnegativeCheckSetting = nonnegativeCheckSetting;
	}

	@Override
	public void visit(int version, int access, String name, @Nullable String signature,
			@Nullable String superName, @Nullable String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		isEnum = DESCRIPTION_OF_ENUM.equals(superName);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			@Nullable String signature, @Nullable String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
		Scope methodScope = scopeOf(access);
		Type[] argumentTypes = Type.getArgumentTypes(desc);
		boolean isStaticMethod = (access & Opcodes.ACC_STATIC) != 0;

		if (isEnum && NAME_OF_CONSTRCTOR.equals(name)) {
			// don't inject to constructors of Enum, because it gets null always
		} else if (nullCheckSetting != null && nullCheckSetting.getTargetScope().contains(methodScope)) {
			mv = new NullCheckMethodVisitor(api, mv, isStaticMethod, argumentTypes, nullCheckSetting);
		}
		if (nonnegativeCheckSetting != null && nonnegativeCheckSetting.getTargetScope().contains(methodScope)) {
			mv = new NegativeCheckMethodVisitor(api, mv, isStaticMethod, argumentTypes, nonnegativeCheckSetting);
		}

		return mv;
	}

	private Scope scopeOf(int access) {
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			return Scope.PUBLIC;
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			return Scope.PRIVATE;
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			return Scope.PROTECTED;
		} else {
			return Scope.DEFAULT;
		}
	}
}
