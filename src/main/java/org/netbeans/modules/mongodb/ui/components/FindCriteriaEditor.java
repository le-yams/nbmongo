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
package org.netbeans.modules.mongodb.ui.components;

import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import lombok.Getter;
import org.bson.BsonDocument;
import org.bson.json.JsonParseException;
import org.netbeans.modules.mongodb.api.FindCriteria;
import org.netbeans.modules.mongodb.bson.Bsons;
import org.netbeans.modules.mongodb.ui.util.DialogNotification;
import org.netbeans.modules.mongodb.ui.util.JsonUIUtils;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

/**
 *
 * @author Yann D'Isanto
 */
@NbBundle.Messages({
    "invalidFilterJson=invalid filter json",
    "invalidProjectionJson=invalid projection json",
    "invalidSortJson=invalid sort json"})
public final class FindCriteriaEditor extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;

    private JDialog dialog;

    private int dialogResult;

    @Getter
    private FindCriteria findCriteria;

    public FindCriteriaEditor() {
        this(FindCriteria.EMPTY);
    }

    /**
     * Creates new form QueryPanel
     */
    public FindCriteriaEditor(FindCriteria findCriteria) {
        this.findCriteria = findCriteria;
        initComponents();
        JsonUIUtils.setJsonEditorKit(filterEditor);
        JsonUIUtils.setJsonEditorKit(projectionEditor);
        JsonUIUtils.setJsonEditorKit(sortEditor);
    }

    public BsonDocument parseFilter() throws JsonParseException {
        return getEditorValue(filterEditor);
    }

    public void setFindCriteria(FindCriteria findCriteria) {
        setEditorValue(filterEditor, findCriteria.getFilter());
        setEditorValue(projectionEditor, findCriteria.getProjection());
        setEditorValue(sortEditor, findCriteria.getSort());
        this.findCriteria = findCriteria;
    }

    public BsonDocument parseProjection() throws JsonParseException {
        return getEditorValue(projectionEditor);
    }

    public BsonDocument parseSort() throws JsonParseException {
        return getEditorValue(sortEditor);
    }

    private BsonDocument getEditorValue(JEditorPane editor) throws JsonParseException {
        final String json = editor.getText().trim();
        return BsonDocument.parse(json);
    }

    private void setEditorValue(JEditorPane editor, BsonDocument value) {
        editor.setText(Bsons.shellAndPretty(value != null ? value : new BsonDocument()));
    }

    public boolean validateInput() {
        try {
            parseFilter();
        } catch (JsonParseException ex) {
            DialogNotification.error(Bundle.invalidFilterJson());
            return false;
        }
        try {
            parseProjection();
        } catch (JsonParseException ex) {
            DialogNotification.error(Bundle.invalidProjectionJson());
            return false;
        }
        try {
            parseSort();
        } catch (JsonParseException ex) {
            DialogNotification.error(Bundle.invalidSortJson());
            return false;
        }
        return true;
    }

    private void refreshInput() {
        setEditorValue(filterEditor, findCriteria.getFilter());
        setEditorValue(projectionEditor, findCriteria.getProjection());
        setEditorValue(sortEditor, findCriteria.getSort());
    }

    public boolean showDialog() {
        final Frame owner = WindowManager.getDefault().getMainWindow();
        dialog = new JDialog(owner, "Edit query", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        refreshInput();
        dialogResult = JOptionPane.CLOSED_OPTION;
        dialog.setVisible(true);
        if (dialogResult == JOptionPane.OK_OPTION) {
            findCriteria = FindCriteria.builder()
                    .filter(parseFilter())
                    .projection(parseProjection())
                    .sort(parseSort())
                    .build();
            return true;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogPanel = new javax.swing.JPanel();
        panel = this;
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        splitPane1 = new javax.swing.JSplitPane();
        filterPanel = new javax.swing.JPanel();
        filterLabel = new javax.swing.JLabel();
        filterScrollPane = new javax.swing.JScrollPane();
        filterEditor = new javax.swing.JEditorPane();
        splitPane2 = new javax.swing.JSplitPane();
        projectionPanel = new javax.swing.JPanel();
        projectionLabel = new javax.swing.JLabel();
        projectionScrollPane = new javax.swing.JScrollPane();
        projectionEditor = new javax.swing.JEditorPane();
        sortPanel = new javax.swing.JPanel();
        sortLabel = new javax.swing.JLabel();
        sortScrollPane = new javax.swing.JScrollPane();
        sortEditor = new javax.swing.JEditorPane();

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, org.openide.util.NbBundle.getMessage(FindCriteriaEditor.class, "FindCriteriaEditor.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(okButton, org.openide.util.NbBundle.getMessage(FindCriteriaEditor.class, "FindCriteriaEditor.okButton.text")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogPanelLayout = new javax.swing.GroupLayout(dialogPanel);
        dialogPanel.setLayout(dialogPanelLayout);
        dialogPanelLayout.setHorizontalGroup(
            dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogPanelLayout.createSequentialGroup()
                        .addGap(0, 119, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        dialogPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        dialogPanelLayout.setVerticalGroup(
            dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        splitPane1.setDividerLocation(150);
        splitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitPane1.setResizeWeight(0.5);

        org.openide.awt.Mnemonics.setLocalizedText(filterLabel, org.openide.util.NbBundle.getMessage(FindCriteriaEditor.class, "FindCriteriaEditor.filterLabel.text")); // NOI18N

        filterScrollPane.setViewportView(filterEditor);

        javax.swing.GroupLayout filterPanelLayout = new javax.swing.GroupLayout(filterPanel);
        filterPanel.setLayout(filterPanelLayout);
        filterPanelLayout.setHorizontalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filterPanelLayout.createSequentialGroup()
                        .addComponent(filterLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(filterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
                .addContainerGap())
        );
        filterPanelLayout.setVerticalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPane1.setTopComponent(filterPanel);

        splitPane2.setDividerLocation(200);
        splitPane2.setResizeWeight(0.5);

        org.openide.awt.Mnemonics.setLocalizedText(projectionLabel, org.openide.util.NbBundle.getMessage(FindCriteriaEditor.class, "FindCriteriaEditor.projectionLabel.text")); // NOI18N

        projectionScrollPane.setViewportView(projectionEditor);

        javax.swing.GroupLayout projectionPanelLayout = new javax.swing.GroupLayout(projectionPanel);
        projectionPanel.setLayout(projectionPanelLayout);
        projectionPanelLayout.setHorizontalGroup(
            projectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectionScrollPane)
                    .addGroup(projectionPanelLayout.createSequentialGroup()
                        .addComponent(projectionLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        projectionPanelLayout.setVerticalGroup(
            projectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPane2.setLeftComponent(projectionPanel);

        org.openide.awt.Mnemonics.setLocalizedText(sortLabel, org.openide.util.NbBundle.getMessage(FindCriteriaEditor.class, "FindCriteriaEditor.sortLabel.text")); // NOI18N

        sortScrollPane.setViewportView(sortEditor);

        javax.swing.GroupLayout sortPanelLayout = new javax.swing.GroupLayout(sortPanel);
        sortPanel.setLayout(sortPanelLayout);
        sortPanelLayout.setHorizontalGroup(
            sortPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sortPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sortPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addGroup(sortPanelLayout.createSequentialGroup()
                        .addComponent(sortLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sortPanelLayout.setVerticalGroup(
            sortPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sortPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sortLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sortScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitPane2.setRightComponent(sortPanel);

        splitPane1.setRightComponent(splitPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (validateInput()) {
            dialogResult = JOptionPane.OK_OPTION;
            dialog.dispose();
        }
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dialogResult = JOptionPane.CANCEL_OPTION;
        dialog.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel dialogPanel;
    private javax.swing.JEditorPane filterEditor;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JScrollPane filterScrollPane;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel panel;
    private javax.swing.JEditorPane projectionEditor;
    private javax.swing.JLabel projectionLabel;
    private javax.swing.JPanel projectionPanel;
    private javax.swing.JScrollPane projectionScrollPane;
    private javax.swing.JEditorPane sortEditor;
    private javax.swing.JLabel sortLabel;
    private javax.swing.JPanel sortPanel;
    private javax.swing.JScrollPane sortScrollPane;
    private javax.swing.JSplitPane splitPane1;
    private javax.swing.JSplitPane splitPane2;
    // End of variables declaration//GEN-END:variables
}
