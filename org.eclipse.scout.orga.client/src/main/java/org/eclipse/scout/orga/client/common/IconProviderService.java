package org.eclipse.scout.orga.client.common;

import java.net.URL;

import org.eclipse.scout.rt.client.services.common.icon.AbstractIconProviderService;
import org.eclipse.scout.rt.platform.Order;

import org.eclipse.scout.orga.client.ResourceBase;

/**
 * Provides application icons that are packaged with this application (resources
 * folder).
 */
@Order(2000)
public class IconProviderService extends AbstractIconProviderService {

	@Override
	protected URL findResource(String relativePath) {
		return ResourceBase.class.getResource("img/" + relativePath);
	}
}
