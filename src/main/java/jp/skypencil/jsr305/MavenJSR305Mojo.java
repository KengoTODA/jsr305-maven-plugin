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

/**
 * enhance check codes
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
	 * @parameter property="jsr305.classes"
	 *            default-value="${project.build.outputDirectory}"
	 * @required
	 */
	protected File classesDirectory;
	/**
	 * Setting for null check. default is:
	 *
	 * <pre>&lt;configuration&gt;
	 *   &lt;nullCheck&gt;
	 *     &lt;targetScope&gt;PUBLIC&lt;/targetScope&gt;
	 *     &lt;level&gt;PERMISSIVE&lt;/level&gt;
	 *   &lt;exception&gt;java.lang.IllegalArgumentException&lt;/exception&gt;
	 * &lt;/nullCheck&gt;
	 * &lt;/configuration&gt;</pre>
	 * 
	 * @parameter
	 */
	protected jp.skypencil.jsr305.nullable.Setting nullCheck;
	/**
	 * Setting for negative check. default is:
	 * <pre>&lt;configuration&gt;
	 *   &lt;negativeCheck&gt;
	 *     &lt;targetScope&gt;PUBLIC&lt;/targetScope&gt;
	 *     &lt;exception&gt;java.lang.IllegalArgumentException&lt;/exception&gt;
	 *   &lt;/negativeCheck&gt;
	 * &lt;/configuration&gt;</pre>
	 * 
	 * @parameter
	 */
	protected jp.skypencil.jsr305.negative.Setting negativeCheck;
	/**
	 * Setting for regex check. default is:
	 * <pre>&lt;configuration&gt;
	 *   &lt;regexCheck&gt;
	 *     &lt;targetScope&gt;PUBLIC&lt;/targetScope&gt;
	 *     &lt;exception&gt;java.lang.IllegalArgumentException&lt;/exception&gt;
	 *   &lt;/regexCheck&gt;
	 * &lt;/configuration&gt;</pre>
	 * 
	 * @parameter
	 */
	protected jp.skypencil.jsr305.regex.Setting regexCheck;

	@Override
	public void execute() throws MojoExecutionException {
		getLog().info("Start to enhance...");
		if (!classesDirectory.exists()) {
			throw new MojoExecutionException("Class directory is not found. Didn't you compile yet?");
		}

		try {
			new MavenJSR305PackageVisitor(nullCheck, negativeCheck, regexCheck).visitPackage(classesDirectory, getLog());
		} catch (IOException e) {
			throw new MojoExecutionException("A inner exception occurs", e);
		}
		getLog().info("finished.");
	}
}
