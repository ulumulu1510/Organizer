package org.eclipse.scout.orga.server.sql;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.database.generator.GeneratorApplication;
import org.eclipse.scout.orga.database.table.TableDataInitializer;
import org.eclipse.scout.orga.server.ServerProperties;

public class DataStoreService extends AbstractJooqService implements IDataStoreService {

	private static final Logger LOG = LoggerFactory.getLogger(DataStoreService.class);

	@Override
	public void create() {
		createDatabaseScheme();
		populateDatabase();
	}

	private void createDatabaseScheme() {
		if (CONFIG.getPropertyValue(ServerProperties.DatabaseAutoCreateProperty.class)) {
			LOG.info("Create database schema");
			GeneratorApplication.setupDatabase(getContext());
		}
	}

	private void populateDatabase() {
		if (CONFIG.getPropertyValue(ServerProperties.DatabaseAutoPopulateProperty.class)) {
			LOG.info("Populate database");

			TableDataInitializer data = BEANS.get(TableDataInitializer.class);
			DSLContext context = getContext();
			data.initialize(context);

			if (CONFIG.getPropertyValue(ServerProperties.DatabaseAutoPopulateProperty.class)) {
				LOG.info("Add additional sample data");
				data.addSamples(context);
			}
		}
	}

	@Override
	public void drop() {
		LOG.error("Add implementation");
	}
}
