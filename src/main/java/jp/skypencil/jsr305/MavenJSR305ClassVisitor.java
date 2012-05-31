package jp.skypencil.jsr305;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;

import jp.skypencil.jsr305.negative.NegativeCheckMethodVisitor;
import jp.skypencil.jsr305.nullable.NullCheckMethodVisitor;
import jp.skypencil.jsr305.regex.MatchesPatternMethodVisitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MavenJSR305ClassVisitor extends ClassVisitor {
	private static final String DESCRIPTION_OF_ENUM = Type.getInternalName(Enum.class);
	private static final String NAME_OF_CONSTRCTOR = "<init>";
	private final jp.skypencil.jsr305.nullable.Setting nullCheckSetting;
	private final jp.skypencil.jsr305.negative.Setting nonnegativeCheckSetting;
	private final jp.skypencil.jsr305.regex.Setting regexSetting;
	private boolean isEnum;
	private boolean nonnullByDefault;
	private boolean nullableByDefault;

	public MavenJSR305ClassVisitor(int api, ClassVisitor inner,
			@Nullable jp.skypencil.jsr305.nullable.Setting nullCheckSetting,
			@Nullable jp.skypencil.jsr305.negative.Setting nonnegativeCheckSetting,
			@Nullable jp.skypencil.jsr305.regex.Setting regexSetting,
			@Nonnull PackageInfo info) {
		super(api, inner);
		this.nullCheckSetting = nullCheckSetting;
		this.nonnegativeCheckSetting = nonnegativeCheckSetting;
		this.regexSetting = regexSetting;
		this.nonnullByDefault = checkNotNull(info).isNonnullByDefault();
		this.nullableByDefault = info.isNullableByDefault();
	}

	@Override
	public void visit(int version, int access, String name, @Nullable String signature,
			@Nullable String superName, @Nullable String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		isEnum = DESCRIPTION_OF_ENUM.equals(superName);
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (desc.equals(Type.getDescriptor(ParametersAreNonnullByDefault.class))) {
			this.nonnullByDefault = true;
			this.nullableByDefault = false;
		} else if (desc.equals(Type.getDescriptor(ParametersAreNullableByDefault.class))) {
			this.nonnullByDefault = false;
			this.nullableByDefault = true;
		}
		return super.visitAnnotation(desc, visible);
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
			mv = new NullCheckMethodVisitor(api, mv, isStaticMethod, argumentTypes, nullCheckSetting, this.nonnullByDefault, this.nullableByDefault);
		}
		if (nonnegativeCheckSetting != null && nonnegativeCheckSetting.getTargetScope().contains(methodScope)) {
			mv = new NegativeCheckMethodVisitor(api, mv, isStaticMethod, argumentTypes, nonnegativeCheckSetting);
		}
		if (regexSetting != null && regexSetting.getTargetScope().contains(methodScope)) {
			mv = new MatchesPatternMethodVisitor(api, mv, isStaticMethod, argumentTypes, regexSetting);
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
