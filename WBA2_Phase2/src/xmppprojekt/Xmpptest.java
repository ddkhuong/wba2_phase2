package xmppprojekt;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import resources.Profil.Filter;
import resources.Profil.Filtercontainer;
import resources.Profil.Profil;
import resources.Profil.Profile;
import resources.Serie.Darsteller;
import resources.Serie.Episode;
import resources.Serie.Staffel;
import restfulwebservice.NachrichtenService;
import restfulwebservice.ProfileService;
import restfulwebservice.SerienService;

public class Xmpptest {

    private static ConnectionConfiguration config = new ConnectionConfiguration("localhost", 5222);
    private static XMPPConnection connection = new XMPPConnection(config);
    private static PubSubManager mgr = new PubSubManager(connection);
    private String username;
    private String password;
    private String profilXmlAlsString;
    private String FeedXmlAlsString;
    private String SerienXmlAlsString;
    private int index;
    private Abos abo;
    private JList addAboList;
    private JList subscriptionsList;
    private JList serienListe;
    private JList darstellerListe;
    private JList episodenListe;
    private JList filterListe;
    private JList serienInFilterListe;
    private JList availableSerieList;
    private JTextField usernameTextfield;
    private JTextField serienNameTextfield;
    private JTextField profilbildTextfeld;
    private JTextField nummerTextField;
    private JTextField nameTextField;
    private JTextField staffelTextField;
    private JPasswordField passwordTextfield;
    private JTextArea seriePayloadTextarea;
    private JTextArea profilBeschreibungTextfeld;
    private JTextArea beschreibungTextArea;
    private ArrayList<PayloadItem<SimplePayload>> notifications;
    private ArrayList<LeafNode> serien = new ArrayList<>();
    private Listener listener = new Listener();
    private AccountManager am;
    private JTabbedPane filterTab1;
    public String serienName;
    public ProfileService profileService = new ProfileService();
    private NachrichtenService nachrichtenService = new NachrichtenService();
    private SerienService serienService = new SerienService();
    DefaultListModel subscribeList = new DefaultListModel();
    DefaultListModel unsubscribeList = new DefaultListModel();
    DefaultListModel serienList = new DefaultListModel();
    DefaultListModel filterList = new DefaultListModel();
    DefaultListModel serienInFilterList = new DefaultListModel();
    DefaultListModel darstellerList = new DefaultListModel();
    DefaultListModel episodenList = new DefaultListModel();
    private Hauptmenue haupt;
    private String aktuellerFilter;

    // altes Menü und start funktion
  /*  public void start() throws XMPPException {

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
			
     }
     } */
    // connect mit dem Server
    // textfelder aus dem loginfenster als übergabewerte um den username und das Password zu erhalten
    public void connect(JTextField usernameTextfield, JPasswordField passwordTextfield) {
        this.usernameTextfield = usernameTextfield;
        this.passwordTextfield = passwordTextfield;
        try {
            username = usernameTextfield.getText();
            password = passwordTextfield.getText();
            connection.connect();
            connection.login(username, password);
            Roster roster = connection.getRoster();  // weiß nicht 
            roster.setSubscriptionMode(SubscriptionMode.accept_all); // weiß nicht
            System.out.println("Hallo " + username + " Sie haben sich erfolgreich verbunden");
            anAlleAltenAnhaengen(); // listener hinzufügen an alle 
        } catch (XMPPException ex) {
            System.out.println("Fehler beim Einloggen");
        }
    }
    //selbsterklärend

    public void disconnect() {
        connection.disconnect();
    }

    // nicht verwendet (sollte im loginfenster passieren wenn button register geklickt wird)
    public void register(JTextField usernameTextfield, JPasswordField passwordTextfield) {
        this.usernameTextfield = usernameTextfield;     //wieder die textfelder aus dem login
        this.passwordTextfield = passwordTextfield;     //wieder die textfelder aus dem login
        try {
            username = usernameTextfield.getText();
            password = passwordTextfield.getPassword().toString();
            am.createAccount(username, password); //create account tut aber irgendwie nicht am ist Account manager
        } catch (XMPPException ex) {
            System.out.println("Fehler beim Registrieren");
        }


    }

