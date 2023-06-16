## T-GuiApi is a simple library to create custom GUI in minecraft plugins.

Gui is generating from YAML files.
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
                <shadedPattern>my.plugin.gui</shadedPattern>
            </relocation>
	 </relocations>
        <filters>
            <filter>
                 <artifact>com.github.timsixth:T-GuiApi</artifact>
                   <includes>
                      <include>pl/timsixth/gui/libray/listener/**.class</include>
                      <include>pl/timsixth/gui/libray/model/**.class</include>
                      <include>pl/timsixth/gui/libray/model/action/**.class</include>
                      <include>pl/timsixth/gui/libray/model/action/click/**.class</include>
                      <include>pl/timsixth/gui/libray/model/action/custom/impl/**.class</include>
                      <include>pl/timsixth/gui/libray/model/action/custom/**.class</include>
                      <include>pl/timsixth/gui/libray/manager/**.class</include>
		      <include>pl/timsixth/gui/libray/manager/registration/**.class</include>
                      <include>pl/timsixth/gui/libray/util/**.class</include>
                   </includes>
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
# Configure plugin 
```java
public final class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigFile configFile = new ConfigFile(this);
        ActionRegistration actionRegistration = new ActionRegistrationImpl(); //create new instance of ActionRegistration
        actionRegistration.register(new NoneClickAction(), new SendMessageAction(), new GiveItemsActionImpl()); //register actions
        AbstractMenuManager menuManager = new MenuManager(actionRegistration, configFile);
        menuManager.load(); //load every gui file
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(menuManager), this); //register listener
    } 
```
MenuManager
```java 
//extend AbstractMenuManager
public class MenuManager extends AbstractMenuManager {

    private final ConfigFile configFile; //inject configFile

    public MenuManager(ActionRegistration actionRegistration, ConfigFile configFile) {
        super(actionRegistration);
        this.configFile = configFile;
    }

    @Override
    public void load() {
        load(configFile.getYmlGuis()); //load file with gui inside 
    }
}
```
ConfigFile

```java
@Getter
public final class ConfigFile {

     /*
     Declare files variables
     */
    private final File guisFile;
    private final YamlConfiguration ymlGuis;
    @Getter(AccessLevel.NONE)
    private final GuiApiPlugin guiApiPlugin;

    public ConfigFile(GuiApiPlugin guiApiPlugin){ //inject your main class
        this.guiApiPlugin = guiApiPlugin;

        guisFile = createFile("guis.yml"); //create new File by createFile method

        ymlGuis = YamlConfiguration.loadConfiguration(guisFile); //load YAML configuration, it is important, because this is required to load gui from file 
    }
    
    private File createFile(String name) {
        if (!guiApiPlugin.getDataFolder().exists()) {
            guiApiPlugin.getDataFolder().mkdir();
        }
        File file = new File(guiApiPlugin.getDataFolder(), name);
        if (!file.exists()) {
            guiApiPlugin.saveResource(name, true);
        }
        return file;
    }
}
```
How to open created gui ?

```java
       Player player = (Player) sender; //create player

        Optional<Menu> menuOptional = menuManager.getMenuByName("test"); //inject menuManager and get menu by name

        if (!menuOptional.isPresent()) return true;

        player.openInventory(menuManager.createMenu(menuOptional.get())); //open created menu
```
GUI file
<br>
You can create one gui in one file or more than one gui in single file.

```yaml
guis: #every gui file must have guis section
  test: #gui name
    displayname: '&a&lYours chests' #display name of gui name
    size: 54 #size
    empty_slots: #set empty slot filling 
      id: 7 #id of glass (on 1.8.8 to 1.12.2) is it important but the plugin doesn't support these version. 
      material: STAINED_GLASS_PANE #on 1.13.2 you can use BLACK_STAINED_GLASS_PANE and set id to 0 (Not tested yet)
    slots: #slots section
      22: #slot number
        material: RED_WOOL #material name
        id: 14 #material id
        displayname: '&cList is empty2' #item displayName
        click_action: #click actiom
          type: 'GIVE_ITEMS' #name of action
          args: #args
            - ''
          items: #items top give 
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
