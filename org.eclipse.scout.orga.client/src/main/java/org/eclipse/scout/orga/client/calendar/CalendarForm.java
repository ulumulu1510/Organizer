package org.eclipse.scout.orga.client.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.orga.client.ClientSession;
import org.eclipse.scout.orga.client.calendar.CalendarForm.MainBox.CalendarField;
import org.eclipse.scout.orga.client.common.AbstractDeleteMenu;
import org.eclipse.scout.orga.client.common.AbstractEditMenu;
import org.eclipse.scout.orga.client.common.AbstractNewMenu;
import org.eclipse.scout.orga.client.booking.BookingForm;
import org.eclipse.scout.orga.shared.calendar.CalendarFormData;
import org.eclipse.scout.orga.shared.calendar.ICalendarService;
import org.eclipse.scout.orga.shared.booking.BookingFormParam;
import org.eclipse.scout.orga.shared.booking.IBookingService;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.root.ContextMenuEvent;
import org.eclipse.scout.rt.client.ui.action.menu.root.ContextMenuListener;
import org.eclipse.scout.rt.client.ui.action.menu.root.IContextMenu;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.CalendarComponent;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.OutlineMenuWrapper;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;

@FormData(value = CalendarFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CalendarForm extends AbstractForm {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Calendar");
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public CalendarField getCalendarField() {
		return getFieldByClass(CalendarField.class);
	}

	@Order(1000)
	public class MainBox extends AbstractGroupBox {

		@Order(1000)
		public class CalendarField extends AbstractCalendarField<CalendarField.Calendar> {

			@Override
			protected int getConfiguredGridH() {
				return 20;
			}

			@Override
			protected int getConfiguredGridW() {
				return 0;
			}

			@Override
			protected boolean getConfiguredLabelVisible() {
				return false;
			}

			@Override
			protected boolean getConfiguredStatusVisible() {
				return false;
			}

			public class Calendar extends AbstractCalendar {

		        private final List<IMenu> mainMenus = new ArrayList<>();
		        private final List<IMenu> calendarMenus = new ArrayList<>();

		        @Override
		        protected void execInitCalendar() {
		        	mainMenus.addAll(getForm().getRootGroupBox().getContextMenu().getChildActions());
		        	ContextMenuListener listener = event -> {
		        		if (ContextMenuEvent.TYPE_STRUCTURE_CHANGED == event.getType()) {
		        			IContextMenu rootContextMenu = getForm().getRootGroupBox().getContextMenu();
		        			calendarMenus.clear();
		        			for (IMenu menu : event.getSource().getChildActions()) {
		        				calendarMenus.add(OutlineMenuWrapper.wrapMenu(menu));
		        			}
		        			rootContextMenu.setChildActions(calendarMenus.isEmpty() ? mainMenus : calendarMenus);
		        		}
		        	};
		          getCalendar().getContextMenu().addContextMenuListener(listener);
		        }

		        protected String getBookingId() {
		        	CalendarComponent selectedComponent = getSelectedComponent();
					if (selectedComponent == null) {
						return null;
					}
					ICalendarItem item = selectedComponent.getItem();
					return item == null ? null : item.getItemId().toString();
		        }

				@Order(1000)
				public class BookingProvider extends AbstractCalendarItemProvider {
					@Override
					protected void execLoadItemsInBackground(IClientSession session, Date minDate, Date maxDate, Set<ICalendarItem> result) {
						result.addAll(BEANS.get(ICalendarService.class).loadBookingsInInterval(minDate, maxDate));
					}

					@Order(2000)
					public class BookingNewMenu extends AbstractNewMenu {
						@Override
						protected String getConfiguredText() {
							return TEXTS.get("NewBooking");
						}

						@Override
						protected Set<? extends IMenuType> getConfiguredMenuTypes() {
							return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
						}

						@Override
						protected void execAction() {
							startNewBookingSelectedDate();
						}
					}

					@Order(1000)
					public class BookingEditMenu extends AbstractEditMenu {

						@Override
						protected Set<? extends IMenuType> getConfiguredMenuTypes() {
							return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
						}

						@Override
						protected void execAction() {
							BookingForm bookingForm = new BookingForm();
							bookingForm.addFormListener(new CalendarFormListener());
							bookingForm.getBookingIdField().setValue(getBookingId());
							bookingForm.startModify();
						}
					}

					@Order(3000)
					public class BookingDeleteMenu extends AbstractDeleteMenu {

						@Override
						protected Set<? extends IMenuType> getConfiguredMenuTypes() {
							return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
						}

						@Override
						protected void execAction() {
							BEANS.get(IBookingService.class).delete(getBookingId());
							reloadCalendarItems();
						}

						@Override
						public AbstractSmartColumn<String> getOwner() {
							return null;
						}

						@Override
						protected boolean isOwner() {
							CalendarComponent selectedComponent = getSelectedComponent();
							if (selectedComponent == null) {
								return false;
							}
							ICalendarItem item = selectedComponent.getItem();
							return item != null && ClientSession.get().getUserId().equals(item.getOwner());
						}
					}
				}
			}
		}

		@Order(2000)
		public class BookingNewMenu extends AbstractNewMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("NewBooking");
			}

			@Override
			protected Set<? extends IMenuType> getConfiguredMenuTypes() {
				return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
			}

			@Override
			protected void execAction() {
				startNewBookingSelectedDate();
			}
		}

		private void startNewBookingSelectedDate() {
			BookingFormParam param = createFormParamFromSelectedDate();
			BookingForm bookingForm = new BookingForm(param);
			bookingForm.addFormListener(new CalendarFormListener());
			bookingForm.startNew();
		}

		private BookingFormParam createFormParamFromSelectedDate() {
			BookingFormParam param = null;
			Date selectedDate = getCalendarField().getCalendar().getSelectedDate();
			if (selectedDate != null) {
				param = new BookingFormParam();
				param.setDateFrom(selectedDate);
				param.setDateTo(selectedDate);
			}
			return param;
		}
	}

	protected class CalendarFormListener implements FormListener {
		@Override
		public void formChanged(FormEvent e) {
			if (FormEvent.TYPE_CLOSED == e.getType() && e.getForm().isFormStored()) {
				getCalendarField().reloadCalendarItems();
			}
		}
	}
}