    // Abo hinzufügen button aus abos.java wird ausgeführt in aboAddButtonActionPerformed
    public void addAboButton(Abos abo, JList addAboList, JList subscriptionsList) {
        this.abo = abo;         //das abo Jframe (fenster)
        this.addAboList = addAboList;   // die Abo Liste
        this.subscriptionsList = subscriptionsList; //Liste mit den subscriptions

        if (addAboList.isSelectionEmpty()) {        // fehlerabfrage bei nichts ausgewähltem
            System.out.println("Bitte eine Auswahl Treffen");
        } else {
            abo.serienName = addAboList.getSelectedValue().toString();
            serieSubscribe(abo.serienName);     // serie wird abonniert

            refreshLists(addAboList, subscriptionsList);     // abo Liste und subscribe liste aktualisieren mit den hinzugefügten oder gelöschten serien
        }
    }
    //wie oben nur mit delete

    public void aboDeleteButton(Abos abo, JList addAboList, JList subscriptionsList) {
        if (subscriptionsList.isSelectionEmpty()) {
            System.out.println("Bitte eine Auswahlt Treffen");
        } else {
            abo.serienName = subscriptionsList.getSelectedValue().toString();
            serieUnsubscribed(abo.serienName);
            refreshLists(addAboList, subscriptionsList);
        }
    }
    // beide gegebenen listen aktualisieren

