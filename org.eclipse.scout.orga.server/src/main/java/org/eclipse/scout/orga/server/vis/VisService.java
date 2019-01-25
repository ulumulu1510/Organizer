package org.eclipse.scout.orga.server.vis;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.scout.orga.database.or.core.tables.records.BookingRecord;
import org.eclipse.scout.orga.server.booking.BookingService;
import org.eclipse.scout.orga.shared.vis.IVisService;
import org.eclipse.scout.orga.shared.vis.VisItem;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.StringUtility;

public class VisService implements IVisService {

	@Override
	public Map<String, List<VisItem>> calculateUserItems(Collection<String> userIds, Date start, Date end) {
		return BEANS.get(BookingService.class).getBookingsInInterval(start, end)
			.filter(record -> userIds.contains(record.getUserId()))
			.collect(Collectors.groupingBy(
					record -> StringUtility.uppercaseFirst(record.getUserId()),
					Collectors.mapping(this::itemize, Collectors.toList())
			));
	}

	private VisItem itemize(BookingRecord record) {
		Date dateFrom = record.getDateFrom();
		long timeDelta = record.getDateTo().getTime() - dateFrom.getTime();
		long hours = Math.max(TimeUnit.MILLISECONDS.toHours(timeDelta), 1);
		return new VisItem(dateFrom, hours);
	}

}
