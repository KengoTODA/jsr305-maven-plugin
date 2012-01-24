package jp.skypencil.jsr305.negative;

import java.util.SortedSet;

import javax.annotation.Nonnegative;

import org.objectweb.asm.Type;

import com.google.common.collect.Sets;

class PermissiveStrategyFactory implements NegativeCheckStrategyFactory {
	private static final String TARGET_DESCRIPTOR = Type.getDescriptor(Nonnegative.class);
	private final SortedSet<Integer> annotatedParams = Sets.newTreeSet();

	@Override
	public void add(int parameterIndex, String descriptor) {
		if (TARGET_DESCRIPTOR.equals(descriptor)) {
			annotatedParams.add(Integer.valueOf(parameterIndex));
		}
	}

	@Override
	public NegativeCheckStrategy build() {
		return new NegativeCheckStrategy() {
			@Override
			public Iterable<Integer> getParamIndexForNegativeCheck() {
				// we don't need to defensive copy 
				return annotatedParams;
			}
		};
	}

	@Override
	public String getReason() {
		return "this param is annotated with javax.annotation.Nonnegative.";
	}

}
