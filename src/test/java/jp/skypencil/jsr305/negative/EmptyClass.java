package jp.skypencil.jsr305.negative;

import java.math.BigDecimal;

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

	public void publicNormalFloatMethod(float argument) {}
	public void publicNonnegativeFloatMethod(@Nonnegative float argument) {}
	protected void protectedNormalFloatMethod(float argument) {}
	protected void protectedNonnegativeFloatMethod(@Nonnegative float argument) {}
	void defaultNormalFloatMethod(float argument) {}
	void defaultNonnegativeFloatMethod(@Nonnegative float argument) {}
	@SuppressWarnings("unused")
	private void privateNormalFloatMethod(float argument) {}
	@SuppressWarnings("unused")
	private void privateNonnegativeFloatMethod(@Nonnegative float argument) {}

	public void publicNormalByteMethod(byte argument) {}
	public void publicNonnegativeByteMethod(@Nonnegative byte argument) {}
	protected void protectedNormalByteMethod(byte argument) {}
	protected void protectedNonnegativeByteMethod(@Nonnegative byte argument) {}
	void defaultNormalByteMethod(byte argument) {}
	void defaultNonnegativeByteMethod(@Nonnegative byte argument) {}
	@SuppressWarnings("unused")
	private void privateNormalByteMethod(byte argument) {}
	@SuppressWarnings("unused")
	private void privateNonnegativeByteMethod(@Nonnegative byte argument) {}

	public void publicNormalShortMethod(short argument) {}
	public void publicNonnegativeShortMethod(@Nonnegative short argument) {}
	protected void protectedNormalShortMethod(short argument) {}
	protected void protectedNonnegativeShortMethod(@Nonnegative short argument) {}
	void defaultNormalShortMethod(short argument) {}
	void defaultNonnegativeShortMethod(@Nonnegative short argument) {}
	@SuppressWarnings("unused")
	private void privateNormalShortMethod(short argument) {}
	@SuppressWarnings("unused")
	private void privateNonnegativeShortMethod(@Nonnegative short argument) {}

	public static void publicNormalBigDecimalMethod(BigDecimal argument) {}
	public static void publicNonnegativeBigDecimalMethod(@Nonnegative BigDecimal argument) {}
	protected static void protectedNormalBigDecimalMethod(BigDecimal argument) {}
	protected static void protectedNonnegativeBigDecimalMethod(@Nonnegative BigDecimal argument) {}
	static void defaultNormalBigDecimalMethod(BigDecimal argument) {}
	static void defaultNonnegativeBigDecimalMethod(@Nonnegative BigDecimal argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalBigDecimalMethod(BigDecimal argument) {}
	@SuppressWarnings("unused")
	private static void privateNonnegativeBigDecimalMethod(@Nonnegative BigDecimal argument) {}

	// to check working well for methods without arguments 
	public void publicMethodWithoutArguments() {
	}
	// to check working well for methods with non-Number arguments 
	public void publicMethodWithPrimitive(Object object) {
	}
}
