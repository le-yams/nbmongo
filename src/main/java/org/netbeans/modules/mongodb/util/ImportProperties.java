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
package org.netbeans.modules.mongodb.util;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
@Builder
public final class ImportProperties {
    
    private final String collection;
    
    private final boolean drop;

    private final File file;
    
    private final Charset encoding;

    public ImportProperties(String collection, boolean drop, File file, Charset encoding) {
        this.collection = Objects.requireNonNull(collection);
        this.drop = drop;
        this.file = Objects.requireNonNull(file);
        this.encoding = Objects.requireNonNull(encoding);
    }
}
