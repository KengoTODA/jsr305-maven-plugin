package jp.skypencil.jsr305;

import javax.annotation.Nullable;

import jp.skypencil.jsr305.nullable.NullCheckMethodVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MavenJSR305ClassVisitor extends ClassVisitor {
	private static final String DESCRIPTION_OF_ENUM = Type.getInternalName(Enum.class);
	private static final String NAME_OF_CONSTRCTOR = "<init>";
	private final Setting setting;
	private boolean isEnum;

	public MavenJSR305ClassVisitor(int api, ClassVisitor inner, Setting setting) {
		super(api, inner);
		this.setting = setting;
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

		if (isEnum && NAME_OF_CONSTRCTOR.equals(name)) {
			// don't inject to constructors of Enum, because it gets null always
			return mv;
		} else if (setting.getTargetScopeForNullCheck().contains(methodScope)) {
			Type[] argumentTypes = Type.getArgumentTypes(desc);
			boolean isStaticMethod = (access & Opcodes.ACC_STATIC) != 0;
			return new NullCheckMethodVisitor(api, mv, isStaticMethod, argumentTypes, setting);
		} else {
			return mv;
		}
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
