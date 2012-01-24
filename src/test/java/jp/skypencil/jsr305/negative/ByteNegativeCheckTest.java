package jp.skypencil.jsr305.negative;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;

import jp.skypencil.jsr305.Scope;
import jp.skypencil.jsr305.negative.NegativeCheckLevel;

public class ByteNegativeCheckTest extends NegativeCheckTest {

	public ByteNegativeCheckTest(NegativeCheckLevel level,
			Scope settingScope, Scope targetScope, boolean expected) {
		super(level, settingScope, targetScope, expected);
	}

	@Override
	protected boolean isInjected(Object instance, Method method,
			Class<? extends Throwable> exception)
			throws IllegalArgumentException, IllegalAccessException {
		method.setAccessible(true);
		try {
			method.invoke(instance, new Object[] { (byte) 0 });
			method.invoke(instance, new Object[] { (byte) 1 });
		} catch (InvocationTargetException e) {
			Assert.fail();
		}

		try {
			method.invoke(instance, new Object[] { (byte) -1 });
			return false;
		} catch (InvocationTargetException expected) {
			assertThat(expected.getTargetException(), is(exception));
			return true;
		}
	}

	@Override
	protected String getTypeName() {
		return "Byte";
	}

	@Override
	protected Class<?> getType() {
		return byte.class;
	}

}
