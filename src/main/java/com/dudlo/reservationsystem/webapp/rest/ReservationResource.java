package com.dudlo.reservationsystem.webapp.rest;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dudlo.reservationsystem.config.CustomException;
import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.User;
import com.dudlo.reservationsystem.service.MenuService;
import com.dudlo.reservationsystem.service.ReservationService;
import com.dudlo.reservationsystem.service.RestTableService;
import com.dudlo.reservationsystem.service.RestaurantService;
import com.dudlo.reservationsystem.service.TimeSlotService;
import com.dudlo.reservationsystem.service.UserService;
import com.dudlo.reservationsystem.webapp.dto.ExceptionDTO;
import com.dudlo.reservationsystem.webapp.dto.GetReservationDTO;
import com.dudlo.reservationsystem.webapp.dto.MenuDTO;
import com.dudlo.reservationsystem.webapp.dto.MenuReservationDTO;
import com.dudlo.reservationsystem.webapp.dto.ReservationDTO;
import com.dudlo.reservationsystem.webapp.dto.ReservationDTO2;

/**
 * 
 */

@RestController
@RequestMapping("/api")
public class ReservationResource {

	private Logger log = LoggerFactory.getLogger(ReservationResource.class);

	private ReservationService reservationService;

	private RestaurantService restaurantService;

	private RestTableService restTableService;

	private MenuService menuService;

	private UserService userService;

	@Autowired
	private Environment env;

