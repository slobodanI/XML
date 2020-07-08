//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.08 at 01:10:43 PM CEST 
//


package rs.xml.agent.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Zahtev complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Zahtev"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="zid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="status" type="{http://xml.rs/oglas/xsd}ZahtevStatus"/&gt;
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="podnosilacUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="odDate" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="doDate" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="vremePodnosenja" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="ocenjen" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="izvestaj" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="chatId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oglasi" type="{http://xml.rs/oglas/xsd}OglasUZahtevu" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Zahtev", propOrder = {
    "zid",
    "status",
    "username",
    "podnosilacUsername",
    "odDate",
    "doDate",
    "vremePodnosenja",
    "ocenjen",
    "izvestaj",
    "chatId",
    "oglasi"
})
public class Zahtev {

    @XmlElement(required = true)
    protected String zid;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ZahtevStatus status;
    @XmlElement(required = true)
    protected String username;
    @XmlElement(required = true)
    protected String podnosilacUsername;
    protected long odDate;
    protected long doDate;
    protected long vremePodnosenja;
    protected boolean ocenjen;
    protected boolean izvestaj;
    @XmlElement(required = true)
    protected String chatId;
    @XmlElement(required = true)
    protected List<OglasUZahtevu> oglasi;

    /**
     * Gets the value of the zid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZid() {
        return zid;
    }

    /**
     * Sets the value of the zid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZid(String value) {
        this.zid = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ZahtevStatus }
     *     
     */
    public ZahtevStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZahtevStatus }
     *     
     */
    public void setStatus(ZahtevStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the podnosilacUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPodnosilacUsername() {
        return podnosilacUsername;
    }

    /**
     * Sets the value of the podnosilacUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPodnosilacUsername(String value) {
        this.podnosilacUsername = value;
    }

    /**
     * Gets the value of the odDate property.
     * 
     */
    public long getOdDate() {
        return odDate;
    }

    /**
     * Sets the value of the odDate property.
     * 
     */
    public void setOdDate(long value) {
        this.odDate = value;
    }

    /**
     * Gets the value of the doDate property.
     * 
     */
    public long getDoDate() {
        return doDate;
    }

    /**
     * Sets the value of the doDate property.
     * 
     */
    public void setDoDate(long value) {
        this.doDate = value;
    }

    /**
     * Gets the value of the vremePodnosenja property.
     * 
     */
    public long getVremePodnosenja() {
        return vremePodnosenja;
    }

    /**
     * Sets the value of the vremePodnosenja property.
     * 
     */
    public void setVremePodnosenja(long value) {
        this.vremePodnosenja = value;
    }

    /**
     * Gets the value of the ocenjen property.
     * 
     */
    public boolean isOcenjen() {
        return ocenjen;
    }

    /**
     * Sets the value of the ocenjen property.
     * 
     */
    public void setOcenjen(boolean value) {
        this.ocenjen = value;
    }

    /**
     * Gets the value of the izvestaj property.
     * 
     */
    public boolean isIzvestaj() {
        return izvestaj;
    }

    /**
     * Sets the value of the izvestaj property.
     * 
     */
    public void setIzvestaj(boolean value) {
        this.izvestaj = value;
    }

    /**
     * Gets the value of the chatId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChatId() {
        return chatId;
    }

    /**
     * Sets the value of the chatId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChatId(String value) {
        this.chatId = value;
    }

    /**
     * Gets the value of the oglasi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oglasi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOglasi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OglasUZahtevu }
     * 
     * 
     */
    public List<OglasUZahtevu> getOglasi() {
        if (oglasi == null) {
            oglasi = new ArrayList<OglasUZahtevu>();
        }
        return this.oglasi;
    }

}
