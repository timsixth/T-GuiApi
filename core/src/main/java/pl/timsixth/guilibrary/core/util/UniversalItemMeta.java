package pl.timsixth.guilibrary.core.util;

import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is a wrapper for ItemMeta.
 * It works on 1.13+ versions, from 1.20.5 version localized name doesn't work.
 * This class allow to use localized name or item name on all version
 */
@RequiredArgsConstructor
public class UniversalItemMeta {
    private final ItemMeta meta;

    /**
     * Checks if meta has localized name or item name
     *
     * @return true if localized name or item name is present otherwise false
     */
    public boolean hasLocalizedName() {
        try {
            Method method = meta.getClass().getMethod("hasItemName");
            method.setAccessible(true);

            return (boolean) method.invoke(meta);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return meta.hasLocalizedName();
        }
    }

    /**
     * Sets localized name or item name
     *
     * @param localizeName name to set
     */
    public void setLocalizedName(String localizeName) {
        try {
            Method method = meta.getClass().getMethod("setItemName", String.class);
            method.setAccessible(true);

            method.invoke(meta, localizeName);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            meta.setLocalizedName(localizeName);
        }
    }

    /**
     * Gets localized name or item name
     *
     * @return localized name or item name
     */
    public String getLocalizedName() {
        try {
            Method method = meta.getClass().getMethod("getItemName");
            method.setAccessible(true);

            return (String) method.invoke(meta);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return meta.getLocalizedName();
        }
    }

    /**
     * Builds item meta from this class
     *
     * @return bukkit item meta
     */
    public ItemMeta toItemMeta() {
        return meta;
    }

}
