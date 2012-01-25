package jp.skypencil.jsr305.regex;

import javax.annotation.MatchesPattern;

public class EmptyStaticClass {
	public static void publicNormalMethod(String argument) {}
	public static void publicNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	public static void publicAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
	protected static void protectedNormalMethod(String argument) {}
	protected static void protectedNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	protected static void protectedAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
	static void defaultNormalMethod(String argument) {}
	static void defaultNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	static void defaultAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
	@SuppressWarnings("unused")
	private static void privateNormalMethod(String argument) {}
	@SuppressWarnings("unused")
	private static void privateNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	@SuppressWarnings("unused")
	private static void privateAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
}
