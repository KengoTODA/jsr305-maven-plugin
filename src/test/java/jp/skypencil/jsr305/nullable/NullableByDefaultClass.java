package jp.skypencil.jsr305.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;

@ParametersAreNullableByDefault
public class NullableByDefaultClass {
	public void publicNormalMethod(Object argument) {
	}
	public void publicNonnullMethod(@Nonnull Object argument) {
	}
	public void publicNullableMethod(@Nullable Object argument) {
	}
}