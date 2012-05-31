package jp.skypencil.jsr305.nullable;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.objectweb.asm.Type;

import com.google.common.collect.Lists;

class StrictStrategyFactory implements NullCheckStrategyFactory {
	private static final String NULLABLE_DESCRIPTOR = Type.getDescriptor(Nullable.class);
	private static final String NONNULL_DESCRIPTOR = Type.getDescriptor(Nonnull.class);
	private final List<Boolean> annotatedParams;
	private boolean nonnullByDefault = true;

	public StrictStrategyFactory(int arguments) {
		this.annotatedParams = Lists.newArrayListWithCapacity(arguments);
		while (annotatedParams.size() < arguments) {
			annotatedParams.add(null);
		}
	}

	@Override
	public void add(int parameterIndex, String descriptor) {
		if (NULLABLE_DESCRIPTOR.equals(descriptor)) {
			annotatedParams.set(parameterIndex, Boolean.FALSE);
		} else if (NONNULL_DESCRIPTOR.equals(descriptor)) {
			annotatedParams.set(parameterIndex, Boolean.TRUE);
		}
	}

	@Override
	public NullCheckStrategy build() {
		final List<Integer> result = Lists.newArrayList();
		for (int i = 0; i < annotatedParams.size(); ++i) {
			if (Boolean.TRUE.equals(annotatedParams.get(i)) || 
					(nonnullByDefault && annotatedParams.get(i) == null)) {
				result.add(Integer.valueOf(i));
			}
		}

		return new NullCheckStrategy() {
			@Override
			public Iterable<Integer> getParamIndexForNullCheck() {
				// we don't need to defensive copy 
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
		this.nonnullByDefault = true;
	}

	@Override
	public void markAsNullableByDefault() {
		this.nonnullByDefault  = false;
	}
}
