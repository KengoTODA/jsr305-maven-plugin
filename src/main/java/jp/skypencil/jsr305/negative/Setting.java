package jp.skypencil.jsr305.negative;

import jp.skypencil.jsr305.Scope;

import com.google.common.annotations.VisibleForTesting;

public final class Setting {
	private Scope targetScope = Scope.PUBLIC;
	private NegativeCheckLevel level = NegativeCheckLevel.PERMISSIVE;
	private String exception = IllegalArgumentException.class.getName();

	public Setting() {}

	@VisibleForTesting
	Setting(Scope targetScope, NegativeCheckLevel level,
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

	public NegativeCheckLevel getLevel() {
		return level;
	}
}
