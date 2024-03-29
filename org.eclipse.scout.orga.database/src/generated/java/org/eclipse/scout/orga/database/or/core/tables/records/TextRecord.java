/*
 * This file is generated by jOOQ.
*/
package org.eclipse.scout.orga.database.or.core.tables.records;


import javax.annotation.Generated;

import org.eclipse.scout.orga.database.or.core.tables.Text;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class TextRecord extends UpdatableRecordImpl<TextRecord> implements Record3<String, String, String> {

    private static final long serialVersionUID = -1254621358;

    /**
     * Setter for <code>core.TEXT.KEY</code>.
     */
    public void setKey(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>core.TEXT.KEY</code>.
     */
    public String getKey() {
        return (String) get(0);
    }

    /**
     * Setter for <code>core.TEXT.LOCALE</code>.
     */
    public void setLocale(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>core.TEXT.LOCALE</code>.
     */
    public String getLocale() {
        return (String) get(1);
    }

    /**
     * Setter for <code>core.TEXT.TEXT</code>.
     */
    public void setText(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>core.TEXT.TEXT</code>.
     */
    public String getText() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Text.TEXT.KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Text.TEXT.LOCALE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Text.TEXT.TEXT_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getLocale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getLocale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextRecord value1(String value) {
        setKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextRecord value2(String value) {
        setLocale(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextRecord value3(String value) {
        setText(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextRecord values(String value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TextRecord
     */
    public TextRecord() {
        super(Text.TEXT);
    }

    /**
     * Create a detached, initialised TextRecord
     */
    public TextRecord(String key, String locale, String text) {
        super(Text.TEXT);

        set(0, key);
        set(1, locale);
        set(2, text);
    }
}
