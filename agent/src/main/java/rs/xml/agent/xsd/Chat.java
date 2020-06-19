//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.19 at 11:54:21 AM CEST 
//


package rs.xml.agent.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Chat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Chat"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="senderUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="receiverUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="poruke" type="{http://xml.rs/oglas/xsd}Poruka"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Chat", propOrder = {
    "cid",
    "senderUsername",
    "receiverUsername",
    "poruke"
})
public class Chat {

    @XmlElement(required = true)
    protected String cid;
    @XmlElement(required = true)
    protected String senderUsername;
    @XmlElement(required = true)
    protected String receiverUsername;
    @XmlElement(required = true)
    protected Poruka poruke;

    /**
     * Gets the value of the cid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCid() {
        return cid;
    }

    /**
     * Sets the value of the cid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCid(String value) {
        this.cid = value;
    }

    /**
     * Gets the value of the senderUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Sets the value of the senderUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderUsername(String value) {
        this.senderUsername = value;
    }

    /**
     * Gets the value of the receiverUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverUsername() {
        return receiverUsername;
    }

    /**
     * Sets the value of the receiverUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverUsername(String value) {
        this.receiverUsername = value;
    }

    /**
     * Gets the value of the poruke property.
     * 
     * @return
     *     possible object is
     *     {@link Poruka }
     *     
     */
    public Poruka getPoruke() {
        return poruke;
    }

    /**
     * Sets the value of the poruke property.
     * 
     * @param value
     *     allowed object is
     *     {@link Poruka }
     *     
     */
    public void setPoruke(Poruka value) {
        this.poruke = value;
    }

}
