package jp.skypencil.jsr305.negative;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.skypencil.jsr305.Scope;
import jp.skypencil.jsr305.negative.NegativeCheckLevel;

public class FloatNegativeCheckTest extends NegativeCheckTest {

	public FloatNegativeCheckTest(NegativeCheckLevel level,
			Scope settingScope, Scope targetScope, boolean expected) {
		super(level, settingScope, targetScope, expected);
	}

	@Override
	protected boolean isInjected(Object instance, Method method,
			Class<? extends Throwable> exception)
			throws IllegalArgumentException, IllegalAccessException {
		method.setAccessible(true);
		try {
			method.invoke(instance, new Object[] { -1F });
			return false;
		} catch (InvocationTargetException expected) {
			assertThat(expected.getTargetException(), is(exception));
			return true;
		}
	}

	@Override
	protected String getTypeName() {
		return "Float";
	}

	@Override
	protected Class<?> getType() {
		return float.class;
	}

}
