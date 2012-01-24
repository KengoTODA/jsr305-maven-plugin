package jp.skypencil.jsr305.negative;

interface NegativeCheckStrategy {
	Iterable<Integer> getParamIndexForNegativeCheck();
}
