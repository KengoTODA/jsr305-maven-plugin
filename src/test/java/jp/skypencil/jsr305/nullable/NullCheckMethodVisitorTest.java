package jp.skypencil.jsr305.nullable;

import static org.junit.Assert.assertEquals;
import jp.skypencil.jsr305.Scope;
import jp.skypencil.jsr305.Setting;

import org.junit.Test;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class NullCheckMethodVisitorTest {

	@Test
	public void testOrdinal() {
		Setting setting = new Setting(Scope.PRIVATE, NullCheckLevel.PERMISSIVE, IllegalArgumentException.class);
		NullCheckMethodVisitor visitor = new NullCheckMethodVisitor(Opcodes.ASM4, new MethodVisitor(Opcodes.ASM4) {}, false, new Type[0], setting);

		assertEquals("0th", visitor.createOrdinal(0));
		assertEquals("1st", visitor.createOrdinal(1));
		assertEquals("2nd", visitor.createOrdinal(2));
		assertEquals("3rd", visitor.createOrdinal(3));
		assertEquals("4th", visitor.createOrdinal(4));
		assertEquals("11th", visitor.createOrdinal(11));
		assertEquals("12th", visitor.createOrdinal(12));
	}

}
