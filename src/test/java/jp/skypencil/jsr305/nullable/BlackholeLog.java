package jp.skypencil.jsr305.nullable;

import org.apache.maven.plugin.logging.Log;

final class BlackholeLog implements Log {

	@Override
	public void debug(CharSequence arg0) {}

	@Override
	public void debug(Throwable arg0) {}

	@Override
	public void debug(CharSequence arg0, Throwable arg1) {}

	@Override
	public void error(CharSequence arg0) {}

	@Override
	public void error(Throwable arg0) {}

	@Override
	public void error(CharSequence arg0, Throwable arg1) {}

	@Override
	public void info(CharSequence arg0) {}

	@Override
	public void info(Throwable arg0) {}

	@Override
	public void info(CharSequence arg0, Throwable arg1) {}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public void warn(CharSequence arg0) {}

	@Override
	public void warn(Throwable arg0) {}

	@Override
	public void warn(CharSequence arg0, Throwable arg1) {}

}
