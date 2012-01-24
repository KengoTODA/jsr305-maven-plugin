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

	public static void publicNormalFloatMethod(float argument) {}
	public static void publicNonnegativeFloatMethod(@Nonnegative float argument) {}
	protected static void protectedNormalFloatMethod(float argument) {}
	protected static void protectedNonnegativeFloatMethod(@Nonnegative float argument) {}
	static void defaultNormalFloatMethod(float argument) {}
	static void defaultNonnegativeFloatMethod(@Nonnegative float argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalFloatMethod(float argument) {}
	@SuppressWarnings("unused")
	private static void privateNonnegativeFloatMethod(@Nonnegative float argument) {}

	public static void publicNormalByteMethod(byte argument) {}
	public static void publicNonnegativeByteMethod(@Nonnegative byte argument) {}
	protected static void protectedNormalByteMethod(byte argument) {}
	protected static void protectedNonnegativeByteMethod(@Nonnegative byte argument) {}
	static void defaultNormalByteMethod(byte argument) {}
	static void defaultNonnegativeByteMethod(@Nonnegative byte argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalByteMethod(byte argument) {}
	@SuppressWarnings("unused")
	private static void privateNonnegativeByteMethod(@Nonnegative byte argument) {}

	public static void publicNormalShortMethod(short argument) {}
	public static void publicNonnegativeShortMethod(@Nonnegative short argument) {}
	protected static void protectedNormalShortMethod(short argument) {}
	protected static void protectedNonnegativeShortMethod(@Nonnegative short argument) {}
	static void defaultNormalShortMethod(short argument) {}
	static void defaultNonnegativeShortMethod(@Nonnegative short argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalShortMethod(short argument) {}
	@SuppressWarnings("unused")
	private static void privateNonnegativeShortMethod(@Nonnegative short argument) {}

	// to check working well for methods without arguments 
	public static void publicMethodWithoutArguments() {
	}
	// to check working well for methods with non-Number arguments 
	public static void publicMethodWithPrimitive(Object object) {
	}
}
