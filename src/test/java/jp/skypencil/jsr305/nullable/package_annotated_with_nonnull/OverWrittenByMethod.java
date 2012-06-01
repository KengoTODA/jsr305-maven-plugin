package jp.skypencil.jsr305.nullable.package_annotated_with_nonnull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;

public class OverWrittenByMethod {
	@ParametersAreNullableByDefault
	public void publicNormalMethod(Object argument) {
	}
	@ParametersAreNullableByDefault
	public void publicNonnullMethod(@Nonnull Object argument) {
	}
	@ParametersAreNullableByDefault
	public void publicNullableMethod(@Nullable Object argument) {
	}
}