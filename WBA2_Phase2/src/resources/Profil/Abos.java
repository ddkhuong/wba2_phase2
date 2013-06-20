//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.19 um 03:35:06 PM CEST 
//


package resources.Profil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}serien"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "serien"
})
@XmlRootElement(name = "abos")
public class Abos {

    @XmlElement(required = true)
    protected Serien serien;

    /**
     * Ruft den Wert der serien-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Serien }
     *     
     */
    public Serien getSerien() {
        return serien;
    }

    /**
     * Legt den Wert der serien-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Serien }
     *     
     */
    public void setSerien(Serien value) {
        this.serien = value;
    }

}
