package jp.skypencil.jsr305.nullable;

import java.util.BitSet;
import java.util.List;

import javax.annotation.Nullable;

import org.objectweb.asm.Type;

import com.google.common.collect.Lists;

class StrictStrategyFactory implements NullCheckStrategyFactory {
	private static final String TARGET_DESCRIPTOR = Type.getDescriptor(Nullable.class);
	private final BitSet isNullable = new BitSet();
	private final int paramCount;

	public StrictStrategyFactory(int arguments) {
		this.paramCount = arguments;
	}

	@Override
	public void add(int parameterIndex, String descriptor) {
		if (TARGET_DESCRIPTOR.equals(descriptor)) {
			isNullable.set(parameterIndex);
		}
	}

	@Override
	public NullCheckStrategy build() {
		return new NullCheckStrategy() {
			@Override
			public Iterable<Integer> getParamIndexForNullCheck() {
				List<Integer> result = Lists.newArrayList();
				for (int i = 0; i < paramCount; ++i) {
					if (!isNullable.get(i)) {
						result.add(Integer.valueOf(i));
					}
				}
				return result;
			}
		};
	}

	@Override
	public String getReason() {
		return "this param isn't annotated with javax.annotation.Nullable";
	}

	@Override
	public void markAsNonnullByDefault() {
		// Noting to do
	}
}
