package jp.skypencil.jsr305;

public final class PackageInfo {
	private boolean nonnullByDefault;
	private boolean nullableByDefault;

	public PackageInfo(boolean nonnullByDefault, boolean nullableByDefault) {
		this.nonnullByDefault = nonnullByDefault;
		this.nullableByDefault = nullableByDefault;
	}

	public boolean isNonnullByDefault() {
		return nonnullByDefault;
	}

	public boolean isNullableByDefault() {
		return nullableByDefault;
	}
}
