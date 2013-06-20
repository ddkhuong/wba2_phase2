package xmppprojekt;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

public class Xmpptest {

    private static ConnectionConfiguration config = new ConnectionConfiguration("localhost", 5222);
    private static XMPPConnection connection = new XMPPConnection(config);
    private static PubSubManager mgr = new PubSubManager(connection);
    private String username;
    private String password;
    private Abos abo;
    private JList addAboList;
    private JList subscriptionsList;
    private JList serienListe;
    private JTextField usernameTextfield;
    private JTextField serienNameTextfield;
    private JPasswordField passwordTextfield;
    private JTextArea seriePayloadTextarea;
    private ArrayList<PayloadItem<SimplePayload>> notifications;
    private ArrayList<LeafNode> serien = new ArrayList<>();
    private Listener listener = new Listener();
    private AccountManager am;
    public String serienName;
    DefaultListModel subscribeList = new DefaultListModel();
    DefaultListModel unsubscribeList = new DefaultListModel();
    DefaultListModel serienList = new DefaultListModel();

    public void start() throws XMPPException {

        System.out.println("Hallo " + connection.getUser().substring(0, connection.getUser().indexOf("@")) + " Sie haben sich erfolgreich verbunden");
        anAlleAltenAnhaengen();
        //hauptmenue(serienName);


        System.out.println("disconnected");

    }

    private void hauptmenue(String serienName) throws XMPPException {
        /*
         Scanner in = new Scanner(System.in);
         int teste=0;
        
         int wahl=0;
		
         System.out.println("1 Serie Erstellen");
         System.out.println("2 Serien Infos");
         System.out.println("3 Serie Abonnieren");
         System.out.println("4 Abo Kündigen");
         System.out.println("5 Delete Serie");
         System.out.println("6 Publish");
         System.out.println("7 Ende");
         wahl = in.nextInt();
		 
         switch(wahl){
         case 1:
         System.out.println("Serie Erstellen");
         createSerie(serienName);
         hauptmenue(serienName);
         break;
         case 2:
         System.out.println("Serien Infos");
         getLeafInfo(serienName); 
         getSerien();
         hauptmenue(serienName);
         break;
         case 3: 
         System.out.println("Serie Abonnieren");
         serieSubscribe(serienName);
         hauptmenue(serienName);
         break;
         case 4:
         System.out.println("Abo Kündigen");
         serieUnsubscribed(serienName);
         hauptmenue(serienName);
         break;
         case 5:
         deleteSerie(serienName);
         hauptmenue(serienName);
         break;
         case 6:
         publishSerie(serienName);
         hauptmenue(serienName);
         break;
         case 7:
         System.out.println("Bis Bald");
         break;
         default:
         System.out.println("Falsche Eingabe"); 
         hauptmenue(serienName);
         break;
			
         }*/
    }

    public void connect(JTextField usernameTextfield, JPasswordField passwordTextfield) {
        this.usernameTextfield = usernameTextfield;
        this.passwordTextfield = passwordTextfield;
        try {
            username = usernameTextfield.getText();
            password = passwordTextfield.getText();
            connection.connect();
            connection.login(username, password);
            Roster roster = connection.getRoster();
            roster.setSubscriptionMode(SubscriptionMode.accept_all);
            System.out.println("Hallo " + username + " Sie haben sich erfolgreich verbunden");
            anAlleAltenAnhaengen();
        } catch (XMPPException ex) {
            System.out.println("Fehler beim Einloggen");
        }
    }

    public void disconnect() {
        connection.disconnect();
    }
    public void register(JTextField usernameTextfield, JPasswordField passwordTextfield){
        this.usernameTextfield = usernameTextfield;
        this.passwordTextfield = passwordTextfield;
        try {
            username = usernameTextfield.getText();
            password = passwordTextfield.getPassword().toString();
            am.createAccount(username, password);          
        } catch (XMPPException ex) {
            System.out.println("Fehler beim Registrieren");
        }
        
        
    }

    public void addAboButton(Abos abo, JList addAboList, JList subscriptionsList) {
        this.abo = abo;
        this.addAboList = addAboList;
        this.subscriptionsList = subscriptionsList;

        if (addAboList.isSelectionEmpty()) {
            System.out.println("Bitte eine Auswahl Treffen");
        } else {
            abo.serienName = addAboList.getSelectedValue().toString();
            serieSubscribe(abo.serienName);

            refreshLists(addAboList, subscriptionsList);
        }
    }

