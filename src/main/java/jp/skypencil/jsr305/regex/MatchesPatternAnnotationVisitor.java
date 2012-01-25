package jp.skypencil.jsr305.regex;

import java.util.regex.Pattern;

import org.objectweb.asm.AnnotationVisitor;

class MatchesPatternAnnotationVisitor extends AnnotationVisitor {
	private static final String REGEX_NAME = "value";
	private static final String FLAG_NAME = "flag";
	private int flag = 0;
	private String regex;
	private Pattern pattern;

	public MatchesPatternAnnotationVisitor(int api, AnnotationVisitor inner) {
		super(api, inner);
	}

	@Override
	public void visit(String name, Object value) {
		super.visit(name, value);
		if (REGEX_NAME.equals(name)) {
			regex = value.toString();
		} else if (FLAG_NAME.equals(name)) {
			flag = Integer.parseInt(value.toString());
		}
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		this.pattern = Pattern.compile(regex, flag);
	}

	Pattern getPattern() {
		return pattern;
	}
}
