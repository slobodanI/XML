//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.08 at 07:26:26 PM CEST 
//


package rs.xml.oglas.xsdgenerated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="oglasi" type="{http://xml.rs/oglas/xsd}Oglas" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="zahtevi" type="{http://xml.rs/oglas/xsd}Zahtev" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="izvestaji" type="{http://xml.rs/oglas/xsd}Izvestaj" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ocene" type="{http://xml.rs/oglas/xsd}Ocena" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="cenovnici" type="{http://xml.rs/oglas/xsd}Cenovnik" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "oglasi",
    "zahtevi",
    "izvestaji",
    "ocene",
    "cenovnici"
})
@XmlRootElement(name = "getEverythingResponse")
public class GetEverythingResponse {

    protected List<Oglas> oglasi;
    protected List<Zahtev> zahtevi;
    protected List<Izvestaj> izvestaji;
    protected List<Ocena> ocene;
    protected List<Cenovnik> cenovnici;

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
     * {@link Oglas }
     * 
     * 
     */
    public List<Oglas> getOglasi() {
        if (oglasi == null) {
            oglasi = new ArrayList<Oglas>();
        }
        return this.oglasi;
    }

    /**
     * Gets the value of the zahtevi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zahtevi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZahtevi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Zahtev }
     * 
     * 
     */
    public List<Zahtev> getZahtevi() {
        if (zahtevi == null) {
            zahtevi = new ArrayList<Zahtev>();
        }
        return this.zahtevi;
    }

    /**
     * Gets the value of the izvestaji property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the izvestaji property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIzvestaji().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Izvestaj }
     * 
     * 
     */
    public List<Izvestaj> getIzvestaji() {
        if (izvestaji == null) {
            izvestaji = new ArrayList<Izvestaj>();
        }
        return this.izvestaji;
    }

    /**
     * Gets the value of the ocene property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ocene property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOcene().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ocena }
     * 
     * 
     */
    public List<Ocena> getOcene() {
        if (ocene == null) {
            ocene = new ArrayList<Ocena>();
        }
        return this.ocene;
    }

    /**
     * Gets the value of the cenovnici property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cenovnici property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCenovnici().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cenovnik }
     * 
     * 
     */
    public List<Cenovnik> getCenovnici() {
        if (cenovnici == null) {
            cenovnici = new ArrayList<Cenovnik>();
        }
        return this.cenovnici;
    }

}