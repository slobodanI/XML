//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.20 at 03:05:01 PM CEST 
//


package rs.xml.oglas.xsd;

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
 *         &lt;element name="izvestaj" type="{http://xml.rs/oglas/xsd}Izvestaj"/&gt;
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
    "izvestaj"
})
@XmlRootElement(name = "postIzvestajRequest")
public class PostIzvestajRequest {

    @XmlElement(required = true)
    protected Izvestaj izvestaj;

    /**
     * Gets the value of the izvestaj property.
     * 
     * @return
     *     possible object is
     *     {@link Izvestaj }
     *     
     */
    public Izvestaj getIzvestaj() {
        return izvestaj;
    }

    /**
     * Sets the value of the izvestaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link Izvestaj }
     *     
     */
    public void setIzvestaj(Izvestaj value) {
        this.izvestaj = value;
    }

}
