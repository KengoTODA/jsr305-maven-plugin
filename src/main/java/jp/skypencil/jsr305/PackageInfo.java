package jp.skypencil.jsr305;

final class PackageInfo {
	private boolean nonnullByDefault;

	PackageInfo(boolean nonnullByDefault) {
		this.nonnullByDefault = nonnullByDefault;
	}

	boolean isNonnullByDefault() {
		return nonnullByDefault;
	}
}
