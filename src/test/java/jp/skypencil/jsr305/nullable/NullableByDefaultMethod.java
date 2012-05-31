package jp.skypencil.jsr305.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;

public class NullableByDefaultMethod {
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