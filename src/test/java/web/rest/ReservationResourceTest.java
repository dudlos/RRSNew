package web.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dudlo.reservationsystem.ApplicationConfig;
import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Menu.MenuType;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.RoleEnum;
import com.dudlo.reservationsystem.model.SlotEnum;
import com.dudlo.reservationsystem.model.TimeSlot;
import com.dudlo.reservationsystem.model.User;
import com.dudlo.reservationsystem.service.MenuService;
import com.dudlo.reservationsystem.service.ReservationService;
import com.dudlo.reservationsystem.service.RestTableService;
import com.dudlo.reservationsystem.service.RestaurantService;
import com.dudlo.reservationsystem.service.TimeSlotService;
import com.dudlo.reservationsystem.service.UserService;
import com.dudlo.reservationsystem.webapp.rest.ReservationResource;

/**
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
public class ReservationResourceTest {

	@Inject
	private ReservationService reservationService;

	@Inject
	private RestaurantService restaurantService;

	@Inject
	private MenuService menuService;

	@Inject
	private TimeSlotService timeSlotService;

	@Inject
	private RestTableService restTableService;
	@Inject
	private UserService userService;
	

	List<RestTable> allTables;
	
	Menu menu;

	private MockMvc restMvc;

	private Long restaurantID = 0L;

	private Long tableID = 0L;

	private Long timeSlotID = 0L;
	private Long reservationID = 0L;
	private Long menuID = 0L;
	String reservationTime;
	Date bookingDate;
	String localDate;
	String bookingDateString;

	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	
		ReservationResource reservationResource = new ReservationResource(
				reservationService, restaurantService, menuService,
				restTableService, timeSlotService, userService);
		restMvc = MockMvcBuilders.standaloneSetup(reservationResource).build();

		Restaurant rest = restaurantService
				.createNewRestaurant("Havran");
		long rest11 = rest.getRestaurantID();
		menuService.createMenu(MenuType.Main, "BeefStake", "Medium, Well Done Rib Eye", 15.00 , rest);
		menu = menuService.getAllMenusFromRestaurant(rest11).get(0);
		restaurantID = rest.getRestaurantID();
		
		List<SlotEnum> slotsEnumList1 = new ArrayList<SlotEnum>();
		
		timeSlotService.createTimeSlot("MONDAY", rest);
		timeSlotService.createTimeSlot("TUESDAY", rest);
		timeSlotService.createTimeSlot("WEDNESDAY", rest);
		timeSlotService.createTimeSlot("THURSDAY", rest);
		timeSlotService.createTimeSlot("FRIDAY", rest);
		timeSlotService.createTimeSlot("SATURDAY", rest);
		timeSlotService.createTimeSlot("SUNDAY", rest);
		
		
		
		slotsEnumList1.add(SlotEnum.SLOT1);
		slotsEnumList1.add(SlotEnum.SLOT2);
		slotsEnumList1.add(SlotEnum.SLOT3);
		List<SlotEnum> slotsEnumList2 = new ArrayList<SlotEnum>();
		slotsEnumList2.add(SlotEnum.SLOT4);
		slotsEnumList2.add(SlotEnum.SLOT5);
		slotsEnumList2.add(SlotEnum.SLOT6);
		List<SlotEnum> slotsEnumList3 = new ArrayList<SlotEnum>();
		slotsEnumList3.add(SlotEnum.SLOT1);
		slotsEnumList3.add(SlotEnum.SLOT9);

		List<TimeSlot> timeSlotsRest1 = timeSlotService.getAllTimeSlotsForRestaurant(rest); 
		

		timeSlotService.addSlots(timeSlotsRest1.get(0), slotsEnumList1);
		timeSlotService.addSlots(timeSlotsRest1.get(1), slotsEnumList2);
		timeSlotService.addSlots(timeSlotsRest1.get(2), slotsEnumList3);
		timeSlotService.addSlots(timeSlotsRest1.get(3), slotsEnumList1);
		timeSlotService.addSlots(timeSlotsRest1.get(4), slotsEnumList2);
		timeSlotService.addSlots(timeSlotsRest1.get(5), slotsEnumList3);
		timeSlotService.addSlots(timeSlotsRest1.get(6), slotsEnumList1);
		
		restTableService.createTable(5, "A17", rest);
		restTableService.createTable(4, "A10", rest);
		allTables = restTableService.findAllByRestaurant(rest);
		RestTable table = restTableService.findAll().get(0);
		tableID = table.getTableID();
		bookingDate = Date.valueOf(LocalDate.now());
		bookingDateString = bookingDate.toString();
		localDate = bookingDate.toLocalDate().getDayOfWeek().toString();
		TimeSlot timeSlot = timeSlotService.getTimeSlotByDay(rest, localDate);
		timeSlotID = timeSlot.getTimeSlotID();
		
		userService.createUser("Peter", "Duhacek", "peterduhacek@yahoo.com", "centra6", RoleEnum.ROLE_USER);
		 List<User> users = userService.getAllUsers();
		 User user = users.get(0);
		
		List<SlotEnum> list = new ArrayList<SlotEnum>(timeSlot.getSlots());
	    reservationTime =  list.get(0).getSlot();
		Reservation booking = reservationService.makeReservation(Date.valueOf(LocalDate.now()), 4, rest, table, reservationTime, user);
		reservationID = booking.getReservationID();
		menuID = menu.getMenuID();
	}

	@Test
	public void checkAvailibilityDate_Success_Test() throws Exception {
		restMvc.perform(
				get("/api/checkAvailibilityDate")
						.accept(MediaType.APPLICATION_JSON)
						.param("reservationDate", bookingDateString)
						.param("restaurantID", String.valueOf(restaurantID)))
				.andDo(print())
				.andExpect(content().string(containsString(reservationTime)));
			
}

	@Test
	public void checkAvailibilityTime_Success_Test() throws Exception {
		restMvc.perform(
				get("/api/checkAvailibilityTime")
						.accept(MediaType.APPLICATION_JSON)
						.param("reservationDate", bookingDateString)
						.param("restaurantID", String.valueOf(restaurantID))
						.param("reservationTime", reservationTime)
					.param("numberOfPerson", String.valueOf(2))
	)

				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.freeTables", hasSize(2)))
				.andExpect(
						jsonPath("$.freeTables[0].tableID", is(Long.valueOf(allTables.get(0)
								.getTableID()).intValue())))
				.andExpect(
						jsonPath("$.freeTables[1].tableID", is(Long.valueOf(allTables.get(1)
								.getTableID()).intValue())))
				.andExpect(jsonPath("$.freeTables[*].capacity", containsInAnyOrder(4, 5)));
	}

	@Test
	public void makeReservation_Success_Test() throws Exception {
		restMvc.perform(
				post("/api/makeReservation").accept(MediaType.APPLICATION_JSON)
						.param("reservationDate", bookingDateString)
						.param("reservationTime", bookingDateString)
						.param("numberOfPerson", String.valueOf(2))
						.param("restaurantID", String.valueOf(restaurantID))
						.param("tableID", String.valueOf(tableID))
					.param("timeSlotID", String.valueOf(timeSlotID)))

				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void addMenus_Success_Test() throws Exception {
		restMvc.perform(
		post("/api/addMenus").accept(MediaType.APPLICATION_JSON)
		.param("reservationID", String.valueOf(reservationID))
		.param("menuID",  String.valueOf(menuID)))
				
				.andDo(print())
				.andExpect(status().isOk());
				//andExpect(jsonPath("$.menuList[0].menu.menuID", is(menu.getMenuID())));
		
	}
}
