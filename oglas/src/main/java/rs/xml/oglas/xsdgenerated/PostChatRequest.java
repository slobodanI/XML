//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.08 at 11:09:32 AM CEST 
//


package rs.xml.oglas.xsdgenerated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="chat" type="{http://xml.rs/oglas/xsd}Chat"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "chat"
})
@XmlRootElement(name = "postChatRequest")
public class PostChatRequest {

    @XmlElement(required = true)
    protected Chat chat;

    /**
     * Gets the value of the chat property.
     * 
     * @return
     *     possible object is
     *     {@link Chat }
     *     
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * Sets the value of the chat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Chat }
     *     
     */
    public void setChat(Chat value) {
        this.chat = value;
    }

}
