package jp.skypencil.jsr305.nullable;

import java.util.Arrays;
import java.util.List;

import jp.skypencil.jsr305.Scope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NullCheckTest extends AbstractTest {
	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{NullCheckLevel.PERMISSIVE, Scope.NONE, Scope.PRIVATE, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.NONE, Scope.DEFAULT, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.NONE, Scope.PROTECTED, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.NONE, Scope.PUBLIC, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.PRIVATE, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.DEFAULT, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.PROTECTED, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.PRIVATE, Scope.PUBLIC, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.PRIVATE, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.DEFAULT, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.PROTECTED, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.DEFAULT, Scope.PUBLIC, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.PRIVATE, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.DEFAULT, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.PROTECTED, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.PROTECTED, Scope.PUBLIC, true, false},
				{NullCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.PRIVATE, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.DEFAULT, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.PROTECTED, false, false},
				{NullCheckLevel.PERMISSIVE, Scope.PUBLIC, Scope.PUBLIC, true, false},
				{NullCheckLevel.STRICT, Scope.NONE, Scope.PRIVATE, false, false},
				{NullCheckLevel.STRICT, Scope.NONE, Scope.DEFAULT, false, false},
				{NullCheckLevel.STRICT, Scope.NONE, Scope.PROTECTED, false, false},
				{NullCheckLevel.STRICT, Scope.NONE, Scope.PUBLIC, false, false},
				{NullCheckLevel.STRICT, Scope.PRIVATE, Scope.PRIVATE, true, true},
				{NullCheckLevel.STRICT, Scope.PRIVATE, Scope.DEFAULT, true, true},
				{NullCheckLevel.STRICT, Scope.PRIVATE, Scope.PROTECTED, true, true},
				{NullCheckLevel.STRICT, Scope.PRIVATE, Scope.PUBLIC, true, true},
				{NullCheckLevel.STRICT, Scope.DEFAULT, Scope.PRIVATE, false, false},
				{NullCheckLevel.STRICT, Scope.DEFAULT, Scope.DEFAULT, true, true},
				{NullCheckLevel.STRICT, Scope.DEFAULT, Scope.PROTECTED, true, true},
				{NullCheckLevel.STRICT, Scope.DEFAULT, Scope.PUBLIC, true, true},
				{NullCheckLevel.STRICT, Scope.PROTECTED, Scope.PRIVATE, false, false},
				{NullCheckLevel.STRICT, Scope.PROTECTED, Scope.DEFAULT, false, false},
				{NullCheckLevel.STRICT, Scope.PROTECTED, Scope.PROTECTED, true, true},
				{NullCheckLevel.STRICT, Scope.PROTECTED, Scope.PUBLIC, true, true},
				{NullCheckLevel.STRICT, Scope.PUBLIC, Scope.PRIVATE, false, false},
				{NullCheckLevel.STRICT, Scope.PUBLIC, Scope.DEFAULT, false, false},
				{NullCheckLevel.STRICT, Scope.PUBLIC, Scope.PROTECTED, false, false},
				{NullCheckLevel.STRICT, Scope.PUBLIC, Scope.PUBLIC, true, true},
		});
	}

	public NullCheckTest(NullCheckLevel level, Scope settingScope, Scope targetScope, boolean nonnullIsTarget, boolean defaultIsTarget) {
		super(level, settingScope, targetScope, nonnullIsTarget, defaultIsTarget);
	}

	@Test
	public void testForNonStaticMethod() throws Throwable {
		test("jp/skypencil/jsr305/nullable/EmptyClass", IllegalArgumentException.class);
	}

	@Test
	public void testForStaticMethod() throws Throwable {
		test("jp/skypencil/jsr305/nullable/EmptyStaticClass", IllegalArgumentException.class);
	}

	@Test
	public void testForNonStaticMethodWithNullPointerException() throws Throwable {
		test("jp/skypencil/jsr305/nullable/EmptyClass", NullPointerException.class);
	}

	@Test
	public void testForStaticMethodWithNullPointerException() throws Throwable {
		test("jp/skypencil/jsr305/nullable/EmptyStaticClass", NullPointerException.class);
	}
}
