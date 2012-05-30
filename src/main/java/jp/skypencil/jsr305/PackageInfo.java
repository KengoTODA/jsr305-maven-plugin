package jp.skypencil.jsr305;

public final class PackageInfo {
	private boolean nonnullByDefault;

	public PackageInfo(boolean nonnullByDefault) {
		this.nonnullByDefault = nonnullByDefault;
	}

	public boolean isNonnullByDefault() {
		return nonnullByDefault;
	}
}
