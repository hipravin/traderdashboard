//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.28 at 12:49:56 PM MSK 
//


package com.hipravin.tradersdashboard.moex.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for rowType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rowType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="TRADENO" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="TRADETIME" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="BOARDID" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="SECID" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PRICE" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="QUANTITY" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="PERIOD" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="TRADETIME_GRP" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="SYSTIME" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="BUYSELL" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="DECIMALS" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="data_version" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="seqnum" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rowType", propOrder = {
    "value"
})
public class RowType {

    @XmlAttribute(name = "TRADENO")
    protected String tradeno;
    @XmlAttribute(name = "TRADETIME")
    protected String tradetime;
    @XmlAttribute(name = "BOARDID")
    protected String boardid;
    @XmlAttribute(name = "SECID")
    protected String secid;
    @XmlAttribute(name = "PRICE")
    protected String price;
    @XmlAttribute(name = "QUANTITY")
    protected String quantity;
    @XmlAttribute(name = "PERIOD")
    protected String period;
    @XmlAttribute(name = "TRADETIME_GRP")
    protected String tradetimegrp;
    @XmlAttribute(name = "SYSTIME")
    protected String systime;
    @XmlAttribute(name = "BUYSELL")
    protected String buysell;
    @XmlAttribute(name = "VALUE")
    protected String value;
    @XmlAttribute(name = "DECIMALS")
    protected String decimals;
    @XmlAttribute(name = "data_version")
    protected String dataVersion;
    @XmlAttribute(name = "seqnum")
    protected String seqnum;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the tradeno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRADENO() {
        return tradeno;
    }

    /**
     * Sets the value of the tradeno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRADENO(String value) {
        this.tradeno = value;
    }

    /**
     * Gets the value of the tradetime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRADETIME() {
        return tradetime;
    }

    /**
     * Sets the value of the tradetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRADETIME(String value) {
        this.tradetime = value;
    }

    /**
     * Gets the value of the boardid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOARDID() {
        return boardid;
    }

    /**
     * Sets the value of the boardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOARDID(String value) {
        this.boardid = value;
    }

    /**
     * Gets the value of the secid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSECID() {
        return secid;
    }

    /**
     * Sets the value of the secid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSECID(String value) {
        this.secid = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRICE() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRICE(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUANTITY() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUANTITY(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIOD() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIOD(String value) {
        this.period = value;
    }

    /**
     * Gets the value of the tradetimegrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRADETIMEGRP() {
        return tradetimegrp;
    }

    /**
     * Sets the value of the tradetimegrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRADETIMEGRP(String value) {
        this.tradetimegrp = value;
    }

    /**
     * Gets the value of the systime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSYSTIME() {
        return systime;
    }

    /**
     * Sets the value of the systime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSYSTIME(String value) {
        this.systime = value;
    }

    /**
     * Gets the value of the buysell property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUYSELL() {
        return buysell;
    }

    /**
     * Sets the value of the buysell property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUYSELL(String value) {
        this.buysell = value;
    }

    /**
     * Gets the value of the decimals property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDECIMALS() {
        return decimals;
    }

    /**
     * Sets the value of the decimals property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDECIMALS(String value) {
        this.decimals = value;
    }

    /**
     * Gets the value of the dataVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataVersion() {
        return dataVersion;
    }

    /**
     * Sets the value of the dataVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataVersion(String value) {
        this.dataVersion = value;
    }

    /**
     * Gets the value of the seqnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeqnum() {
        return seqnum;
    }

    /**
     * Sets the value of the seqnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeqnum(String value) {
        this.seqnum = value;
    }



}