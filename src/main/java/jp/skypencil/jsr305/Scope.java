package jp.skypencil.jsr305;

public enum Scope {
	NONE {
		@Override
		boolean contains(Scope other) {
			return false;
		}
	},
	PRIVATE {
		@Override
		boolean contains(Scope other) {
			return true;
		}
	},
	DEFAULT {
		@Override
		boolean contains(Scope other) {
			return other != PRIVATE;
		}
	},
	PROTECTED {
		@Override
		boolean contains(Scope other) {
			return other == PROTECTED || other == PUBLIC;
		}
	},
	PUBLIC {
		@Override
		boolean contains(Scope other) {
			return other == PUBLIC;
		}
	};
	abstract boolean contains(Scope other);
}
