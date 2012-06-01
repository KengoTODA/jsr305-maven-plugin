package jp.skypencil.jsr305.nullable.package_annotated_with_nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class OverWrittenByMethod {
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