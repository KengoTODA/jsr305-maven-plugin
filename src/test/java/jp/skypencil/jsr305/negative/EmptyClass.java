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

	public void publicNormalDoubleMethod(double argument) {}
	public void publicNonnegativeDoubleMethod(@Nonnegative double argument) {}
	protected void protectedNormalDoubleMethod(double argument) {}
	protected void protectedNonnegativeDoubleMethod(@Nonnegative double argument) {}
	void defaultNormalDoubleMethod(double argument) {}
	void defaultNonnegativeDoubleMethod(@Nonnegative double argument) {}
	@SuppressWarnings("unused")
	private void privateNormalDoubleMethod(double argument) {}
	@SuppressWarnings("unused")
	private void privateNonnegativeDoubleMethod(@Nonnegative double argument) {}

	public void publicNormalLongMethod(long argument) {}
	public void publicNonnegativeLongMethod(@Nonnegative long argument) {}
	protected void protectedNormalLongMethod(long argument) {}
	protected void protectedNonnegativeLongMethod(@Nonnegative long argument) {}
	void defaultNormalLongMethod(long argument) {}
	void defaultNonnegativeLongMethod(@Nonnegative long argument) {}
	@SuppressWarnings("unused")
	private void privateNormalLongMethod(long argument) {}
	@SuppressWarnings("unused")
	private void privateNonnegativeLongMethod(@Nonnegative long argument) {}

	// to check working well for methods without arguments 
	public void publicMethodWithoutArguments() {
	}
	// to check working well for methods with non-Number arguments 
	public void publicMethodWithPrimitive(Object object) {
	}
}
