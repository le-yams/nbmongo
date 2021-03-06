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
package org.netbeans.modules.mongodb.ui.components.result_panel.actions;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.netbeans.modules.mongodb.CollectionInfo;
import org.netbeans.modules.mongodb.resources.Images;
import org.netbeans.modules.mongodb.ui.components.CollectionResultPanel;
import org.netbeans.modules.mongodb.ui.wizards.ExportWizardAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Yann D'Isanto
 */
@Messages({
    "ACTION_exportQueryResult=Export Query Result",
    "ACTION_exportQueryResult_tooltip=Export Query Result"
})
public final class ExportQueryResultAction extends QueryResultPanelAction {

    private static final long serialVersionUID = 1L;

    public ExportQueryResultAction(CollectionResultPanel resultPanel) {
        super(resultPanel,
                Bundle.ACTION_exportQueryResult(),
                new ImageIcon(Images.EXPORT_COLLECTION_ICON),
                Bundle.ACTION_exportQueryResult_tooltip());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        REQUEST_PROCESSOR.execute(new Runnable() {

            @Override
            public void run() {
                Lookup lookup = getResultPanel().getLookup();
                CollectionInfo collectionInfo = lookup.lookup(CollectionInfo.class);
                Map<String, Object> properties = new HashMap<>();
                properties.put(ExportWizardAction.PROP_COLLECTION, collectionInfo.getName());
                properties.put(ExportWizardAction.PROP_DOCUMENTS, getResultPanel().getCurrentResult());
                new ExportWizardAction(lookup, properties).actionPerformed(null);
            }
        });
    }

}
