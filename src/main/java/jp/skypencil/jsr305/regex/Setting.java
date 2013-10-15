package jp.skypencil.jsr305.regex;

import javax.annotation.Nonnull;

import jp.skypencil.jsr305.Scope;

import com.google.common.annotations.VisibleForTesting;

public class Setting {
	private Scope targetScope = Scope.PUBLIC;
	private String exception = IllegalArgumentException.class.getName();

	public Setting() {
	}

	@VisibleForTesting
	Setting(@Nonnull Scope targetScope) {
		this.targetScope = targetScope;
	}

	public Scope getTargetScope() {
		return targetScope;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Throwable> getException() {
		try {
			return (Class<? extends Throwable>) Class.forName(exception);
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e);
		}
	}
}
