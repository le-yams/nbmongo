/*
 * Copyright (C) 2016 Yann D'Isanto
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
package org.netbeans.modules.mongodb.preferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import lombok.AllArgsConstructor;
import org.netbeans.modules.mongodb.NBMongo;
import org.netbeans.modules.mongodb.preferences.migrations.V1ToV2;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;

/**
 *
 * @author Yann D'Isanto
 */
public final class Migrations {

    private static final Map<String, Prefs.Version> VERSIONS;

    static {
        VERSIONS = new HashMap<>();
        VERSIONS.put("8.1.0", Prefs.Version.V1);
        VERSIONS.put("8.1.1", Prefs.Version.V1);
        VERSIONS.put("8.1.2", Prefs.Version.V1);
        VERSIONS.put("8.1.3", Prefs.Version.V1);
        VERSIONS.put("8.1.4", Prefs.Version.V1);
        VERSIONS.put("8.2.0", Prefs.Version.V1);
        VERSIONS.put("8.3.0", Prefs.Version.V2);
    }

    private static final Prefs.Version EXPECTED_PREFS_VERSION = VERSIONS.get(NBMongo.moduleInfo().getImplementationVersion());

    static final Migration V1_TO_V2 = new V1ToV2(null);

    public static final Migration DEFAULT = new Migration(EXPECTED_PREFS_VERSION, null) {
        @Override
        protected void performMigration(Preferences prefs) throws Exception {
            // DO NOTHING
            // It will simply update the preferences 'version' property.
        }
    };

    private static final Map<Prefs.Version, Migration> MIGRATIONS;

    static {
        MIGRATIONS = new HashMap<>();
        MIGRATIONS.put(Prefs.Version.UNDEFINED, V1_TO_V2);
        MIGRATIONS.put(Prefs.Version.V1, V1_TO_V2);
        MIGRATIONS.put(Prefs.Version.V2, DEFAULT);
    }

    public static void migrateIfNecessary() {
        if (EXPECTED_PREFS_VERSION == null) {
            return;
        };
        Prefs.Version actualPrefsVersion = NBMongo.prefs().version();
        if (actualPrefsVersion == EXPECTED_PREFS_VERSION) {
            return;
        }
        MIGRATIONS.get(actualPrefsVersion).run();
    }

    @AllArgsConstructor
    public static abstract class Migration implements Runnable {

        private final Prefs.Version version;

        private final Migration next;

        @Override
        public void run() {
            try {
                Preferences prefs = NBMongo.prefs().root();
                performMigration(prefs);
                prefs.put("version", version.name());
                if (next != null) {
                    next.run();
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        protected abstract void performMigration(Preferences prefs) throws Exception;

        protected static void migrateString(String key, Preferences source, Preferences target) {
            migrateString(key, key, source, target);
        }
        
        protected static void migrateString(String oldKey, String newKey, Preferences source, Preferences target) {
            String value = source.get(oldKey, null);
            if(value != null) {
                target.put(newKey, value);
            }
        }
    }

    // TEST
    static {
        
    }
}
