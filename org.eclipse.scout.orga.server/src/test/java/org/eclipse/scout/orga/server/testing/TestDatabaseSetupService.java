package org.eclipse.scout.orga.server.testing;

import org.eclipse.scout.rt.platform.Replace;

import org.eclipse.scout.orga.server.sql.DatabaseSetupService;


@Replace
public class TestDatabaseSetupService extends DatabaseSetupService {
	public void autoCreateDatabase() {
		//Dont do anything, this is just tests
	}
}
