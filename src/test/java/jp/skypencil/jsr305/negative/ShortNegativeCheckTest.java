package jp.skypencil.jsr305.negative;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;

import jp.skypencil.jsr305.Scope;
import jp.skypencil.jsr305.negative.NegativeCheckLevel;

public class ShortNegativeCheckTest extends NegativeCheckTest {

	public ShortNegativeCheckTest(NegativeCheckLevel level,
			Scope settingScope, Scope targetScope, boolean expected) {
		super(level, settingScope, targetScope, expected);
	}

	@Override
	protected boolean isInjected(Object instance, Method method,
			Class<? extends Throwable> exception)
			throws IllegalArgumentException, IllegalAccessException {
		method.setAccessible(true);
		try {
			method.invoke(instance, new Object[] { (short) 0 });
			method.invoke(instance, new Object[] { (short) 1 });
		} catch (InvocationTargetException e) {
			Assert.fail();
		}

		try {
			method.invoke(instance, new Object[] { (short) -1 });
			return false;
		} catch (InvocationTargetException expected) {
			assertThat(expected.getTargetException(), is(exception));
			return true;
		}
	}

	@Override
	protected String getTypeName() {
		return "Short";
	}

	@Override
	protected Class<?> getType() {
		return short.class;
	}

}
