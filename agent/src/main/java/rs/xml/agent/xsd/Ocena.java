//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.08 at 11:09:32 AM CEST 
//


package rs.xml.agent.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Ocena complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ocena"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ocena" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="komentar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="approved" type="{http://xml.rs/oglas/xsd}OcenaApprovedStatus"/&gt;
 *         &lt;element name="odgovor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="usernameKo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="usernameKoga" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oglasId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="zahtevId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ocena", propOrder = {
    "oid",
    "ocena",
    "komentar",
    "approved",
    "odgovor",
    "usernameKo",
    "usernameKoga",
    "oglasId",
    "zahtevId",
    "deleted"
})
public class Ocena {

    @XmlElement(required = true)
    protected String oid;
    protected int ocena;
    @XmlElement(required = true)
    protected String komentar;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OcenaApprovedStatus approved;
    @XmlElement(required = true)
    protected String odgovor;
    @XmlElement(required = true)
    protected String usernameKo;
    @XmlElement(required = true)
    protected String usernameKoga;
    @XmlElement(required = true)
    protected String oglasId;
    @XmlElement(required = true)
    protected String zahtevId;
    protected boolean deleted;

    /**
     * Gets the value of the oid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOid() {
        return oid;
    }

    /**
     * Sets the value of the oid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOid(String value) {
        this.oid = value;
    }

    /**
     * Gets the value of the ocena property.
     * 
     */
    public int getOcena() {
        return ocena;
    }

    /**
     * Sets the value of the ocena property.
     * 
     */
    public void setOcena(int value) {
        this.ocena = value;
    }

    /**
     * Gets the value of the komentar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKomentar() {
        return komentar;
    }

    /**
     * Sets the value of the komentar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKomentar(String value) {
        this.komentar = value;
    }

    /**
     * Gets the value of the approved property.
     * 
     * @return
     *     possible object is
     *     {@link OcenaApprovedStatus }
     *     
     */
    public OcenaApprovedStatus getApproved() {
        return approved;
    }

    /**
     * Sets the value of the approved property.
     * 
     * @param value
     *     allowed object is
     *     {@link OcenaApprovedStatus }
     *     
     */
    public void setApproved(OcenaApprovedStatus value) {
        this.approved = value;
    }

    /**
     * Gets the value of the odgovor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOdgovor() {
        return odgovor;
    }

    /**
     * Sets the value of the odgovor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOdgovor(String value) {
        this.odgovor = value;
    }

    /**
     * Gets the value of the usernameKo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsernameKo() {
        return usernameKo;
    }

    /**
     * Sets the value of the usernameKo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsernameKo(String value) {
        this.usernameKo = value;
    }

    /**
     * Gets the value of the usernameKoga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsernameKoga() {
        return usernameKoga;
    }

    /**
     * Sets the value of the usernameKoga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsernameKoga(String value) {
        this.usernameKoga = value;
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
     * Gets the value of the deleted property.
     * 
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the value of the deleted property.
     * 
     */
    public void setDeleted(boolean value) {
        this.deleted = value;
    }

}