    public void aboDeleteButton(Abos abo, JList addAboList, JList subscriptionsList) {
        if (subscriptionsList.isSelectionEmpty()) {
            System.out.println("Bitte eine Auswahlt Treffen");
        } else {
            abo.serienName = subscriptionsList.getSelectedValue().toString();
            serieUnsubscribed(abo.serienName);
            refreshLists(addAboList, subscriptionsList);
        }
    }

    public void refreshLists(JList addAboList, JList subscriptionsList) {
        this.addAboList = addAboList;
        this.subscriptionsList = subscriptionsList;
        subscriptionsList.setModel(refreshSubscibeList());
        addAboList.setModel(refreshUnsubscribeList());
    }

    public void createSerie(JTextField serienNameTextfield, JList serienList) {
        this.serienListe = serienList;
        this.serienNameTextfield = serienNameTextfield;
        serienName = serienNameTextfield.getText();
        try {
            mgr.getNode(serienName);
            System.err.println("Serie existiert bereits.");
        } catch (XMPPException e) {
        }


        try {
            if ("".equals(serienName)) {
                System.out.println("Bitte Seriennamen eingeben");
            } else {
                ConfigureForm form = new ConfigureForm(FormType.submit);
                form.setAccessModel(AccessModel.open);
                form.setPublishModel(PublishModel.open);
                form.setDeliverPayloads(true);
                form.setPersistentItems(true);
                form.setPresenceBasedDelivery(false);
                form.setSubscribe(true);
                LeafNode node = (LeafNode) mgr.createNode(serienName, form);
                serien.add(node);
                serienListe.setModel(refreshSerienList());
            }
        } catch (XMPPException e) {
            System.err.println("Fehler beim erstellen der Serie");

        }
    }

    public void deleteSerie(JList serienList) {
        this.serienListe = serienList;
        if (serienListe.isSelectionEmpty()) {
            System.out.println("Bitte etwas auswählen");
        } else {
            try {
                serienName = serienListe.getSelectedValue().toString();
                mgr.deleteNode(serienName);
                System.out.println(serienName + " wurde gelöscht");
                serienListe.setModel(refreshSerienList());
            } catch (XMPPException e) {
                System.out.println("konnte " + serienName + " nicht löschen");
            }
        }
    }

    public void publishSerie(JList serienList, JTextArea seriePayloadTextarea) {
        this.serienListe = serienList;
        this.seriePayloadTextarea = seriePayloadTextarea;
        LeafNode node;
        String payloadText;
        payloadText = seriePayloadTextarea.getText();
        if (serienListe.isSelectionEmpty() || "".equals(payloadText)) {
            System.out.println("Bitte eine Auswahl Treffen");
        } else {
            try {
                serienName = serienListe.getSelectedValue().toString();
                node = mgr.getNode(serienName);
                SimplePayload p = new SimplePayload("root", "", payloadText);
                PayloadItem<SimplePayload> item = new PayloadItem<>(null,p); // man könnte auch = new PayloadItem<SimplePayload>(p);
                node.publish(item);
                //node.send(item);
                System.out.println("Es wurde gepublished ");
            } catch (XMPPException ex) {
                System.out.println("Fehler beim Publishen");
                
            }
        }
    }

    public void getLeafInfo(String serienName) throws XMPPException {
        ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(connection);
        DiscoverItems discoItems = discoManager.discoverItems("pubsub.localhost");
        java.util.Iterator<org.jivesoftware.smackx.packet.DiscoverItems.Item> it = discoItems.getItems();
        while (it.hasNext()) {
            DiscoverItems.Item item = (DiscoverItems.Item) it.next();
            System.out.println(item.getEntityID());
            System.out.println(item.getNode());
            System.out.println(item.getName());

        }

    }

    public boolean isSerieSubscribed(String serienName) {
        try {
            LeafNode node = mgr.getNode(serienName);
            List<Subscription> subscriptions = node.getSubscriptions();
            for (Subscription s : subscriptions) {
                if (s.getJid().equals(getJID())) {
                    return true;
                }
            }

        } catch (XMPPException ex) {
            System.out.println("Fehler Bei isSerieSubscribed");
        }
        return false;
    }

