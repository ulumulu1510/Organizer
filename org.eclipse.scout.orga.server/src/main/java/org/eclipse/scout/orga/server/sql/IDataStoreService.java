package org.eclipse.scout.orga.server.sql;

import org.eclipse.scout.rt.platform.ApplicationScoped;

@ApplicationScoped
public interface IDataStoreService {

	void drop();

	void create();
}
