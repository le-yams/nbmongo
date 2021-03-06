/* 
 * Copyright (C) 2015 Yann D'Isanto
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.netbeans.modules.mongodb.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.Getter;
import org.openide.nodes.PropertySupport;

/**
 * Read-Only property that uses localized strings from the bundle file.
 */
class LocalizedProperty<T> extends PropertySupport.ReadOnly<T> {

    @Getter
    private final T value;

    public LocalizedProperty(ResourceBundle bundle, String prefix, String propertyName, Class<T> propertyType, T value) {
        super(
            safeResource(bundle, nameKey(prefix, propertyName), propertyName),
            propertyType,
            safeResource(bundle, dislayNameKey(prefix, propertyName), propertyName),
            safeResource(bundle, shortDescriptionKey(prefix, propertyName), propertyName)
        );
        this.value = value;
    }
    
    private static String safeResource(ResourceBundle bundle, String key, String fallback) {
        try {
            return bundle.getString(key);
        } catch(MissingResourceException ex) {
            return fallback;
        }
    }
    
    private static String nameKey(String prefix, String propertyName) {
        return buildKey(prefix, propertyName, "name");
    }

    private static String dislayNameKey(String prefix, String propertyName) {
        return buildKey(prefix, propertyName, "displayname");
    }

    private static String shortDescriptionKey(String prefix, String propertyName) {
        return buildKey(prefix, propertyName, "shortdesc");
    }
    
    private static String buildKey(String prefix, String propertyName, String key) {
        return new StringBuilder(prefix)
            .append('.')
            .append(propertyName)
            .append('.')
            .append(key)
            .toString();
    }
}
