/*
 * This file is generated by jOOQ.
*/
package org.eclipse.scout.orga.database.or.core.tables.records;


import javax.annotation.Generated;

import org.eclipse.scout.orga.database.or.core.tables.Role;
import org.jooq.Field;
import org.jooq.Record1;
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
public class RoleRecord extends UpdatableRecordImpl<RoleRecord> implements Record3<String, String, Boolean> {

    private static final long serialVersionUID = 1591873934;

    /**
     * Setter for <code>core.ROLE.NAME</code>.
     */
    public void setName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>core.ROLE.NAME</code>.
     */
    public String getName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>core.ROLE.TEXT</code>.
     */
    public void setText(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>core.ROLE.TEXT</code>.
     */
    public String getText() {
        return (String) get(1);
    }

    /**
     * Setter for <code>core.ROLE.ACTIVE</code>.
     */
    public void setActive(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>core.ROLE.ACTIVE</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, Boolean> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Role.ROLE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Role.ROLE.TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field3() {
        return Role.ROLE.ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component3() {
        return getActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value3() {
        return getActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value1(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value2(String value) {
        setText(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord value3(Boolean value) {
        setActive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRecord values(String value1, String value2, Boolean value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RoleRecord
     */
    public RoleRecord() {
        super(Role.ROLE);
    }

    /**
     * Create a detached, initialised RoleRecord
     */
    public RoleRecord(String name, String text, Boolean active) {
        super(Role.ROLE);

        set(0, name);
        set(1, text);
        set(2, active);
    }
}
