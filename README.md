## T-GuiApi is a simple library to create custom GUI in minecraft plugins.

Gui is generating from .yml.
This library is using in my plugins.

## Examples
https://github.com/timsixth/T-GuiApi/tree/main/src/main/java/pl/timsixth/gui/libray/example
<br>
https://github.com/timsixth/T-GuiApi/blob/main/src/main/resources/guis.yml

## Getting started

# Maven
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
            <url>https://jitpack.io</url>
	</repository>
</repositories>
  
 <dependency>
     <groupId>com.github.timsixth</groupId>
     <artifactId>T-GuiApi</artifactId>
     <version>Tag</version>
 </dependency>
```
# Gradle
```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
    implementation 'com.github.timsixth:T-GuiApi:Tag'
}
```
# Shading
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.4.1</version>
    <configuration>
        <relocations>
            <relocation>
                <pattern>pl.timsixth.gui.libray</pattern>
                <!-- Be sure to change the package below -->
                <shadedPattern>my.plugin.utils</shadedPattern>
            </relocation>
	 </relocations>
        <filters>
            <filter>
                <artifact>*:*</artifact>
                <excludes>
		     <exclude>pl/timsixth/gui/libray/GuiApiPlugin*</exclude>
		     <exclude>pl/timsixth/gui/libray/example/ConfigFile*</exclude>
		     <exclude>pl/timsixth/gui/libray/example/MenuManager</exclude>
		     <exclude>pl/timsixth/gui/libray/example/TestCommand</exclude>
                </excludes>
            </filter>
        </filters>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
