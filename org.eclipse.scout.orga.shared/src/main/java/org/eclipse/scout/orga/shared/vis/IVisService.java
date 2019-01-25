package org.eclipse.scout.orga.shared.vis;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IVisService extends IService {

	Map<String, List<VisItem>> calculateUserItems(Collection<String> userIds, Date start, Date end);

}
