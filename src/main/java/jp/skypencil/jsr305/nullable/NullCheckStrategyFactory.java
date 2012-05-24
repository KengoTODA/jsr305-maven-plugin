package jp.skypencil.jsr305.nullable;

interface NullCheckStrategyFactory {
	void markAsNonnullByDefault();
	void add(int parameterIndex, String descriptor);
	NullCheckStrategy build();
	String getReason();
}
