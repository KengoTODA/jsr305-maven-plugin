package jp.skypencil.jsr305.nullable.package_annotated_with_nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OverWrittenByClass {
	public void publicNormalMethod(Object argument) {
	}
	public void publicNonnullMethod(@Nonnull Object argument) {
	}
	public void publicNullableMethod(@Nullable Object argument) {
	}
}