# maven-jsr305-plugin
a Maven plugin which injects precondition check logics to your classes.

## about jsr305
 - http://jcp.org/en/jsr/detail?id=305

# how to use
Just write this in your pom.xml.
    <plugin>
    	<groupId>jp.skypencil</groupId>
    	<artifactId>maven-jsr305-plugin</artifactId>
    	<version>0.1-SNAPSHOT</version>
    	<executions>
    		<execution>
    			<goals>
    				<goal>enhance</goal>
    			</goals>
    		</execution>
    	</executions>
    </plugin>

# features
## Null check
This feature injects a code like `if (arg == null) {throw new IllegalArgumentException("...");}`.
This feature has 3 configurations.
1. target scope (PUBLIC, PROTECTED, DEFAULT, PRIVATE)
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
Default is `java.lang.IllegalArgumentException`.


## Negative check
This feature injects a code like `if (arg < 0) {throw new IllegalArgumentException("...");}`.
This feature has 2 configurations.
1. target scope (PUBLIC, PROTECTED, DEFAULT, PRIVATE)
2. exception which will be thrown

You can write them in your pom.xml. For example:
    <configuration>
    	<negativeCheck>
    		<targetScope>DEFAULT</targetScope>
    		<exception>java.lang.IllegalArgumentException</exception>
    	</negativeCheck>
    </configuration>

Please note that this feature handles NaN arguments as negative.

### 1st configuration: target scope
If you choose `PROTECTED` for target scope, check logics will be injected to `public` and `protected` methods
including constructors.
If you choose `PUBLIC`, check logics will be injected to only `public` methods including constructors.
Default value is `PUBLIC`.

### 2nd configuration: exception which will be thrown
You can specify an exception which has a constructor with a String argument.
Default is `java.lang.IllegalArgumentException`.


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
