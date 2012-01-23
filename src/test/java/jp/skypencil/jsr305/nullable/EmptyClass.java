package jp.skypencil.jsr305.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class EmptyClass {
	public void publicNormalMethod(Object argument) {
	}
	public void publicNonnullMethod(@Nonnull Object argument) {
	}
	public void publicNullableMethod(@Nullable Object argument) {
	}
	void defaultNormalMethod(Object argument) {
	}
	void defaultNonnullMethod(@Nonnull Object argument) {
	}
	void defaultNullableMethod(@Nullable Object argument) {
	}
	protected void protectedNormalMethod(Object argument) {
	}
	protected void protectedNonnullMethod(@Nonnull Object argument) {
	}
	protected void protectedNullableMethod(@Nullable Object argument) {
	}
	@SuppressWarnings("unused")
	private void privateNormalMethod(Object argument) {
	}
	@SuppressWarnings("unused")
	private void privateNonnullMethod(@Nonnull Object argument) {
	}
	@SuppressWarnings("unused")
	private void privateNullableMethod(@Nullable Object argument) {
	}

	// to check working well for methods without arguments 
	public void publicMethodWithoutArguments() {
	}
	// to check working well for methods with primitive arguments 
	public void publicMethodWithPrimitive(int argument) {
	}
}