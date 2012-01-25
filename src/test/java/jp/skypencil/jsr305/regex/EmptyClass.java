package jp.skypencil.jsr305.regex;

import javax.annotation.MatchesPattern;

public class EmptyClass {
	public void publicNormalMethod(String argument) {}
	public void publicNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	public void publicAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
	protected void protectedNormalMethod(String argument) {}
	protected void protectedNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	protected void protectedAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
	void defaultNormalMethod(String argument) {}
	void defaultNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	void defaultAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
	@SuppressWarnings("unused")
	private void privateNormalMethod(String argument) {}
	@SuppressWarnings("unused")
	private void privateNumMethod(@MatchesPattern("[0-9]+") String argument) {}
	@SuppressWarnings("unused")
	private void privateAlphabetMethod(@MatchesPattern("[a-z]+") String argument) {}
}
