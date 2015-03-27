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
package org.netbeans.modules.mongodb.ui.windows.queryresultpanel.actions;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.awt.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;
import org.jdesktop.swingx.treetable.TreeTableNode;
import org.netbeans.modules.mongodb.ui.util.JsonPropertyEditor;
import org.netbeans.modules.mongodb.ui.windows.QueryResultPanel;
import org.netbeans.modules.mongodb.ui.windows.collectionview.treetable.DocumentNode;
import org.netbeans.modules.mongodb.ui.windows.collectionview.treetable.JsonPropertyNode;
import org.netbeans.modules.mongodb.util.JsonProperty;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Yann D'Isanto
 */
@Messages({
    "editJsonPropertyTitle=Edit json property",
    "ACTION_editJsonProperty=Edit json property",
    "ACTION_editJsonProperty_tooltip=Edit Selected JSON Property"
})
public final class EditJsonPropertyNodeAction extends QueryResultPanelAction {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private JsonPropertyNode propertyNode;

    public EditJsonPropertyNodeAction(QueryResultPanel resultPanel, JsonPropertyNode propertyNode) {
        super(resultPanel,
            Bundle.ACTION_editJsonProperty(),
            null,
            Bundle.ACTION_editJsonProperty_tooltip());
        this.propertyNode = propertyNode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JsonProperty property = propertyNode.getUserObject();
        JsonProperty newProperty = JsonPropertyEditor.show(property);
        if (newProperty == null) {
            return;
        }
        getResultPanel().getTreeTableModel().setUserObject(propertyNode, newProperty);
        TreeTableNode parentNode = propertyNode.getParent();
        DBObject parent = (DBObject) parentNode.getUserObject();
        if (newProperty.getName().equals(property.getName()) == false) {
            parent.removeField(property.getName());
        }
        parent.put(newProperty.getName(), newProperty.getValue());
        while ((parentNode instanceof DocumentNode) == false) {
            parentNode = parentNode.getParent();
        }
        try {
            final DBCollection dbCollection = getResultPanel().getLookup().lookup(DBCollection.class);
            dbCollection.save((DBObject) parentNode.getUserObject());
        } catch (MongoException ex) {
            DialogDisplayer.getDefault().notify(
                new NotifyDescriptor.Message(ex.getLocalizedMessage(), NotifyDescriptor.ERROR_MESSAGE));
        }
    }
}