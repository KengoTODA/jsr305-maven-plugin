package jp.skypencil.jsr305;

import javax.annotation.concurrent.Immutable;

import jp.skypencil.jsr305.nullable.NullCheckLevel;

@Immutable
public final class Setting {
	private final Scope targetScopeForNullCheck;
	private final NullCheckLevel nullCheckLevel;
	private Class<? extends Throwable> exceptionForNullCheck;

	public Setting (Scope targetForNullCheck, NullCheckLevel level, Class<? extends Throwable> exceptionForNullCheck) {
		this.targetScopeForNullCheck = targetForNullCheck;
		this.nullCheckLevel = level;
		this.exceptionForNullCheck = exceptionForNullCheck;
	}

	public Class<? extends Throwable> getExceptionForNullCheck() {
		return exceptionForNullCheck;
	}

	public Scope getTargetScopeForNullCheck() {
		return targetScopeForNullCheck;
	}

	public NullCheckLevel getNullCheckLevel() {
		return nullCheckLevel;
	}
}