    public void serieSubscribe(String serienName) {
        LeafNode node = null;
        try {
            node = (LeafNode) mgr.getNode(serienName);
        } catch (XMPPException ex) {
            System.out.println("Fehler beim Holen der Serie");
        }

        if (isSerieSubscribed(serienName)) {
            System.out.println("Serie bereits abonniert");
        } else {
            try {
                node.addItemEventListener(listener);
                System.out.println("Listerner hinzugefügt");
                node.subscribe(getJID());
                System.out.println(serienName + " abonniert");
            } catch (Exception e) {
                System.out.println("Serie konnte nicht abonniert werden");
            }
        }
    }

    public void serieUnsubscribed(String serienName) {
        try {
            LeafNode node = (LeafNode) mgr.getNode(serienName);
            if (isSerieSubscribed(serienName)) {
                try {
                    node.unsubscribe(getJID());
                    node.removeItemEventListener(listener);
                    System.out.println("Abonnement entfernt");
                } catch (Exception e) {
                    System.out.println("Serie konnte nicht abonniert werden");
                }
            } else {
                System.out.println("Abonnement nicht gefunden");
            }
        } catch (XMPPException ex) {
            Logger.getLogger(Xmpptest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultListModel refreshUnsubscribeList() {
        unsubscribeList.clear();
        for (int i = 0; i < getSerienListe().size(); i++) {
            String currentSerie = getSerienListe().get(i).getId();

            if (!isSerieSubscribed(currentSerie)) {
                unsubscribeList.addElement(currentSerie);
            }

        }
        return unsubscribeList;
    }

    public DefaultListModel refreshSubscibeList() {
        subscribeList.clear();
        for (int i = 0; i < getSerienListe().size(); i++) {
            String currentSerie = getSerienListe().get(i).getId();
            if (isSerieSubscribed(currentSerie)) {
                subscribeList.addElement(currentSerie);
            }

        }
        return subscribeList;
    }

    public DefaultListModel refreshSerienList() {
        serienList.clear();
        for (int i = 0; i < getSerienListe().size(); i++) {

            serienList.addElement(getSerienListe().get(i).getId());
        }
        return serienList;
    }

    // Jede erstellte Serie Anzeigen + Anzahl subscriptions
    public void getSerien() throws XMPPException {
        getSerienListe();

        for (int i = 0; i < serien.size(); i++) {
            LeafNode node = mgr.getNode(serien.get(i).getId());
            int size = node.getSubscriptions().size();
            System.out.println("Serie " + (i + 1) + ": " + serien.get(i).getId());
            System.out.println("Anzahl Subscriptions: " + size);

            if (size > 0) {
                for (int j = 0; j < size; j++) {
                    System.out.println("Abonniert von : " + node.getSubscriptions().get(j).getJid());
                }
            }
        }
    }

    public void anAlleAltenAnhaengen() throws XMPPException {
        List<Subscription> list = mgr.getSubscriptions();
        for (Subscription s : list) {
            mgr.getNode(s.getNode()).addItemEventListener(listener);
            System.out.println("Listener hinzugefügt zu " + s.getNode());

        }
    }

    public String getJID() {
        return username + "@" + "localhost";
    }

    public void itemList() {
        try {
            LeafNode node = mgr.getNode("Dexter");
            List<? extends Item> ite = node.getItems();
            if (ite.isEmpty()) {
                System.out.println("Liste Leer");
            } else {
                System.out.println(ite.size());
                System.out.println(ite.get(1));
            }
        } catch (XMPPException ex) {
            System.out.println("Fehler bei item List");
        }

    }

    public ArrayList<LeafNode> getSerienListe() {
        serien.clear();
        ServiceDiscoveryManager disco_manager = ServiceDiscoveryManager.getInstanceFor(connection);
        DiscoverItems discoItems;
        try {
            discoItems = disco_manager.discoverItems("pubsub.localhost");
            java.util.Iterator<org.jivesoftware.smackx.packet.DiscoverItems.Item> it = discoItems.getItems();
            while (it.hasNext()) {
                DiscoverItems.Item item = (DiscoverItems.Item) it.next();
                try {
                    serien.add((LeafNode) mgr.getNode(item.getNode()));
                } catch (XMPPException e) {
                    System.err.println("Fehler beim Hinzufügen.");
                }
            }
        } catch (XMPPException e1) {
            System.err.println("Konnte Liste von Serien nicht holen.");

        }
        return serien;
    }

    public class Listener implements ItemEventListener<Item> {

        @Override
        public void handlePublishedItems(ItemPublishEvent<Item> event) {
            System.out.println("Neues Item ist da!");

        }
    }
}
