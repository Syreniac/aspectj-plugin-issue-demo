# AspectJ Plugin Issue Demo

Barebones project demonstrating the case where 

`mvn install -pl module-two -am`

followed by invoking either the following inside the module-two directory

`mvn install` 

**OR**

`mvn install -pl module-two`

(i.e. without the -am argument)

This results in the second install lifecycle causing an aspectj recompilation even though none of the sources have strictly changed. This can continue infinitely (i.e. if you then run the first command again after either of the second ones, the first command will trigger an aspectj compilation again and so on). 

## Cause

This is caused by the builddef.lst file produced in the first maven run recording module-one as being at path\to\repo\aspectj-plugin-issue-demo\module-one\target\module-one-1.0-SNAPSHOT.jar, whereas the second has the dependency provided as .m2\org\example\module-one-1.0-SNAPSHOT.jar. Because these two paths differ (even though the underlying jar has nominally the same version), it is flagged as being changed in AbstractAjcCompiler.hasArgumentsChanged().

## Proposed Solution

As an option (I can definitely appreciate not everyone would want this), there should be an option to compare class path entries by the file name excluding the directory structure leading up to it. This would cause jars with the same name (including the version number) to be considered identical, and prevent the compilation occurring if there are no changes. Essentially this would mean that invoking the aspectj-maven-plugin would work consistently regardless of the projects added into the reactor. 