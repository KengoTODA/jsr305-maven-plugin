package jp.skypencil.jsr305.regex;

import javax.annotation.Nonnull;

import jp.skypencil.jsr305.Scope;

public class Setting {

	private final Scope settingScope;

	public Setting(@Nonnull Scope settingScope) {
		this.settingScope = settingScope;
	}

	Scope getSettingScope() {
		return settingScope;
	}

}
