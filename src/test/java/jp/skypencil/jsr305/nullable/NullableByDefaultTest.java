package jp.skypencil.jsr305.nullable;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jp.skypencil.jsr305.Scope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NullableByDefaultTest extends AbstractTest {
	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{NullCheckLevel.STRICT},
				{NullCheckLevel.PERMISSIVE}
		});
	}

	public NullableByDefaultTest(NullCheckLevel level) throws IOException {
		super(level, Scope.PUBLIC, Scope.PUBLIC, true, false);
	}

	@Test
	public void testAnnotatedPackage() throws Throwable {
		test("jp/skypencil/jsr305/nullable/package_annotated_with_nullable/NullableByDefaultPackage", IllegalArgumentException.class);
	}

	@Test
	public void testAnnotatedClass() throws Throwable {
		test("jp/skypencil/jsr305/nullable/NullableByDefaultClass", IllegalArgumentException.class);
	}

	@Test
	public void testMethodAnnotationOverWriteClassAnnotation() throws Throwable {
		test("jp/skypencil/jsr305/nullable/OverWrittenByNullableClass", IllegalArgumentException.class);
	}

	@Test
	public void testMethodAnnotationOverWritePackageAnnotation() throws Throwable {
		test("jp/skypencil/jsr305/nullable/package_annotated_with_nonnull/OverWrittenByMethod", IllegalArgumentException.class);
	}

	@Test
	public void testClassAnnotationOverWritePackageAnnotation() throws Throwable {
		test("jp/skypencil/jsr305/nullable/package_annotated_with_nonnull/OverWrittenByClass", IllegalArgumentException.class);
	}

	@Test
	public void testAnnotatedMethod() throws Throwable {
		test("jp/skypencil/jsr305/nullable/NullableByDefaultMethod", IllegalArgumentException.class);
	}
}
