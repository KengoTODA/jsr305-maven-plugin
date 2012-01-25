package jp.skypencil.jsr305.negative;

import javax.annotation.Nonnegative;

abstract class AbstractClass {
	abstract void method(@Nonnegative int argument);
}
