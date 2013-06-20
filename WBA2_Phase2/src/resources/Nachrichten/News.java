//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.18 um 04:13:55 PM CEST 
//


package resources.Nachrichten;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serienname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}artikel"/>
 *         &lt;element ref="{}titel"/>
 *         &lt;element name="beschreibung" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}bild"/>
 *         &lt;element ref="{}kommentare"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "serienname",
    "artikel",
    "titel",
    "beschreibung",
    "bild",
    "kommentare"
})
@XmlRootElement(name = "news")
public class News {

    @XmlElement(required = true)
    protected String serienname;
    @XmlElement(required = true)
    protected Artikel artikel;
    @XmlElement(required = true)
    protected String titel;
    @XmlElement(required = true)
    protected String beschreibung;
    @XmlElement(required = true)
    protected Bild bild;
    @XmlElement(required = true)
    protected Kommentare kommentare;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Ruft den Wert der serienname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerienname() {
        return serienname;
    }

    /**
     * Legt den Wert der serienname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerienname(String value) {
        this.serienname = value;
    }

    /**
     * Ruft den Wert der artikel-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Artikel }
     *     
     */
    public Artikel getArtikel() {
        return artikel;
    }

    /**
     * Legt den Wert der artikel-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Artikel }
     *     
     */
    public void setArtikel(Artikel value) {
        this.artikel = value;
    }

    /**
     * Ruft den Wert der titel-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitel() {
        return titel;
    }

    /**
     * Legt den Wert der titel-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitel(String value) {
        this.titel = value;
    }

    /**
     * Ruft den Wert der beschreibung-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Legt den Wert der beschreibung-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeschreibung(String value) {
        this.beschreibung = value;
    }

    /**
     * Ruft den Wert der bild-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Bild }
     *     
     */
    public Bild getBild() {
        return bild;
    }

    /**
     * Legt den Wert der bild-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Bild }
     *     
     */
    public void setBild(Bild value) {
        this.bild = value;
    }

    /**
     * Ruft den Wert der kommentare-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Kommentare }
     *     
     */
    public Kommentare getKommentare() {
        return kommentare;
    }

    /**
     * Legt den Wert der kommentare-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Kommentare }
     *     
     */
    public void setKommentare(Kommentare value) {
        this.kommentare = value;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
