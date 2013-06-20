//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.20 um 11:52:29 AM CEST 
//


package resources.Serie;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the resources.Serie package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Genre_QNAME = new QName("", "genre");
    private final static QName _Beschreibung_QNAME = new QName("", "beschreibung");
    private final static QName _Name_QNAME = new QName("", "name");
    private final static QName _EpisodeEpisodenbeschreibung_QNAME = new QName("", "episodenbeschreibung");
    private final static QName _EpisodeNummer_QNAME = new QName("", "nummer");
    private final static QName _EpisodeDatum_QNAME = new QName("", "datum");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: resources.Serie
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Darsteller }
     * 
     */
    public Darsteller createDarsteller() {
        return new Darsteller();
    }

    /**
     * Create an instance of {@link Serie }
     * 
     */
    public Serie createSerie() {
        return new Serie();
    }

    /**
     * Create an instance of {@link AlleStaffeln }
     * 
     */
    public AlleStaffeln createAlleStaffeln() {
        return new AlleStaffeln();
    }

    /**
     * Create an instance of {@link Staffel }
     * 
     */
    public Staffel createStaffel() {
        return new Staffel();
    }

    /**
     * Create an instance of {@link Episode }
     * 
     */
    public Episode createEpisode() {
        return new Episode();
    }

    /**
     * Create an instance of {@link Cast }
     * 
     */
    public Cast createCast() {
        return new Cast();
    }

    /**
     * Create an instance of {@link Darsteller.Bild }
     * 
     */
    public Darsteller.Bild createDarstellerBild() {
        return new Darsteller.Bild();
    }

    /**
     * Create an instance of {@link Serien }
     * 
     */
    public Serien createSerien() {
        return new Serien();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "genre")
    public JAXBElement<String> createGenre(String value) {
        return new JAXBElement<String>(_Genre_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "beschreibung")
    public JAXBElement<String> createBeschreibung(String value) {
        return new JAXBElement<String>(_Beschreibung_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "episodenbeschreibung", scope = Episode.class)
    public JAXBElement<String> createEpisodeEpisodenbeschreibung(String value) {
        return new JAXBElement<String>(_EpisodeEpisodenbeschreibung_QNAME, String.class, Episode.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name", scope = Episode.class)
    public JAXBElement<String> createEpisodeName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, Episode.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nummer", scope = Episode.class)
    public JAXBElement<Float> createEpisodeNummer(Float value) {
        return new JAXBElement<Float>(_EpisodeNummer_QNAME, Float.class, Episode.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "datum", scope = Episode.class)
    public JAXBElement<XMLGregorianCalendar> createEpisodeDatum(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_EpisodeDatum_QNAME, XMLGregorianCalendar.class, Episode.class, value);
    }

}