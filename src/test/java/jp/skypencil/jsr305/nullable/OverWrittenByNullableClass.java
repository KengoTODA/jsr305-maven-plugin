package jp.skypencil.jsr305.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;

@ParametersAreNonnullByDefault
public class OverWrittenByNullableClass {
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