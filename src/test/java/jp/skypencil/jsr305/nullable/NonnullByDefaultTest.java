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
public class NonnullByDefaultTest extends AbstractTest {
	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{NullCheckLevel.STRICT},
				{NullCheckLevel.PERMISSIVE}
		});
	}

	public NonnullByDefaultTest(NullCheckLevel level) throws IOException {
		super(level, Scope.PUBLIC, Scope.PUBLIC, true, true);
	}

	@Test
	public void testAnnotatedPackage() throws Throwable {
		test("jp/skypencil/jsr305/nullable/package_annotated_with_nonnull/NonnullByDefaultPackage", IllegalArgumentException.class);
	}

	@Test
	public void testAnnotatedClass() throws Throwable {
		test("jp/skypencil/jsr305/nullable/NonnullByDefaultClass", IllegalArgumentException.class);
	}

	@Test
	public void testMethodAnnotationOverWriteClassAnnotation() throws Throwable {
		test("jp/skypencil/jsr305/nullable/OverWrittenByNonnullClass", IllegalArgumentException.class);
	}

	@Test
	public void testClassAnnotationOverWritePackageAnnotation() throws Throwable {
		test("jp/skypencil/jsr305/nullable/package_annotated_with_nullable/OverWrittenByClass", IllegalArgumentException.class);
	}

	@Test
	public void testMethodAnnotationOverWritePackageAnnotation() throws Throwable {
		test("jp/skypencil/jsr305/nullable/package_annotated_with_nullable/OverWrittenByMethod", IllegalArgumentException.class);
	}

	@Test
	public void testAnnotatedMethod() throws Throwable {
		test("jp/skypencil/jsr305/nullable/NonnullByDefaultMethod", IllegalArgumentException.class);
	}
}
