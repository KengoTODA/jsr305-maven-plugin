package jp.skypencil.jsr305.nullable;

import jp.skypencil.jsr305.Scope;

import com.google.common.annotations.VisibleForTesting;

public final class Setting {
	private Scope targetScope = Scope.PUBLIC;
	private NullCheckLevel level = NullCheckLevel.PERMISSIVE;
	private String exception = IllegalArgumentException.class.getName();

	public Setting() {}

	@VisibleForTesting
	Setting(Scope targetScope, NullCheckLevel level,
			Class<? extends Throwable> exception) {
		this.targetScope = targetScope;
		this.level = level;
		this.exception = exception.getName();
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Throwable> getException() {
		try {
			return (Class<? extends Throwable>) Class.forName(exception);
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e);
		}
	}

	public Scope getTargetScope() {
		return targetScope;
	}

	public NullCheckLevel getLevel() {
		return level;
	}
}
