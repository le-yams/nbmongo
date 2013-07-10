/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timboudreau.netbeans.mongodb;

import com.mongodb.MongoClient;
import static com.timboudreau.netbeans.mongodb.MongoServicesNode.MONGO_ICON;
import java.awt.event.ActionEvent;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;

/**
 * The services tab node for MongoDB connections.
 *
 * @author Tim Boudreau
 */
@ServicesTabNodeRegistration(displayName = "MongoNodeName", iconResource = MONGO_ICON, position = 3, name = "mongodb")
@Messages("MongoNodeName=Mongo DB")
public class MongoServicesNode extends AbstractNode {

    @StaticResource
    public static final String MONGO_ICON
            = "com/timboudreau/netbeans/mongodb/mongo-small.png"; //NOI18N
    @StaticResource
    public static final String MONGO_DB
            = "com/timboudreau/netbeans/mongodb/mongo-db.png"; //NOI18N
    @StaticResource
    public static final String MONGO_COLLECTION
            = "com/timboudreau/netbeans/mongodb/mongo-collection.png"; //NOI18N
    @StaticResource
    public static final String MONGO_CONNECTION
            = "com/timboudreau/netbeans/mongodb/mongo-connection.png"; //NOI18N
    @StaticResource
    public static final String MONGO_ITEM
            = "com/timboudreau/netbeans/mongodb/mongo-item.png"; //NOI18N

    private final ConnectionChildFactory factory;

    public MongoServicesNode() {
        this(new ConnectionChildFactory());
    }

    MongoServicesNode(ConnectionChildFactory factory) {
        super(Children.create(factory, false));
        this.factory = factory;
        setDisplayName(Bundle.MongoNodeName());
        setIconBaseWithExtension(MONGO_ICON);
    }

    static {
        // By default every connection exception will result in a
        // popup dialog.  Try to crank down the volume.
        Logger mongoLogger = Logger.getLogger("com.mongodb");
        mongoLogger.setLevel(Level.INFO);
        mongoLogger.setFilter(new Filter() {

            @Override
            public boolean isLoggable(LogRecord record) {
                record.setLevel(Level.FINE);
                return false;
            }
        });
    }

    static Preferences prefs() {
        return NbPreferences.forModule(MongoServicesNode.class).node("connections"); //NOI18N
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new NewConnectionAction()};
    }

    @Messages("LBL_newConnection=New Connection")
    private class NewConnectionAction extends AbstractAction {

        public NewConnectionAction() {
            super(Bundle.LBL_newConnection());
        }

        @Override
        @Messages("TTL_newConnection=New MongoDB Connection")
        public void actionPerformed(ActionEvent e) {
            final NewConnectionPanel pnl = new NewConnectionPanel();
            final DialogDescriptor desc = new DialogDescriptor(pnl, Bundle.TTL_newConnection());

            pnl.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    desc.setValid(pnl.isOk());
                }
            });
            Object dlgResult = DialogDisplayer.getDefault().notify(desc);
            System.out.println("DLG RESULT " + dlgResult);
            if (dlgResult.equals(NotifyDescriptor.OK_OPTION)) {
                String host = pnl.getHost();
                int port = pnl.getPort();
                String name = pnl.getConnectionName();
                Preferences prefs = prefs();
                try (ConnectionInfo info = new ConnectionInfo(prefs)) {
                    if (!name.isEmpty()) {
                        info.setDisplayName(name);
                    }
                    info.setPort(port);
                    info.setHost(host);
                    System.out.println("CREATE " + info);
                } finally {
                    factory.doRefresh();
                }
            }
        }
    }
}
