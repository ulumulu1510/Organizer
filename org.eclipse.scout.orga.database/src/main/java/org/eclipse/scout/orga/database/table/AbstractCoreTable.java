package org.eclipse.scout.orga.database.table;

import org.eclipse.scout.orga.database.generator.AbstractTable;

public abstract class AbstractCoreTable extends AbstractTable {

	@Override
	public String getSchemaName() {
		return CoreSchema.SCHEMA;
	}
}
