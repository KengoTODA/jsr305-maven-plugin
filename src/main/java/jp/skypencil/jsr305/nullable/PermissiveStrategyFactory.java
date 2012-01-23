package jp.skypencil.jsr305.nullable;

import java.util.SortedSet;

import javax.annotation.Nonnull;

import org.objectweb.asm.Type;

import com.google.common.collect.Sets;

class PermissiveStrategyFactory implements NullCheckStrategyFactory {
	private static final String TARGET_DESCRIPTOR = Type.getDescriptor(Nonnull.class);
	private final SortedSet<Integer> annotatedParams = Sets.newTreeSet();

	@Override
	public void add(int parameterIndex, String descriptor) {
		if (TARGET_DESCRIPTOR.equals(descriptor)) {
			annotatedParams.add(Integer.valueOf(parameterIndex));
		}
	}

	@Override
	public NullCheckStrategy build() {
		return new NullCheckStrategy() {
			@Override
			public Iterable<Integer> getParamIndexForNullCheck() {
				// we don't need to defensive copy 
				return annotatedParams;
			}
		};
	}

	@Override
	public String getReason() {
		return "this param is annotated with javax.annotation.Nonnull.";
	}
}
