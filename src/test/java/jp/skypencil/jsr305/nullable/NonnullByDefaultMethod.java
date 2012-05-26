package jp.skypencil.jsr305.nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class NonnullByDefaultMethod {
	@ParametersAreNonnullByDefault
	public void publicNormalMethod(Object argument) {
	}
	@ParametersAreNonnullByDefault
	public void publicNonnullMethod(@Nonnull Object argument) {
	}
	@ParametersAreNonnullByDefault
	public void publicNullableMethod(@Nullable Object argument) {
	}
}