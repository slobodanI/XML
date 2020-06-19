//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.19 at 11:54:21 AM CEST 
//


package rs.xml.agent.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Oglas complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Oglas"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mesto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="marka" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="gorivo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="menjac" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klasa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cena" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="kilometraza" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="planiranaKilometraza" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="sedistaZaDecu" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="osiguranje" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="odDate" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="doDate" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="slike" type="{http://xml.rs/oglas/xsd}Slika" maxOccurs="3" minOccurs="3"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Oglas", propOrder = {
    "oid",
    "mesto",
    "marka",
    "model",
    "gorivo",
    "menjac",
    "klasa",
    "cena",
    "kilometraza",
    "planiranaKilometraza",
    "sedistaZaDecu",
    "osiguranje",
    "username",
    "odDate",
    "doDate",
    "deleted",
    "slike"
})
public class Oglas {

    @XmlElement(required = true)
    protected String oid;
    @XmlElement(required = true)
    protected String mesto;
    @XmlElement(required = true)
    protected String marka;
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected String gorivo;
    @XmlElement(required = true)
    protected String menjac;
    @XmlElement(required = true)
    protected String klasa;
    protected int cena;
    protected int kilometraza;
    protected int planiranaKilometraza;
    protected int sedistaZaDecu;
    protected boolean osiguranje;
    @XmlElement(required = true)
    protected String username;
    protected long odDate;
    protected long doDate;
    protected boolean deleted;
    @XmlElement(required = true)
    protected List<Slika> slike;

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
     * Gets the value of the mesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMesto() {
        return mesto;
    }

    /**
     * Sets the value of the mesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMesto(String value) {
        this.mesto = value;
    }

    /**
     * Gets the value of the marka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarka() {
        return marka;
    }

    /**
     * Sets the value of the marka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarka(String value) {
        this.marka = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the gorivo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGorivo() {
        return gorivo;
    }

    /**
     * Sets the value of the gorivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGorivo(String value) {
        this.gorivo = value;
    }

    /**
     * Gets the value of the menjac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenjac() {
        return menjac;
    }

    /**
     * Sets the value of the menjac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenjac(String value) {
        this.menjac = value;
    }

    /**
     * Gets the value of the klasa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlasa() {
        return klasa;
    }

    /**
     * Sets the value of the klasa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlasa(String value) {
        this.klasa = value;
    }

    /**
     * Gets the value of the cena property.
     * 
     */
    public int getCena() {
        return cena;
    }

    /**
     * Sets the value of the cena property.
     * 
     */
    public void setCena(int value) {
        this.cena = value;
    }

    /**
     * Gets the value of the kilometraza property.
     * 
     */
    public int getKilometraza() {
        return kilometraza;
    }

    /**
     * Sets the value of the kilometraza property.
     * 
     */
    public void setKilometraza(int value) {
        this.kilometraza = value;
    }

    /**
     * Gets the value of the planiranaKilometraza property.
     * 
     */
    public int getPlaniranaKilometraza() {
        return planiranaKilometraza;
    }

    /**
     * Sets the value of the planiranaKilometraza property.
     * 
     */
    public void setPlaniranaKilometraza(int value) {
        this.planiranaKilometraza = value;
    }

    /**
     * Gets the value of the sedistaZaDecu property.
     * 
     */
    public int getSedistaZaDecu() {
        return sedistaZaDecu;
    }

    /**
     * Sets the value of the sedistaZaDecu property.
     * 
     */
    public void setSedistaZaDecu(int value) {
        this.sedistaZaDecu = value;
    }

    /**
     * Gets the value of the osiguranje property.
     * 
     */
    public boolean isOsiguranje() {
        return osiguranje;
    }

    /**
     * Sets the value of the osiguranje property.
     * 
     */
    public void setOsiguranje(boolean value) {
        this.osiguranje = value;
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

    /**
     * Gets the value of the slike property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the slike property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSlike().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Slika }
     * 
     * 
     */
    public List<Slika> getSlike() {
        if (slike == null) {
            slike = new ArrayList<Slika>();
        }
        return this.slike;
    }

}
