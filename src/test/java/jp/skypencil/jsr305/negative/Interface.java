package jp.skypencil.jsr305.negative;

import javax.annotation.Nonnegative;

interface Interface {
	void method(@Nonnegative int argument);
}
