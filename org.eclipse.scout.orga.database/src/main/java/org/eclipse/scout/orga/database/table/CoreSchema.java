package org.eclipse.scout.orga.database.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.database.generator.AbstractSchema;

public class CoreSchema extends AbstractSchema {

	public static final String SCHEMA = "core";

	@Override
	public String getName() {
		return SCHEMA;
	}

	public Logger getLogger() {
		return LoggerFactory.getLogger(CoreSchema.class);
	}
}
