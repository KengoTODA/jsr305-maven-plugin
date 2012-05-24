package jp.skypencil.jsr305.nullable;

public enum NullCheckLevel {
	PERMISSIVE {
		@Override
		NullCheckStrategyFactory createFactory(int arguments) {
			return new PermissiveStrategyFactory(arguments);
		}
	},
	STRICT {
		@Override
		NullCheckStrategyFactory createFactory(int arguments) {
			return new StrictStrategyFactory(arguments);
		}
	};

	abstract NullCheckStrategyFactory createFactory(int arguments);
}
