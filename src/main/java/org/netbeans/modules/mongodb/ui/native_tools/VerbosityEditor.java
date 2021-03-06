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
package org.netbeans.modules.mongodb.ui.native_tools;

import java.util.regex.Pattern;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Yann D'Isanto
 */
@Messages({
    "# {0} - verbose arg",
    "verbosityLevelLabel=({0})"
})
public final class VerbosityEditor extends javax.swing.JPanel {

//    private final StringBuilder verboseArgBuilder = new StringBuilder("-");

    private Pattern verboseArgPattern;

    private int maxLevel = 1;

    public VerbosityEditor() {
        this(5);
    }

    /**
     * Creates new form VerbosityPanel
     */
    public VerbosityEditor(int maxLevel) {
        initComponents();
        setMaxLevel(maxLevel);
        verbosityLevelSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
//                verboseArgBuilder.setLength(verbosityLevelSlider.getValue() + 1);
                updateVerbosityLevelLabel();
            }
        });
        setVerboseLevel(1);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        if (maxLevel < 1) {
            throw new IllegalArgumentException("verbose max level must be greater than 1");
        }
        this.maxLevel = maxLevel;
        verboseArgPattern = Pattern.compile("-v{1," + maxLevel + "}");
        final int level = getVerboseLevel();
        setVerboseLevel(level > maxLevel ? maxLevel : level);
        verbosityLevelSlider.setMaximum(maxLevel);
    }

    private void updateVerbosityState() {
        final boolean verbose = verboseCheckBox.isSelected();
        verbosityLevelSlider.setEnabled(verbose);
        verbosityLevelLabel.setEnabled(verbose);
    }

    /**
     * @return true if the verbose checkbox is selected.
     */
    public boolean isVerboseSelected() {
        return verboseCheckBox.isSelected();
    }

    public void setVerboseSelected(boolean selected) {
        verboseCheckBox.setSelected(selected);
        updateVerbosityState();
    }

    /**
     * @return the verbose arg (e.g. "-vv").
     */
    public String getVerboseArg() {
        final StringBuilder verboseArgBuilder = new StringBuilder("-");
        for(int i = 0; i < getVerboseLevel(); i ++) {
            verboseArgBuilder.append('v');
        }
        return verboseArgBuilder.toString();
    }

    public void setVerboseArg(String arg) {
        if (verboseArgPattern.matcher(arg).matches() == false) {
            throw new IllegalArgumentException("invalid verbose arg");
        }
        setVerboseLevel(arg.length() - 1);
    }

    public int getVerboseLevel() {
        return verbosityLevelSlider.getValue();
    }

    public void setVerboseLevel(int level) {
        if (level > maxLevel) {
            throw new IllegalArgumentException("maximum verbose level is " + maxLevel + ": " + level);
        }
        verbosityLevelSlider.setValue(level);
        updateVerbosityLevelLabel();
    }

    private void updateVerbosityLevelLabel() {
        verbosityLevelLabel.setText(Bundle.verbosityLevelLabel(getVerboseArg()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        verboseCheckBox = new javax.swing.JCheckBox();
        verbosityLevelLabel = new javax.swing.JLabel();
        verbosityLevelSlider = new javax.swing.JSlider();

        org.openide.awt.Mnemonics.setLocalizedText(verboseCheckBox, org.openide.util.NbBundle.getMessage(VerbosityEditor.class, "VerbosityEditor.verboseCheckBox.text")); // NOI18N
        verboseCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verboseCheckBoxActionPerformed(evt);
            }
        });

        verbosityLevelLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(verbosityLevelLabel, org.openide.util.NbBundle.getMessage(VerbosityEditor.class, "VerbosityEditor.verbosityLevelLabel.text")); // NOI18N
        verbosityLevelLabel.setEnabled(false);

        verbosityLevelSlider.setMinimum(1);
        verbosityLevelSlider.setMinorTickSpacing(1);
        verbosityLevelSlider.setPaintTicks(true);
        verbosityLevelSlider.setValue(1);
        verbosityLevelSlider.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(verboseCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verbosityLevelLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verbosityLevelSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {verboseCheckBox, verbosityLevelLabel});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(verboseCheckBox)
                .addComponent(verbosityLevelLabel))
            .addComponent(verbosityLevelSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void verboseCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verboseCheckBoxActionPerformed
        updateVerbosityState();
    }//GEN-LAST:event_verboseCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox verboseCheckBox;
    private javax.swing.JLabel verbosityLevelLabel;
    private javax.swing.JSlider verbosityLevelSlider;
    // End of variables declaration//GEN-END:variables
}
