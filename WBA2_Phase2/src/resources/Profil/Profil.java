//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.19 um 03:35:06 PM CEST 
//


package resources.Profil;

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
 *         &lt;element ref="{}user"/>
 *         &lt;element name="profilbild" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}beschreibung"/>
 *         &lt;element ref="{}filtercontainer"/>
 *         &lt;element ref="{}abos"/>
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
    "user",
    "profilbild",
    "beschreibung",
    "filtercontainer",
    "abos"
})
@XmlRootElement(name = "profil")
public class Profil {

    @XmlElement(required = true)
    protected String user;
    @XmlElement(required = true)
    protected String profilbild;
    @XmlElement(required = true)
    protected String beschreibung;
    @XmlElement(required = true)
    protected Filtercontainer filtercontainer;
    @XmlElement(required = true)
    protected Abos abos;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Ruft den Wert der user-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Legt den Wert der user-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Ruft den Wert der profilbild-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfilbild() {
        return profilbild;
    }

    /**
     * Legt den Wert der profilbild-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfilbild(String value) {
        this.profilbild = value;
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
     * Ruft den Wert der filtercontainer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Filtercontainer }
     *     
     */
    public Filtercontainer getFiltercontainer() {
        return filtercontainer;
    }

    /**
     * Legt den Wert der filtercontainer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Filtercontainer }
     *     
     */
    public void setFiltercontainer(Filtercontainer value) {
        this.filtercontainer = value;
    }

    /**
     * Ruft den Wert der abos-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Abos }
     *     
     */
    public Abos getAbos() {
        return abos;
    }

    /**
     * Legt den Wert der abos-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Abos }
     *     
     */
    public void setAbos(Abos value) {
        this.abos = value;
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
