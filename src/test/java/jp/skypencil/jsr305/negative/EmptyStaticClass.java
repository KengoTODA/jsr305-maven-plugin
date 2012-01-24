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

	public static void publicNormalDoubleMethod(double argument) {}
	public static void publicNonnegativeDoubleMethod(@Nonnegative double argument) {}
	protected static void protectedNormalDoubleMethod(double argument) {}
	protected static void protectedNonnegativeDoubleMethod(@Nonnegative double argument) {}
	static void defaultNormalDoubleMethod(double argument) {}
	static void defaultNonnegativeDoubleMethod(@Nonnegative double argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalDoubleMethod(double argument) {}
	@SuppressWarnings("unused")
	private static void privateNonnegativeDoubleMethod(@Nonnegative double argument) {}

	public static void publicNormalLongMethod(long argument) {}
	public static void publicNonnegativeLongMethod(@Nonnegative long argument) {}
	protected static void protectedNormalLongMethod(long argument) {}
	protected static void protectedNonnegativeLongMethod(@Nonnegative long argument) {}
	static void defaultNormalLongMethod(long argument) {}
	static void defaultNonnegativeLongMethod(@Nonnegative long argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalLongMethod(long argument) {}
	@SuppressWarnings("unused")
	private static void privateNonnegativeLongMethod(@Nonnegative long argument) {}

	// to check working well for methods without arguments 
	public static void publicMethodWithoutArguments() {
	}
	// to check working well for methods with non-Number arguments 
	public static void publicMethodWithPrimitive(Object object) {
	}
}
