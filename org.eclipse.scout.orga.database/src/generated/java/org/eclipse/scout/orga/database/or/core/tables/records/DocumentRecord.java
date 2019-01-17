/*
 * This file is generated by jOOQ.
*/
package org.eclipse.scout.orga.database.or.core.tables.records;


import java.math.BigDecimal;

import javax.annotation.Generated;

import org.eclipse.scout.orga.database.or.core.tables.Document;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DocumentRecord extends UpdatableRecordImpl<DocumentRecord> implements Record8<String, String, String, BigDecimal, byte[], String, String, Boolean> {

    private static final long serialVersionUID = -1233333969;

    /**
     * Setter for <code>core.DOCUMENT.ID</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.ID</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>core.DOCUMENT.NAME</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.NAME</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>core.DOCUMENT.TYPE</code>.
     */
    public void setType(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.TYPE</code>.
     */
    public String getType() {
        return (String) get(2);
    }

    /**
     * Setter for <code>core.DOCUMENT.SIZE</code>.
     */
    public void setSize(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.SIZE</code>.
     */
    public BigDecimal getSize() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>core.DOCUMENT.CONTENT</code>.
     */
    public void setContent(byte... value) {
        set(4, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.CONTENT</code>.
     */
    public byte[] getContent() {
        return (byte[]) get(4);
    }

    /**
     * Setter for <code>core.DOCUMENT.USER_ID</code>.
     */
    public void setUserId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.USER_ID</code>.
     */
    public String getUserId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>core.DOCUMENT.UPLOADED</code>.
     */
    public void setUploaded(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.UPLOADED</code>.
     */
    public String getUploaded() {
        return (String) get(6);
    }

    /**
     * Setter for <code>core.DOCUMENT.ACTIVE</code>.
     */
    public void setActive(Boolean value) {
        set(7, value);
    }

    /**
     * Getter for <code>core.DOCUMENT.ACTIVE</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, BigDecimal, byte[], String, String, Boolean> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, String, BigDecimal, byte[], String, String, Boolean> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Document.DOCUMENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Document.DOCUMENT.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Document.DOCUMENT.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field4() {
        return Document.DOCUMENT.SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<byte[]> field5() {
        return Document.DOCUMENT.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Document.DOCUMENT.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Document.DOCUMENT.UPLOADED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field8() {
        return Document.DOCUMENT.ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component4() {
        return getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] component5() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getUploaded();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component8() {
        return getActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value4() {
        return getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] value5() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getUploaded();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value8() {
        return getActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value3(String value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value4(BigDecimal value) {
        setSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value5(byte... value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value6(String value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value7(String value) {
        setUploaded(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord value8(Boolean value) {
        setActive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentRecord values(String value1, String value2, String value3, BigDecimal value4, byte[] value5, String value6, String value7, Boolean value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DocumentRecord
     */
    public DocumentRecord() {
        super(Document.DOCUMENT);
    }

    /**
     * Create a detached, initialised DocumentRecord
     */
    public DocumentRecord(String id, String name, String type, BigDecimal size, byte[] content, String userId, String uploaded, Boolean active) {
        super(Document.DOCUMENT);

        set(0, id);
        set(1, name);
        set(2, type);
        set(3, size);
        set(4, content);
        set(5, userId);
        set(6, uploaded);
        set(7, active);
    }
}
