//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.20 at 03:05:01 PM CEST 
//


package rs.xml.chat.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Izvestaj complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Izvestaj"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="iid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oglasId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="zahtevId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="predjeniKilometri" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Izvestaj", propOrder = {
    "iid",
    "oglasId",
    "zahtevId",
    "predjeniKilometri",
    "tekst"
})
public class Izvestaj {

    @XmlElement(required = true)
    protected String iid;
    @XmlElement(required = true)
    protected String oglasId;
    @XmlElement(required = true)
    protected String zahtevId;
    protected int predjeniKilometri;
    @XmlElement(required = true)
    protected String tekst;

    /**
     * Gets the value of the iid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIid() {
        return iid;
    }

    /**
     * Sets the value of the iid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIid(String value) {
        this.iid = value;
    }

    /**
     * Gets the value of the oglasId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOglasId() {
        return oglasId;
    }

    /**
     * Sets the value of the oglasId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOglasId(String value) {
        this.oglasId = value;
    }

    /**
     * Gets the value of the zahtevId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZahtevId() {
        return zahtevId;
    }

    /**
     * Sets the value of the zahtevId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZahtevId(String value) {
        this.zahtevId = value;
    }

    /**
     * Gets the value of the predjeniKilometri property.
     * 
     */
    public int getPredjeniKilometri() {
        return predjeniKilometri;
    }

    /**
     * Sets the value of the predjeniKilometri property.
     * 
     */
    public void setPredjeniKilometri(int value) {
        this.predjeniKilometri = value;
    }

    /**
     * Gets the value of the tekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTekst() {
        return tekst;
    }

    /**
     * Sets the value of the tekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTekst(String value) {
        this.tekst = value;
    }

}