    public void refreshLists(JList addAboList, JList subscriptionsList) {
        this.addAboList = addAboList;
        this.subscriptionsList = subscriptionsList;
        subscriptionsList.setModel(refreshSubscibeList());
        addAboList.setModel(refreshUnsubscribeList());
    }
    //serie erstellen mit namen aus gegebenem textfeld und liste in die die serie eingetragen wird

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
            if ("".equals(serienName)) {  // falls nichts eingegeben wurde
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
                serienListe.setModel(refreshSerienList()); // liste aktualisieren

            }
        } catch (XMPPException e) {
            System.err.println("Fehler beim erstellen der Serie");

        }
    }
    //serie löschen  liste aus serie.java

    public void deleteSerie(JList serienList) {
        this.serienListe = serienList;
        if (serienListe.isSelectionEmpty()) {  // falls nichts ausgewählt wurde
            System.out.println("Bitte etwas auswählen");
        } else {
            try {
                serienName = serienListe.getSelectedValue().toString();  // ausgewählten wert bekommen
                mgr.deleteNode(serienName);
                System.out.println(serienName + " wurde gelöscht");
                serienListe.setModel(refreshSerienList());  // serienliste aktualisieren
            } catch (XMPPException e) {
                System.out.println("konnte " + serienName + " nicht löschen");
            }
        }
    }
    // serie publishen liste und payload textare aus serie.java

    public void publishSerie(JList serienList, JTextArea seriePayloadTextarea) {
        this.serienListe = serienList;
        this.seriePayloadTextarea = seriePayloadTextarea;
        LeafNode node;
        String payloadText;
        payloadText = seriePayloadTextarea.getText();   // wert aus payload textarea
        if (serienListe.isSelectionEmpty() || "".equals(payloadText)) { // falls nichts eingegeben oder nichts ausgewählt wurde
            System.out.println("Bitte eine Auswahl Treffen");
        } else {
            try {
                serienName = serienListe.getSelectedValue().toString();  // wert aus liste
                node = mgr.getNode(serienName);
                payloadText = "<publish>" + payloadText + "</payload>";     // damit payload xml entspricht
                SimplePayload p = new SimplePayload("root", "", payloadText);  // payload erstellen
                PayloadItem<SimplePayload> item = new PayloadItem<>(null, p); // man könnte auch = new PayloadItem<SimplePayload>(p);
                node.publish(item);                 // publish
                //node.send(item);                  // send als alternative aber nicht genommen weil synchron?
                System.out.println("Es wurde gepublished ");
            } catch (XMPPException ex) {
                System.out.println("Fehler beim Publishen");

            }
        }
    }
    // überflüssige funktion überbleibsel von einem meilenstein

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
    // gucken ob eine serie abonniert wurde 

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
    // serie abonnieren

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
                node.addItemEventListener(listener);   // eventlistener hinzufügen damti man notifications erhält
                System.out.println("Listerner hinzugefügt");
                node.subscribe(getJID());       // benutzer mit der jid abonniert die node
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
                    node.unsubscribe(getJID());     // jid bekommen um user abonnement zu entfernen
                    node.removeItemEventListener(listener); // listener entfernt
                    System.out.println("Abonnement entfernt");
                } catch (Exception e) {
                    System.out.println("Serie konnte nicht abonniert werden");
                }
            } else {
                System.out.println("Abonnement nicht gefunden");
            }
        } catch (XMPPException ex) {
            System.err.println("fehler");
        }
    }
    // serienliste unsubscribe aktualisieren

    public DefaultListModel refreshUnsubscribeList() {  // default list model ist das model das eine JList benötigt um aktualisiert zu werden
        unsubscribeList.clear();        // erstmal liste leeren
        for (int i = 0; i < getSerienListe().size(); i++) {  // alle serien durchgehen
            String currentSerie = getSerienListe().get(i).getId();      // name der aktuellen serie

            if (!isSerieSubscribed(currentSerie)) {         // falls serie nicht abonniert wurde
                unsubscribeList.addElement(currentSerie);   // wird die rechte liste in abos.java aktualisiert 
            }

        }
        return unsubscribeList;  // serienlist model wird zurückgegeben 
    }

    public DefaultListModel refreshSubscibeList() {  // das gleich wie hier drüber nur mit subscribe list
        subscribeList.clear();
        for (int i = 0; i < getSerienListe().size(); i++) {
            String currentSerie = getSerienListe().get(i).getId();
            if (isSerieSubscribed(currentSerie)) {
                subscribeList.addElement(currentSerie);
            }

        }
        return subscribeList;
    }

    public DefaultListModel refreshSerienInFilterList(JList filterList) {
        serienInFilterList.clear();
        String selectedFilter = "";
        if (filterList.isSelectionEmpty()) {
            try {
                selectedFilter = profileService.getProfil(getUserIndex()).getFiltercontainer().getFilter().get(0).getId();
                aktuellerFilter = selectedFilter;
            } catch (FileNotFoundException | JAXBException ex) {
                System.out.println("Fehler");
            }
        } else {
            selectedFilter = filterList.getSelectedValue().toString();
            aktuellerFilter = selectedFilter;
        }
        for (int i = 0; i < getAnzahlSerienImFilter(selectedFilter); i++) {
            serienInFilterList.addElement(getFilter(selectedFilter).getSerie().get(i));

        }
        return serienInFilterList;
    }

    public DefaultListModel filterListMain(String filterName) {
        serienInFilterList.clear();
        String selectedFilter = filterName;
        System.out.println(selectedFilter);
        for (int i = 0; i < getAnzahlSerienImFilter(selectedFilter); i++) {
            serienInFilterList.addElement(getFilter(selectedFilter).getSerie().get(i));
        }
        System.out.println(serienInFilterList.toString());
        return serienInFilterList;
    }

    // Jede erstellte Serie Anzeigen + Anzahl subscriptions
    public void getSerien() throws XMPPException {
        getSerienListe();   // die liste serien wird aktualisiert 

        for (int i = 0; i < serien.size(); i++) {  // alle serie durchgehen
            LeafNode node = mgr.getNode(serien.get(i).getId());   // aktuelle serie
            int size = node.getSubscriptions().size();          // anzahl der subscriptions
            System.out.println("Serie " + (i + 1) + ": " + serien.get(i).getId());
            System.out.println("Anzahl Subscriptions: " + size);

            if (size > 0) {
                for (int j = 0; j < size; j++) {
                    System.out.println("Abonniert von : " + node.getSubscriptions().get(j).getJid());
                }
            }
        }
    }

    public void anAlleAltenAnhaengen() throws XMPPException {  // listener zu allen hinzufügen
        List<Subscription> list = mgr.getSubscriptions();  // liste mit alles subscriptions
        for (Subscription s : list) {
            mgr.getNode(s.getNode()).addItemEventListener(listener); // zur aktuellen subscription listener hinzufügen
            System.out.println("Listener hinzugefügt zu " + s.getNode());

        }
    }

    public String getJID() {  // jid erzeugen und zurückgeben  
        return username + "@" + "localhost";  // fehlerfreie neue version
        //return connection.getUser()+ "@" connection.getHost(); alte version hat fehler verursacht
    }

    /*   public void itemList() {      // versuch alle items in eine Liste zu schreiben 
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

     }*/
    public ArrayList<LeafNode> getSerienListe() {  // Liste mit serien aktualisieren
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

    public String getFilterName(int index) {  // filter bekommen mit dem profile service
        String filterName = null;
        try {
            filterName = profileService.getAllFilter(getUserIndex()).getFilter().get(index).getId();
        } catch (JAXBException | FileNotFoundException ex) {
            System.out.println("Fehler bei getFilterName");
        }
        return filterName;
    }

    public int getAnzahlFilter() {
        int filterAnzahl = 0;
        try {
            filterAnzahl = profileService.getProfil(getUserIndex()).getFiltercontainer().getFilter().size();
        } catch (JAXBException | FileNotFoundException ex) {
            System.out.println("Fehler bei getFilterAnzahl");
        }
        return filterAnzahl;
    }

    public Filter getFilter(String filterName) {
        Filter fil = null;
        try {
            fil = profileService.getFilter(getUserIndex(), filterName);
        } catch (JAXBException | FileNotFoundException ex) {
            System.out.println("fehler");
        }
        return fil;
    }

    public int getFilterSerienIndex(String filterSerieName, String filterIndex) throws FileNotFoundException, JAXBException {
        return profileService.getFilter(getUserIndex(), filterIndex).getSerie().indexOf(filterSerieName);
    }

    public String getFilterSerienName(int index, String filterIndex) throws FileNotFoundException, JAXBException {
        return profileService.getFilter(getUserIndex(), filterIndex).getSerie().get(index);
    }

    public int getAnzahlSerienImFilter(String filterIndex) { // Anzahl der Serien im Filter mit dem index filterIndex
        int anzahlSerienImFilter = 0;
        try {

            anzahlSerienImFilter = profileService.getFilter(getUserIndex(), filterIndex).getSerie().size();

        } catch (FileNotFoundException | JAXBException ex) {
            System.out.println("Fehler");
        }
        return anzahlSerienImFilter;
    }

    public String getUserIndex() throws FileNotFoundException, JAXBException { //Index vom aktuellen user in der profilXML
        String userIndex = "";  // index des gewünschten users
        int userSize = profileService.getProfile().getProfil().size();
        for (int i = 0; i < userSize; i++) {
            if (username.equals(profileService.getProfile().getProfil().get(i).getUser())) {
                userIndex = profileService.getProfile().getProfil().get(i).getId();
            }
        }
        return userIndex;
    }

    public DefaultListModel refreshFilterList() {  // filter liste aktualisieren profile service schon drin
        filterList.clear();
        try {

            int filterSize;
            int serienImFilter;

            if ("".equals(getUserIndex())) {
                System.out.println("User nicht gefunden");
            } else {
                filterSize = getAnzahlFilter();
                for (int i = 0; i < filterSize; i++) {
                    String filterName = profileService.getProfil(getUserIndex()).getFiltercontainer().getFilter().get(i).getId();
                    filterList.addElement(filterName);
                }
            }

        } catch (JAXBException | FileNotFoundException ex) {
            System.out.println("Fehler beim holen der Profilinfos");
        }
        return filterList;
    }

    /*  public void starteServer(){   wollte neuen rest server starten geht aber nicht weil man multiprocessoring müsste
     try {
     String url = "http://localhost.com:4869";
        
     SelectorThread srv = GrizzlyServerFactory.create(url);
        
     System.out.println("Server wurde gestartet"+ "\n" + "URL: " + url);
     Thread.sleep( 1000 * 60 * 10);
     srv.stopEndpoint();
     System.out.println("Server wurde beendet");
     } catch (IOException | IllegalArgumentException | InterruptedException ex) {
     System.out.println("Fehler bal bla");
     }
     }*/
    public String profilXML() {
        String profilUrl = "http://localhost:4869/profile";
        WebResource wrsProfil = Client.create().resource(profilUrl);
        profilXmlAlsString = wrsProfil.accept(MediaType.APPLICATION_XML).get(String.class);
        return profilXmlAlsString;
    }

    /*
     public DefaultListModel refreshFilterList() {   // filter liste aktualisieren ohne profileService
     filterList.clear();
     try {
     int filterSize;
     int serienImFilter;
     int userNodeIndex = -1;
     int userSize;

     Node userNode;
     Node filterContainerUser;
            
     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
     DocumentBuilder db = dbf.newDocumentBuilder();
     Document xmlDoc = db.parse(new InputSource(new StringReader(profilXML())));
     userSize = xmlDoc.getDocumentElement().getElementsByTagName("user").getLength();
     for (int i = 0; i < userSize; i++) {
     if (username.equals(xmlDoc.getDocumentElement().getElementsByTagName("user").item(i).getTextContent())) {
     userNodeIndex = i;
     } 
     }
            
     if (userNodeIndex != -1) {
     userNode = xmlDoc.getDocumentElement().getElementsByTagName("user").item(userNodeIndex);

     filterContainerUser = userNode.getNextSibling().getNextSibling().getNextSibling();
     filterSize = filterContainerUser.getChildNodes().getLength();
     for (int i = 0; i < filterSize; i++) {
     serienImFilter = filterContainerUser.getChildNodes().item(i).getChildNodes().getLength();
     String filterName = filterContainerUser.getChildNodes().item(i).getAttributes().item(0).getTextContent();
     filterList.addElement(filterName);
     for (int j = 0; j < serienImFilter; j++) {
     String currentSerie = filterContainerUser.getChildNodes().item(i).getChildNodes().item(j).getTextContent();
     // filterList.addElement(currentSerie);
     System.out.println(currentSerie);
     }
     }
     } else {
     System.out.println("User hat keine Abos");
     }

     } catch (ParserConfigurationException | SAXException | IOException ex) {
     System.out.println("Fehler");
     }
     return filterList;
     }  */
    //Listener
    public class Listener implements ItemEventListener<Item> {  // was der listener macht

        @Override
        public void handlePublishedItems(ItemPublishEvent<Item> event) {
            System.out.println("Neues Item ist da!");   // hier muss noch was hin damit die items angezeigt werden

        }
    }

    public void deleteFilter(JList filterList) {
        this.filterListe = filterList;
        if (filterListe.isSelectionEmpty()) {
            System.out.println("Bitte einen Filter auswählen");
        } else {
            try {
                String selectedFilter = filterListe.getSelectedValue().toString();
                String profil_id = getUserIndex();
                if ("".equals(getUserIndex())) {
                    System.out.println("User nicht gefunden");
                } else {
                    profileService.deleteFilter(profil_id, selectedFilter);
                }
            } catch (FileNotFoundException | JAXBException ex) {
                System.out.println("Fehler beim Löschen");
            }
        }
    }

    public void deleteSerieFromFilter(JList serienInFilterList) {
        try {
            this.serienInFilterListe = serienInFilterList;
            String profil_id = getUserIndex(); //checked
            if (serienInFilterList.isSelectionEmpty()) {
                System.out.println("Bitte erst etwas auswählen");
            } else {
                String selectedSerie = serienInFilterListe.getSelectedValue().toString();
                profileService.deleteSerieInFilter(profil_id,aktuellerFilter, selectedSerie);
            }
        } catch (FileNotFoundException | JAXBException ex) {
            System.out.println("Fehler");
        }
    }

    public void addSerieToFilter(JList availableSerieList) {
        //availableSerieList profileService.postSerieInFilter(profil_id,filter_id,serie);
    this.availableSerieList = availableSerieList;
        if(availableSerieList.isSelectionEmpty()){
            System.out.println("Bitte eine Auswahl treffen");
        }else
        {
    String selectedSerie = availableSerieList.getSelectedValue().toString();
        try {
            //System.out.println(getUserIndex()+" "+aktuellerFilter+" "+selectedSerie);
           profileService.postSerieInFilter(getUserIndex(),aktuellerFilter,selectedSerie);
        } catch (FileNotFoundException | JAXBException ex) {
            System.out.println("Fehler beim adden");
        }
    }
    }

    public void addEpisode(JList serienList, JTextField nummerTextField, JTextField nameTextField, JTextField staffelTextField, JTextArea beschreibungTextArea) {
        this.serienListe = serienList;
        this.nummerTextField = nummerTextField;
        this.nameTextField = nameTextField;
        this.staffelTextField = staffelTextField;
        this.beschreibungTextArea = beschreibungTextArea;
        if (serienListe.isSelectionEmpty()) {
            System.out.println("Fehler Bitte eine Serie auswählen");
        } else {
            if (staffelTextField.getText().isEmpty() | nameTextField.getText().isEmpty() | nummerTextField.getText().isEmpty()) {
                System.out.println("Bitte alle Informationen eingeben");
            } else {

                float nummer = Float.valueOf(nummerTextField.getText().toString());
                String episodenBeschreibung = beschreibungTextArea.getText().toString();
                String staffelIndex = staffelTextField.getText();
                String sName = serienListe.getSelectedValue().toString();
                String episodenName = nameTextField.getText().toString();
                System.out.println("Nummer: " + nummer + " Beschreibung: " + episodenBeschreibung);
                System.out.println("Name:" + episodenName + " SerienName: " + sName + " index" + staffelIndex);
                // serienService.getSerie(sName).getAlleStaffeln().getStaffel().get(staffelIndex).getEpisode().
                Episode e = new Episode();
                e.setEpisodenbeschreibung(episodenBeschreibung);
                e.setName(episodenName);
                e.setNummer(nummer);
                try {
                    serienService.postEpisode(sName, staffelIndex, e);
                } catch (JAXBException | FileNotFoundException ex) {
                    System.out.println("Fehler beim adden der Serie");
                }

            }
        }
    }
    //ab hier alles zur Serien Info Seite

    public DefaultListModel refreshSerienList() {  // serienliste aktualisieren
        serienList.clear();
        for (int i = 0; i < getSerienListe().size(); i++) {

            serienList.addElement(getSerienListe().get(i).getId());
        }
        return serienList;
    }

    public String selectedSerie(JList serienList) {
        this.serienListe = serienList;
        String selectedSerie;
        if (serienListe.isSelectionEmpty()) {

            System.out.println("Bitte Serie Auswählen");
            return "";
        } else {
            selectedSerie = serienListe.getSelectedValue().toString();
            return selectedSerie;
        }
    }

    public String selectedDarsteller(JList darstellerList) {
        this.darstellerListe = darstellerList;
        String selectedDarsteller;
        if (darstellerListe.isSelectionEmpty()) {

            System.out.println("Bitte Serie Auswählen");
            return "";
        } else {
            selectedDarsteller = darstellerListe.getSelectedValue().toString();
            return selectedDarsteller;
        }
    }

    public String selectedEpisode(JList episodenList) {
        this.episodenListe = episodenList;
        String selectedEpisode;
        if (episodenListe.isSelectionEmpty()) {
            System.out.println("Bitte Serie Auswählen");
            return "";
        } else {
            selectedEpisode = episodenListe.getSelectedValue().toString();
            System.out.println(selectedEpisode);
            return selectedEpisode;
        }
    }

    public String getSerienBeschreibung(String selectedSerie) {
        if ("".equals(selectedSerie)) {
            return "-Keine Beschreibung-";
        } else {
            try {
                return serienService.getSerie(selectedSerie).getBeschreibung();
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler");
            }
        }
        return "-Keine Beschreibung-2";
    }

    public String getEpisodenBeschreibung(String selectedSerie, JList episodenListe) {
        this.episodenListe = episodenListe;
        if ("".equals(selectedSerie)) {
            return "-Keine Beschreibung-";
        } else {


            String selectedEpisode = selectedEpisode(episodenListe);
            if ("".equals(selectedEpisode)) {
                System.out.println("jo" + selectedEpisode);
                return "-Keine Beschreibung-3";
            } else {
                try {
                    int indexEpisode = indexOfSelectedEpisode(selectedEpisode, selectedSerie);
                    int indexStaffel = indexOfSelectedEpisode(selectedEpisode, selectedSerie);

                    if (indexEpisode == -1 | indexStaffel == -1) {
                        System.out.println("fehler");
                        return "-keine Beschreibung-";
                    } else {
                        return serienService.getSerie(selectedSerie).getAlleStaffeln().getStaffel().get(indexStaffel).getEpisode().get(indexEpisode).getEpisodenbeschreibung();
                    }
                } catch (JAXBException | FileNotFoundException ex) {
                    System.out.println("Fehler");
                }


            }
        }
        return "-Keine Beschreibung-2";
    }

    public int indexOfSelectedEpisode(String selectedEpisode, String selectedSerie) {
        int x = -1;
        for (int i = 0; i < getEpisodenList(selectedSerie).size(); i++) {
            for (int j = 0; j < getStaffelList(selectedSerie).size(); j++) {
                try {
                    if (selectedEpisode.equals(serienService.getSerie(serienName).getAlleStaffeln().getStaffel().get(j).getEpisode().get(i).getName())) {
                        System.out.println(selectedEpisode);
                        System.out.println(serienService.getSerie(serienName).getAlleStaffeln().getStaffel().get(j).getEpisode().get(i).getName());
                        x = i;
                    }
                } catch (JAXBException | FileNotFoundException ex) {
                    System.out.println("Fehler");
                }

            }
        }
        return x;
    }

    public int indexOfSelectedStaffel(String selectedStaffel, String selectedSerie) {
        int x = -1;

        for (int j = 0; j < getStaffelList(selectedSerie).size(); j++) {
            try {
                if (selectedStaffel.equals(serienService.getSerie(serienName).getAlleStaffeln().getStaffel().get(j).getId())) {
                    x = j;
                }
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler");
            }

        }
        return x;
    }

    public int indexOfSelectedDarsteler(String selectedDarsteller, String selectedSerie) {
        int j = -1;
        for (int i = 0; i < getDarstellerList(selectedSerie).size(); i++) {
            try {
                if (selectedDarsteller.equals(serienService.getCast(selectedSerie).getDarsteller().get(i).getName())) {
                    j = i;
                }
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler");
            }
        }
        return j;
    }

    public String getDarstellerBeschreibung(String selectedDarsteller, String selectedSerie) {
        if ("".equals(selectedDarsteller)) {
            return "-Keine Beschreibung-";
        } else {
            try {
                return serienService.getCast(selectedSerie).getDarsteller().get(indexOfSelectedDarsteler(selectedDarsteller, selectedSerie)).getBeschreibung();
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler");
                return "-Fehlerhaft";
            }
        }
    }

    public String getBildUrl(String selectedDarsteller, String selectedSerie) {
        try {
            String bildUrl;
            int indexOfDarsteller = indexOfSelectedDarsteler(selectedDarsteller, selectedSerie);
            if (indexOfDarsteller == -1) {
                return "-keine Url-";
            } else {
                bildUrl = serienService.getCast(selectedSerie).getDarsteller().get(indexOfDarsteller).getBild().getUrl();
                return bildUrl;
            }
        } catch (JAXBException | FileNotFoundException ex) {
            System.out.println("Fehler");
            return "-keine Url-";
        }
    }

    public List<Episode> getEpisodenList(String selectedSerie) {
        List<Episode> episoden1;
        List<Episode> episoden = null;
        if ("".equals(selectedSerie)) {
            return episoden;
        } else {
            try {
                episoden1 = serienService.getStaffeln(selectedSerie).getStaffel().get(0).getEpisode();
                episoden = serienService.getStaffeln(selectedSerie).getStaffel().get(1).getEpisode();
                episoden.addAll(episoden1);
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler");
            }
        }
        return episoden;
    }

    public List<Darsteller> getDarstellerList(String selectedSerie) {
        List<Darsteller> dars = null;
        if ("".equals(selectedSerie)) {
            return dars;
        } else {
            try {

                dars = serienService.getSerie(selectedSerie).getCast().getDarsteller();
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Konnte Darsteller nicht erhalten");
            }
        }
        return dars;
    }

    public List<Staffel> getStaffelList(String selectedSerie) {
        List<Staffel> staffel = null;
        if ("".equals(selectedSerie)) {
            return staffel;
        } else {
            try {

                staffel = serienService.getSerie(selectedSerie).getAlleStaffeln().getStaffel();
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Konnte Staffel nicht erhalten");
            }
        }
        return staffel;
    }

    public DefaultListModel refreshEpisodenList() {
        episodenList.clear();
        String selectedSerie = selectedSerie(serienListe);
        if ("".equals(selectedSerie)) {
            return episodenList;
        } else {
            for (int i = 0; i < getEpisodenList(selectedSerie).size(); i++) {  // alle serien durchgehen
                float currentEpisodeint = getEpisodenList(selectedSerie).get(i).getNummer();
                String currentEpisode = String.valueOf(currentEpisodeint) + ": " + getEpisodenList(selectedSerie).get(i).getName();
                //String currentEpisode = getEpisodenList(selectedSerie).get(i).getName();  Nur Name ohne die Nummer davor
                episodenList.addElement(currentEpisode);
            }
            return episodenList;  // serienlist model wird zurückgegeben 
        }

    }

    public DefaultListModel refreshDarstellerList() {  // default list model ist das model das eine JList benötigt um aktualisiert zu werden
        darstellerList.clear();        // erstmal liste leeren
        String selectedSerie = selectedSerie(serienListe);
        if ("".equals(selectedSerie)) {
            return darstellerList;
        } else {
            for (int i = 0; i < getDarstellerList(selectedSerie).size(); i++) {  // alle serien durchgehen
                String currentDarsteller = getDarstellerList(selectedSerie).get(i).getName();     // name der aktuellen serie
                darstellerList.addElement(currentDarsteller);
            }
            return darstellerList;  // serienlist model wird zurückgegeben 
        }
    }

    // Ab hier profil bearbeiten
    /**
     *
     * @return
     */
    public String getProfilBeschreibung() {
        String userId = "";
        String userBeschreibung = "-keine Beschreibung-";
        try {
            userId = getUserIndex();
        } catch (FileNotFoundException | JAXBException ex) {
            System.out.println("Fehler beim abrufen des User Indexes");
        }

        if ("".equals(userId)) {
            System.out.println(username + " hat kein Profil. Fehler");

        } else {
            try {
                userBeschreibung = profileService.getProfil(userId).getBeschreibung();

            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler beim abrufen der User Informationen");
            }
        }
        return userBeschreibung;
    }

    public String getProfilbild() {
        String profilbild = "-kein Profilbild-";
        String userId = "";
        try {
            userId = getUserIndex();
        } catch (FileNotFoundException | JAXBException ex) {
            System.out.println("Fehler beim abrufen des User Indexes");
        }
        if ("".equals(userId)) {
            System.out.println(username + " hat kein Profil. Fehler");
        } else {
            try {
                profilbild = profileService.getProfil(userId).getProfilbild();
            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler beim abrufen des Profilbildes");
            }
        }

        return profilbild;
    }

    public void setProfil(JTextArea profilBeschreibungTextfeld, JTextField profilbildTextfeld) {
        this.profilBeschreibungTextfeld = profilBeschreibungTextfeld;
        this.profilbildTextfeld = profilbildTextfeld;
        String userId = "";
        String profilBeschreibung;
        String profilbild;
        try {
            userId = getUserIndex();
        } catch (FileNotFoundException | JAXBException ex) {
            System.out.println("Fehler beim abrufen des User Indexes");
        }
        if ("".equals(userId)) {
            System.out.println(username + " hat kein Profil. Fehler");
        } else {
            try {
                profilBeschreibung = profilBeschreibungTextfeld.getText();
                profilbild = profilbildTextfeld.getText();
                System.out.println(profilbild + " " + profilBeschreibung);
                Profil p = profileService.getProfil(userId);
                p.setBeschreibung(profilBeschreibung);
                p.setProfilbild(profilbild);
                profileService.putProfil(userId, p);

            } catch (JAXBException | FileNotFoundException ex) {
                System.out.println("Fehler beim Setzten der neuen Informationen");
            }

        }
    }
}
