//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.08 at 01:03:54 PM EDT 
//


package org.xframium.history;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Execution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Execution"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="startTime" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="stopTime" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="passed" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="failed" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="ignored" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="total" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="success" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="fileName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="persona" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="context" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="failureType" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="SCRIPT"/&gt;
 *             &lt;enumeration value="APPLICATION"/&gt;
 *             &lt;enumeration value="CONFIG"/&gt;
 *             &lt;enumeration value="CLOUD"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Execution")
public class Execution {

    @XmlAttribute(name = "startTime", required = true)
    protected long startTime;
    @XmlAttribute(name = "stopTime", required = true)
    protected long stopTime;
    @XmlAttribute(name = "passed", required = true)
    protected int passed;
    @XmlAttribute(name = "failed", required = true)
    protected int failed;
    @XmlAttribute(name = "ignored", required = true)
    protected int ignored;
    @XmlAttribute(name = "total", required = true)
    protected int total;
    @XmlAttribute(name = "success", required = true)
    protected boolean success;
    @XmlAttribute(name = "fileName", required = true)
    protected String fileName;
    @XmlAttribute(name = "persona")
    protected String persona;
    @XmlAttribute(name = "context")
    protected String context;
    @XmlAttribute(name = "failureType", required = true)
    protected String failureType;

    /**
     * Gets the value of the startTime property.
     * 
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     */
    public void setStartTime(long value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the stopTime property.
     * 
     */
    public long getStopTime() {
        return stopTime;
    }

    /**
     * Sets the value of the stopTime property.
     * 
     */
    public void setStopTime(long value) {
        this.stopTime = value;
    }

    /**
     * Gets the value of the passed property.
     * 
     */
    public int getPassed() {
        return passed;
    }

    /**
     * Sets the value of the passed property.
     * 
     */
    public void setPassed(int value) {
        this.passed = value;
    }

    /**
     * Gets the value of the failed property.
     * 
     */
    public int getFailed() {
        return failed;
    }

    /**
     * Sets the value of the failed property.
     * 
     */
    public void setFailed(int value) {
        this.failed = value;
    }

    /**
     * Gets the value of the ignored property.
     * 
     */
    public int getIgnored() {
        return ignored;
    }

    /**
     * Sets the value of the ignored property.
     * 
     */
    public void setIgnored(int value) {
        this.ignored = value;
    }

    /**
     * Gets the value of the total property.
     * 
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     */
    public void setTotal(int value) {
        this.total = value;
    }

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the persona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersona() {
        return persona;
    }

    /**
     * Sets the value of the persona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersona(String value) {
        this.persona = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContext(String value) {
        this.context = value;
    }

    /**
     * Gets the value of the failureType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailureType() {
        return failureType;
    }

    /**
     * Sets the value of the failureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailureType(String value) {
        this.failureType = value;
    }

}
