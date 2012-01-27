package jp.skypencil.jsr305;

import javax.annotation.Nonnegative;


public class OrdinalBuilder {
	private OrdinalBuilder() {}

	public static String valueOf(@Nonnegative int index) {
		switch (index) {
			case 1: return "1st";
			case 2: return "2nd";
			case 3: return "3rd";
			default: return index + "th";
		}
	}
}
