package jp.skypencil.jsr305.negative;

import javax.annotation.Nonnegative;

public final class EmptyClass {
	public void publicNormalIntMethod(int argument) {}
	public void publicNonnegativeIntMethod(@Nonnegative int argument) {}
	protected void protectedNormalIntMethod(int argument) {}
	protected void protectedNonnegativeIntMethod(@Nonnegative int argument) {}
	void defaultNormalIntMethod(int argument) {}
	void defaultNonnegativeIntMethod(@Nonnegative int argument) {}
	@SuppressWarnings("unused")
	private void privateNormalIntMethod(int argument) {}
	@SuppressWarnings("unused")
	private void privateNonnegativeIntMethod(@Nonnegative int argument) {}

	// to check working well for methods without arguments 
	public void publicMethodWithoutArguments() {
	}
	// to check working well for methods with non-Number arguments 
	public void publicMethodWithPrimitive(Object object) {
	}
}
