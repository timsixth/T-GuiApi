## T-GuiApi is a simple library to create custom GUI in minecraft plugins.

Gui is generating from .yml.
This library is using in my plugins.

## Examples
https://github.com/timsixth/T-GuiApi/tree/main/src/main/java/pl/timsixth/gui/libray/example
<br>
https://github.com/timsixth/T-GuiApi/blob/main/src/main/resources/guis.yml

## Getting started

# Maven
```maven
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
			...
			maven { url 'https://jitpack.io' }
		}
	}

dependencies {
	        implementation 'com.github.timsixth:T-GuiApi:Tag'
}
```
