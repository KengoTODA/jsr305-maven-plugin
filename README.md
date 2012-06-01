# Goodbye pre-condition check
Are you still coding boring precondition check in your project?
The `jsr305-maven-plugin` injects precondition check logics to classes for you!
What you have to do is: using [JSR305](http://code.google.com/p/jsr-305/) annotations.

![built and tested on DEV@cloud](http://static-www.cloudbees.com/images/badges/BuiltOnDEV.png)

# how to use
Just write this in your pom.xml.

    <build>
    	<plugins>
    		<plugin>
    			<groupId>jp.skypencil</groupId>
    			<artifactId>jsr305-maven-plugin</artifactId>
    			<version>1.1-SNAPSHOT</version>
    			<executions>
    				<execution>
    					<goals>
    						<goal>enhance</goal>
    					</goals>
    					<configurations>
    						...
    					</configurations>
    				</execution>
    			</executions>
    		</plugin>
    		...
    	</plugins>
    	...
    </build>
    <pluginRepositories>
    	<pluginRepository>
    		<!-- repository for jsr305-maven-plugin -->
    		<id>release.skypencil.forge.cloudbees.com</id>
    		<url>http://repository-skypencil.forge.cloudbees.com/release/</url>
    	</pluginRepository>
    </pluginRepositories>

# features
## Null check
This feature injects a code like `if (arg == null) {throw new IllegalArgumentException("...");}`.
This feature has 3 configurations.

1. target scope (PUBLIC, PROTECTED, DEFAULT, PRIVATE, NONE)
2. null check level (STRICT, PERMISSIVE)
3. exception which will be thrown

You can write them in your pom.xml. For example:

    <configuration>
    	<nullCheck>
    		<targetScope>DEFAULT</targetScope>
    		<level>STRICT</level>
    		<exception>java.lang.NullPointerException</exception>
    	</nullCheck>
    </configuration>

### 1st configuration: target scope
If you choose `PROTECTED` for target scope, check logics will be injected to `public` and `protected` methods
including constructors.
If you choose `PUBLIC`, check logics will be injected to only `public` methods including constructors.
Default value is `PUBLIC`.

### 2nd configuration: null check level
If you choose `STRICT` for this level, check logics will be injected to parameters without `@Nullable`
annotation.
If you choose `PERMISSIVE` for this level, check logics will be injected to parameters with `@Nonnull`
annotation.
Default value is `PERMISSIVE`.

### 3rd configuration: exception which will be thrown
You can specify an exception which has a constructor with a String argument.
Default value is `java.lang.IllegalArgumentException`.


## Negative check
This feature injects a code like `if (arg < 0) {throw new IllegalArgumentException("...");}` for arguments annotated with @Nonnegative.
This feature has 2 configurations.

1. target scope (PUBLIC, PROTECTED, DEFAULT, PRIVATE, NONE)
2. exception which will be thrown

You can write them in your pom.xml. For example:

    <configuration>
    	<negativeCheck>
    		<targetScope>DEFAULT</targetScope>
    		<exception>java.lang.IllegalArgumentException</exception>
    	</negativeCheck>
    </configuration>

Please note that this feature handles NaN arguments as negative.

## Regex check
This feature injects a code like `if (!Regex.matches(pattern, arg)) {throw new IllegalArgumentException("...");}` for arguments annotated with @MatchesPattern.
This feature has 2 configurations.

1. target scope (PUBLIC, PROTECTED, DEFAULT, PRIVATE, NONE)
2. exception which will be thrown

You can write them in your pom.xml. For example:

    <configuration>
    	<regexCheck>
    		<targetScope>DEFAULT</targetScope>
    		<exception>java.lang.IllegalArgumentException</exception>
    	</regexCheck>
    </configuration>


# History
## 0.1
- supported null check feature with @Nonnull and @Nullable annotation

## 0.2
- supported negative check feature with @Nonnegative annotation

## 0.3
- added `NONE` to scope
- started to build on cloudbees
- supported regex check feature with @MatchesPattern annotation

## 1.0
- fixed some bugs
- enhance code coverage
- update documents

## 1.1
- supported @ParametersAreNonnullByDefault
- supported @ParametersAreNullableByDefault
- renamed from maven-jsr305-plugin to jsr305-maven-plugin 
- reducing PMD warnings


# copyright and license
Copyright 2012 Kengo TODA (eller86)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
