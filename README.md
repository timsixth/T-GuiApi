## T-GuiApi is a simple library to create custom GUI in minecraft plugins.

Gui is generating from YAML files.
This library is using in my plugins.

## Examples
https://github.com/timsixth/T-GuiApi/tree/main/example-plugin/src/main/java/pl/timsixth/guilibrary/example<br>
https://github.com/timsixth/T-GuiApi/blob/main/example-plugin/src/main/resources/guis.yml
## Features
* Generating GUI's from YAML files.
* Two options of GUI storage - InMemory (Default) or InYAML
* Process system (You can open GUI, next open another GUI, your data haven't gone , you can transfer them to next GUI. This system can be used in plugin which you need configuration in GUI for user friendly)

## Getting started

Maven
```xml
<repositories>
   <repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
   </repository>
</repositories>
  
 <dependency>
   <groupId>com.github.timsixth.T-GuiApi</groupId>
   <artifactId>core</artifactId> <!-- Adds main library. Type processes to add processes to your plugin -->
   <version>{VERSION}</version>
 </dependency>
```

Gradle
```gradle
allprojects {
    repositories {
	maven { url 'https://jitpack.io' }
   }
}

dependencies {
    implementation 'com.github.timsixth:T-GuiApi:{VERSION}'
}
```

Shading
```xml
 <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.3.0</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <relocations>
                                <relocation>
                                    <pattern>pl.timsixth.guilibrary.core</pattern>
                                    <shadedPattern>{YOUR_PACKAGE}.guilibrary.core</shadedPattern>
                                </relocation>
                            </relocations>
                            <filters>
                                <filter>
                                    <artifact>>com.github.timsixth.T-GuiApi:core</artifact>
                                    <excludeDefaults>false</excludeDefaults>
                                    <includes>
                                        <include>{YOUR_PACKAGE}.guilibrary.core</include>
                                    </includes>
                                </filter>
                            </filters>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

GUI file
<br>
You can create one gui in one file or more than one gui in single file.

```yaml
guis: #every gui file must have guis section
  test: #gui name, must be unique 
    displayname: '&a&lYours chests' #display name of gui name
    size: 54 #size
    empty_slots: #set empty slot filling 
      material: BLACK_STAINED_GLASS_PANE #material name
    slots: #slots section
      22: #slot number
        material: RED_WOOL #material name
        displayname: '&cList is empty2' #item displayName
        click_action: #click actiom
          type: 'GIVE_ITEMS' #name of action
          args: #args
            - ''
          items: #items to give 
            DIAMOND: #material name 
              amount: 1 #amount
      21:
        material: DIAMOND
        displayname: '&cList is empty'
        click_action:
          type: 'SEND_MESSAGE'
          args:
            - '&aKOKOS' #message to send
```
