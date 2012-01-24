package jp.skypencil.jsr305.negative;


interface NegativeCheckStrategyFactory {
	void add(int parameterIndex, String descriptor);
	NegativeCheckStrategy build();
	String getReason();
}
