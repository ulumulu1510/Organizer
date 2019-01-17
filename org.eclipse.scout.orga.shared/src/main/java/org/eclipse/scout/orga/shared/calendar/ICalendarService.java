package org.eclipse.scout.orga.shared.calendar;

import java.util.Date;
import java.util.Set;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;

@TunnelToServer
public interface ICalendarService extends IService {

	Set<ICalendarItem> loadPaymentsInInterval(Date minDate, Date maxDate);
}
