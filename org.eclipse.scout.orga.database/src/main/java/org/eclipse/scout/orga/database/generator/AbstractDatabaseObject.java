package org.eclipse.scout.orga.database.generator;

import org.eclipse.scout.rt.platform.util.StringUtility;
import org.jooq.DSLContext;

public abstract class AbstractDatabaseObject implements IDatabaseObject {

	private DSLContext context;

	@Override
	public void setContext(DSLContext context) {
		assert context != null;
		this.context = context;
	}

	@Override
	public DSLContext getContext() {
		return context;
	}

	@Override
	public String getCreateSQL() {
		return context
				.createSchema(getName())
				.getSQL();
	}

	@Override
	public void create() {
		String sql = getCreateSQL();
		getLogger().info("SQL-DEV sql statement: {}", sql);

		if(StringUtility.hasText(sql)) {
			context.execute(sql);
		}
	}

	@Override
	public void drop() {

	}
}
