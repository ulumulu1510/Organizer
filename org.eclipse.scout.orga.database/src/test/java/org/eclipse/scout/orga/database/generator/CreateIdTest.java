package org.eclipse.scout.orga.database.generator;

import org.junit.Test;

import org.eclipse.scout.orga.database.table.TableDataInitializer;

public class CreateIdTest {

	@Test
	public void createIds()  throws Exception {
		System.out.println(TableDataInitializer.createId());
		System.out.println(TableDataInitializer.createId());
		System.out.println(TableDataInitializer.createId());
		System.out.println(TableDataInitializer.createId());
	}
}