	@Autowired
	public ReservationResource(ReservationService reservationService, RestaurantService restaurantService, MenuService menuService,
			RestTableService restTableService, TimeSlotService timeSlotService, UserService userService) {
		this.reservationService = reservationService;
		this.restaurantService = restaurantService;
		this.menuService = menuService;
		this.restTableService = restTableService;
		this.userService = userService;
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public ModelAndView homePage() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/getAllRestaurants", method = RequestMethod.GET)
	public ArrayList<Restaurant> getAllRestaurants() {
		ArrayList<Restaurant> allRestaurants = new ArrayList<Restaurant>();
		allRestaurants.addAll(restaurantService.getAllRestaurants());
		return allRestaurants;

	}

	@RequestMapping(value = "/testNoResultException", method = RequestMethod.GET)
	public ArrayList<Restaurant> testNoResultException() {
		throw new NoResultException();

	}

	@RequestMapping(value = "/checkAvailibilityDate", method = RequestMethod.GET)
	ResponseEntity<ReservationDTO> checkAvailibilityDate(
			@RequestParam(value = "reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String reservationDate,
			@RequestParam(value = "restaurantID") Long restaurantId) {

		// NOTE: hodil by sa check, ci restaurantId nie je null
		Restaurant restaurant = restaurantService.findById(restaurantId);

		List<String> freeSlots = reservationService.checkAvailibilityDate(java.sql.Date.valueOf(reservationDate), restaurant);

		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setFreeSlots(freeSlots);

		return new ResponseEntity<ReservationDTO>(reservationDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/checkAvailibilityTime", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReservationDTO2> checkAvailibilityTime(

	@RequestParam(value = "restaurantID") Long restaurantId,
			@RequestParam(value = "reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String reservationDate,
			@RequestParam(value = "reservationTime") String reservationTime, @RequestParam(value = "numberOfPerson") int numberOfPerson) {

		Restaurant restaurant = restaurantService.findById(restaurantId);
		List<RestTable> freeTables = reservationService.checkAvailibilityTime(restaurant, java.sql.Date.valueOf(reservationDate),
				reservationTime, numberOfPerson);

		ReservationDTO2 reservationDTO2 = new ReservationDTO2();
		reservationDTO2.setFreeTables(freeTables);

		return new ResponseEntity<ReservationDTO2>(reservationDTO2, HttpStatus.OK);
	}


	 @RequestMapping(value = "/makeReservation", method = RequestMethod.POST)
	 
	 public Long makeReservation(@RequestParam(value =
	 "reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String
	 reservationDate,
	  
	  @RequestParam(value = "reservationTime") String reservationTime,
	  @RequestParam(value = "numberOfPerson") int numberOfPerson,
	  @RequestParam(value = "restaurantID") long restaurantID,
	  @RequestParam(value = "tableID") long tableID, @RequestParam(value ="userID") long userID) {
	   Restaurant restaurant = restaurantService.findById(restaurantID);
	  RestTable table = restTableService.findById(tableID); User user =
	  userService.findUserByID(userID);
	  
	  Reservation reservation = reservationService.makeReservation(java.sql.Date.valueOf(reservationDate), numberOfPerson, restaurant, table,
	  reservationTime, user); 
	  Long reservationID = reservation.getReservationID();
	  return reservationID; 
	  }
	 

	@RequestMapping(value = "/tableBooking" , method = RequestMethod.GET)
	public ModelAndView tableBooking(@RequestParam(value = "reservationID") Long reservationID) {
		ModelAndView mav = new ModelAndView("SuccessBooking");
		mav.addObject("reservationID", reservationID);
		return mav;
	
		
	}
	
	@RequestMapping(value = "/preLoginView" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView preLoginView(
			@RequestParam(value =  "reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String reservationDate,
			  @RequestParam(value = "reservationTime") String reservationTime,
			  @RequestParam(value = "numberOfPerson") int numberOfPerson,
			  @RequestParam(value = "restaurantID") long restaurantID,
			  @RequestParam(value = "tableID") long tableID, 
			  @RequestParam(value ="userID") long userID) 
	{
		ModelAndView mav = new ModelAndView("preLogin");
		mav.addObject("reservationDate", reservationDate).addObject("reservationTime", reservationTime).addObject("numberOfPerson", numberOfPerson)
		.addObject( "restaurantID",  restaurantID).addObject("tableID", tableID).addObject("userID", userID);
		return mav ;
		
	}
			
			

	/*@RequestMapping(value = "/makeReservation", method = RequestMethod.POST)
	@ResponseBody
	public RedirectView makeReservation(RedirectAttributes redirectAttrs,
			@RequestParam(value = "reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String reservationDate,
			@RequestParam(value = "reservationTime") String reservationTime, @RequestParam(value = "numberOfPerson") int numberOfPerson,
			@RequestParam(value = "restaurantID") long restaurantId, @RequestParam(value = "tableID") long tableID,
			@RequestParam(value = "userID") long userID) {

		Restaurant restaurant = restaurantService.findById(restaurantId);
		RestTable table = restTableService.findById(tableID);
		User user = userService.findUserByID(userID);

		Reservation reservation = reservationService.makeReservation(java.sql.Date.valueOf(reservationDate), numberOfPerson, restaurant,
				table, reservationTime, user);
		Long reservationID = reservation.getReservationID();

		redirectAttrs.addAttribute("reservationID", reservationID);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("tableBooking");
		redirectView.setStatusCode(HttpStatus.SEE_OTHER);
		return redirectView;

	}*/

	@RequestMapping(value =  "/getReservation" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GetReservationDTO> getReservation(@RequestParam(value = "reservationID") long reservationID) {
		Reservation reservation = reservationService.findByID(reservationID);
		GetReservationDTO getReservationDTO = new GetReservationDTO();
		getReservationDTO.setNewReservation(reservation);
		return new ResponseEntity<GetReservationDTO>(getReservationDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllMenusForRestaurant", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<MenuDTO> getAllMenusForRestaurant(@RequestParam(value = "restaurantID") long restaurantID) {

		MenuDTO getMenuDTO = new MenuDTO();
		getMenuDTO.setMenus(menuService.getAllMenusFromRestaurant(restaurantID));
		return new ResponseEntity<MenuDTO>(getMenuDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllMenusForMenuType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<MenuDTO> getAllMenusForMenuType(@RequestParam(value = "restaurantID") long restaurantID,
			@RequestParam(value = "menuType") String menuType) {

		MenuDTO getMenuDTO = new MenuDTO();
		getMenuDTO.setMenus(menuService.getAllMenusForMenuType(menuType, restaurantID));
		return new ResponseEntity<MenuDTO>(getMenuDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/addMenus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<MenuReservationDTO> addMenu(@RequestParam(value = "reservationID") Long reservationID,
			@RequestParam(value = "menuID1", required = false) Long menuID1,
			@RequestParam(value = "menuID2", required = false) Long menuID2,
			@RequestParam(value = "menuID3", required = false) Long menuID3,
			@RequestParam(value = "menuID4", required = false) Long menuID4, @RequestParam(value = "menuID5", required = false) Long menuID5) {
		ArrayList<Long> newList = new ArrayList<Long>();
		if (menuID1 != null) {
			newList.add(menuID1);
		}
		;
		if (menuID2 != null) {
			newList.add(menuID2);
		}
		;
		if (menuID3 != null) {
			newList.add(menuID3);
		}
		if (menuID4 != null) {
			newList.add(menuID4);
		}
		;
		if (menuID5 != null) {
			newList.add(menuID5);
		}
		;

		for (Long menu : newList) {
			reservationService.addMenus(reservationID, menu);
		}
		Reservation reservation = reservationService.findByID(reservationID);
		MenuReservationDTO getMenuDTO = new MenuReservationDTO();
		getMenuDTO.setMenuList(reservation.getMenus());

		return new ResponseEntity<MenuReservationDTO>(getMenuDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/findAllMenusPerReservation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Menu> getAllMenusReservation(@RequestParam(value = "reservationID") long reservationID) {
		List<Menu> allMenu = new ArrayList<Menu>();
		allMenu = reservationService.findAllMenusPerReservation(reservationID);
		return allMenu;
	}

	@ExceptionHandler(TypeMismatchException.class)
	@ResponseBody
	public String typeMismatchExceptionHandler(Exception ex, HttpServletRequest request) {
		log.error("Type Mismatch Exception: ", ex);
		return ex.getMessage();
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	public ResponseEntity<ExceptionDTO> missingServletRequestParameterExceptionHandler(Exception ex, HttpServletRequest request) {
		log.error("Missing Servlet Request Parameter Exception: ", ex);
		String errorMsg = MessageFormat.format(env.getProperty("global.exception"), ex.getLocalizedMessage());
		return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(errorMsg), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoResultException.class)
	@ResponseBody
	public ResponseEntity<ExceptionDTO> noResultExceptionExceptionHandler(Exception ex, HttpServletRequest request) {
		log.error("No Result Exception: ", ex);
		String errorMsg = env.getProperty("global.noResultException");
		return new ResponseEntity<ExceptionDTO>(new ExceptionDTO(errorMsg), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	@ResponseBody
	public String customExceptionHandler(CustomException ex) {
		log.warn("Custom Exception: ", ex);
		return env.getProperty(ex.getExceptionCode());

	}

}
