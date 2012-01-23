package jp.skypencil.jsr305.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class EmptyStaticClass {
	public static void publicNormalMethod(Object param) {
	}
	public static void publicNonnullMethod(@Nonnull Object param) {
	}
	public static void publicNullableMethod(@Nullable Object param) {
	}
	static void defaultNormalMethod(Object param) {
	}
	static void defaultNonnullMethod(@Nonnull Object param) {
	}
	static void defaultNullableMethod(@Nullable Object param) {
	}
	protected static void protectedNormalMethod(Object param) {
	}
	protected static void protectedNonnullMethod(@Nonnull Object param) {
	}
	protected static void protectedNullableMethod(@Nullable Object param) {
	}
	@SuppressWarnings("unused")
	private static void privateNormalMethod(Object param) {
	}
	@SuppressWarnings("unused")
	private static void privateNonnullMethod(@Nonnull Object param) {
	}
	@SuppressWarnings("unused")
	private static void privateNullableMethod(@Nullable Object param) {
	}
}