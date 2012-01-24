package jp.skypencil.jsr305.negative;

public enum NegativeCheckLevel {
	PERMISSIVE {
		@Override
		NegativeCheckStrategyFactory createFactory() {
			return new PermissiveStrategyFactory();
		}
	};
	abstract NegativeCheckStrategyFactory createFactory();
}
