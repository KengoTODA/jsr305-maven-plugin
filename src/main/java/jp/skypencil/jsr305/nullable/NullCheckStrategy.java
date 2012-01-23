package jp.skypencil.jsr305.nullable;


interface NullCheckStrategy {
	Iterable<Integer> getParamIndexForNullCheck();
}
