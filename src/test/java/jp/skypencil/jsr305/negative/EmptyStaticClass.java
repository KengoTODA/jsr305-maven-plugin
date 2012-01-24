package jp.skypencil.jsr305.negative;

import javax.annotation.Nonnegative;

public class EmptyStaticClass {
	public static void publicNormalIntMethod(int argument) {}
	public static void publicNonnegativeIntMethod(@Nonnegative int argument) {}
	protected static void protectedNormalIntMethod(int argument) {}
	protected static void protectedNonnegativeIntMethod(@Nonnegative int argument) {}
	static void defaultNormalIntMethod(int argument) {}
	static void defaultNonnegativeIntMethod(@Nonnegative int argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalIntMethod(int argument) {}
	@SuppressWarnings("unused")
	private static void privateNonnegativeIntMethod(@Nonnegative int argument) {}
}
