package jp.skypencil.jsr305;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.google.common.io.Files;

/**
 * enhance null check codes
 * 
 * @goal enhance
 * @phase process-classes
 * @requiresDependencyResolution compile
 * @author eller86 (Kengo TODA)
 */
public class MavenJSR305Mojo extends AbstractMojo {
	/**
	 * Place where target classes are.
	 * 
	 * @parameter expression="${jsr305.classes}"
	 *            default-value="${project.build.outputDirectory}"
	 * @required
	 */
	protected File classesDirectory;
	/**
	 * @parameter
	 * @required
	 */
	protected jp.skypencil.jsr305.nullable.Setting nullCheck;
	/**
	 * @parameter
	 * @required
	 */
	protected jp.skypencil.jsr305.negative.Setting negativeCheck;

	@Override
	public void execute() throws MojoExecutionException {
		getLog().info("Start to enhance...");
		if (!classesDirectory.exists()) {
			throw new MojoExecutionException("Class directory is not found. Didn't you compile yet?");
		}

		try {
			enhance(classesDirectory);
		} catch (IOException e) {
			throw new MojoExecutionException("A inner exception occurs", e);
		} catch (ClassNotFoundException e) {
			throw new MojoExecutionException("A inner exception occurs", e);
		}
		getLog().info("finished.");
	}

	private void enhance(File dir) throws IOException, ClassNotFoundException {
		for (File child : dir.listFiles()) {
			if (child.isDirectory()) {
				enhance(child);
			} else {
				if (Files.getFileExtension(child.getName()).equals("class")) {
					getLog().debug("inject to " + child.getAbsolutePath());
					byte[] binary = Files.toByteArray(child);
					int majorVersionOfClassFileFormat = binary[6] << 8 | binary[7];
					int api = apiFor(majorVersionOfClassFileFormat);

					ClassReader reader = new ClassReader(binary);
					ClassWriter writer = new ClassWriter(0);
					reader.accept(new MavenJSR305ClassVisitor(api, writer, nullCheck, negativeCheck), 0);
					byte[] enhanced = writer.toByteArray();
					Files.write(enhanced, child);
				}
			}
		}
	}

	private int apiFor(int majorVersionOfClassFileFormat) {
		switch (majorVersionOfClassFileFormat) {
			case 51: return Opcodes.V1_7;
			case 50: return Opcodes.V1_6;
			case 49: return Opcodes.V1_5;
		}
		throw new RuntimeException("unknown major version: " + majorVersionOfClassFileFormat);
	}
}
