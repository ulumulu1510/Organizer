package org.eclipse.scout.orga.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPreferenceTable extends AbstractCoreTable {

    public static final Logger LOG = LoggerFactory.getLogger(UserPreferenceTable.class);
    private static final String TABLE = "user_preference";
    private static final String USER = "user";
    private static final String NODE = "node";
    private static final String DATA = "data";
    
    @Override
    public String createSQLInternal() {
        return getContext().createTable(getName())
                .column(USER, TYPE_STRING_L)
                .column(NODE, TYPE_STRING_M)
                .column(DATA, TYPE_BLOB)
                .constraint(DSL.constraint(getPKName()).primaryKey(USER, NODE))
                .getSQL();
    }

    @Override
    public String getName() {
        return TABLE;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }

}
