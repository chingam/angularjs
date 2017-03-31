package net.m4.empowerhub.connector.galaxyconnect.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metafour.empowerhub.connector.api.json.Address;
import com.metafour.empowerhub.connector.api.json.Availability;
import com.metafour.empowerhub.connector.api.json.Basket;
import com.metafour.empowerhub.connector.api.json.BasketItem;
import com.metafour.empowerhub.connector.api.json.Guest;
import com.metafour.empowerhub.connector.api.json.Product;
import com.metafour.empowerhub.connector.api.json.Supplier;
import com.metafour.empowerhub.connector.galaxyconnect.GalaxyConnect;

/**
 * 
 * @author Minhaj
 * @since 2017-03-31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GalaxyConnect.class)
@ActiveProfiles({ "default", "dev", "test" })
public class GalaxyConnectApiCertificationTest {

	@Autowired private TestRestTemplate restTemplate;

	/*
	 * Booking Item for a Capacity Managed Product
	 * Quantity:1
	 * Has Guest(s) Associated: Yes
	 * Lead Guest Associated: Yes
	 */
	@Test
	public void testBookingSingleQuantityCapacityManaged() {
		final int quantity = 1;
		//Get suppliers
		ResponseEntity<Supplier[]> rs = restTemplate.getForEntity("/suppliers", Supplier[].class);
		String supplierId=new ArrayList<Supplier>(Arrays.asList(rs.getBody())).stream().collect(Collectors.toList()).get(1).getId();
		//Get products by supplier id
		ResponseEntity<Product[]> responseProducts = restTemplate.getForEntity("/suppliers/" + supplierId + "/products", Product[].class);
		String productCode=new ArrayList<Product>(Arrays.asList(responseProducts.getBody())).stream().filter(p -> p.getHasAvailability()==Boolean.TRUE).collect(Collectors.toList()).get(0).getId();
		//Get availability of product
		ResponseEntity<Availability> responseAvailabiltys = restTemplate.getForEntity("/products/{productid}/availability", Availability.class, productCode);
		String eventId=new ArrayList<Availability>(Arrays.asList(responseAvailabiltys.getBody())).stream().collect(Collectors.toList()).get(0).getEvents().stream().collect(Collectors.toList()).get(0).getId();
		//Create basket
		ResponseEntity<Basket> responseBasket = restTemplate.postForEntity("/baskets", null, Basket.class);
		String basketId = responseBasket.getBody().getId();
		
		BasketItem bi = new BasketItem(basketId, null);
		bi.setProductId(productCode);
		bi.setEventId(eventId);
		bi.setQuantity(quantity);
		//Add item to basket
		ResponseEntity<BasketItem> responseBusketItem = restTemplate.postForEntity("/baskets/" + basketId + "/items", bi, BasketItem.class);
		//Add guest to basket
		ResponseEntity<Guest> responseGuest = restTemplate.postForEntity("/baskets/" + basketId + "/guests", addGuest(true), Guest.class);
		//check out
		ResponseEntity<Basket> resCheckout = restTemplate.postForEntity("/baskets/" + basketId + "/checkout", null, Basket.class);
		assertEquals(HttpStatus.OK, rs.getStatusCode());
		Basket basket = resCheckout.getBody();
		System.out.println(resCheckout);
		System.out.println(basket);
		assertNotNull("Checkout not done, missing something", rs);
	}
	
	
	/*
	 * Booking Item
	 * Capacity Managed Product
	 * Quantity:2
	 * Guest
	 * Is Lead Guest: Yes
	 */
	@Test
	public void testBookingMultiquantityCapacityManaged() {
		final int quantity = 2;
		//Get suppliers
		ResponseEntity<Supplier[]> rs = restTemplate.getForEntity("/suppliers", Supplier[].class);
		String supplierId=new ArrayList<Supplier>(Arrays.asList(rs.getBody())).stream().collect(Collectors.toList()).get(1).getId();
		//Get products by supplier id
		ResponseEntity<Product[]> responseProducts = restTemplate.getForEntity("/suppliers/" + supplierId + "/products", Product[].class);
		String productCode=new ArrayList<Product>(Arrays.asList(responseProducts.getBody())).stream().filter(p -> p.getHasAvailability()==Boolean.TRUE).collect(Collectors.toList()).get(0).getId();
		//Get availability of product
		ResponseEntity<Availability> responseAvailabiltys = restTemplate.getForEntity("/products/{productid}/availability", Availability.class, productCode);
		String eventId=new ArrayList<Availability>(Arrays.asList(responseAvailabiltys.getBody())).stream().collect(Collectors.toList()).get(0).getEvents().stream().collect(Collectors.toList()).get(0).getId();
		//Create basket
		ResponseEntity<Basket> responseBasket = restTemplate.postForEntity("/baskets", null, Basket.class);
		String basketId = responseBasket.getBody().getId();
		
		BasketItem bi = new BasketItem(basketId, null);
		bi.setProductId(productCode);
		bi.setEventId(eventId);
		bi.setQuantity(quantity);
		//Add item to basket
		ResponseEntity<BasketItem> responseBusketItem = restTemplate.postForEntity("/baskets/" + basketId + "/items", bi, BasketItem.class);
		//Add guest one to basket
		ResponseEntity<Guest> responseGuestOne = restTemplate.postForEntity("/baskets/" + basketId + "/guests", addGuest(true), Guest.class);
		//Add guest two to basket
		ResponseEntity<Guest> responseGuestTwo = restTemplate.postForEntity("/baskets/" + basketId + "/guests", addGuest(false), Guest.class);
		//check out
		ResponseEntity<Basket> resCheckout = restTemplate.postForEntity("/baskets/" + basketId + "/checkout", null, Basket.class);
		assertEquals(HttpStatus.OK, rs.getStatusCode());
		Basket basket = resCheckout.getBody();
		System.out.println(resCheckout);
		System.out.println(basket);
		assertNotNull("Checkout not done, missing something", rs);
	}
	
	
	/*
	 * Booking Item #1
	 * 	Capacity Managed Product
	 * 		Quantity:1
	 * 		Has Guest(s) Associated: Yes
	 * 		Lead Guest Associated: Yes
	 * 
	 * Booking Item #2
	 * 	Capacity Managed Product
	 * 		Quantity:2
	 * 		Has Guest(s) associated: Yes
	 * 		Lead Guest Associated: No
	 */
	@Test
	public void testMultiBookingItemMultiquantityCapacityManaged() {
		int firstQuantity = 1;
		int secondQuantity = 2;
		//Get suppliers
		ResponseEntity<Supplier[]> rs = restTemplate.getForEntity("/suppliers", Supplier[].class);
		String supplierId=new ArrayList<Supplier>(Arrays.asList(rs.getBody())).stream().collect(Collectors.toList()).get(1).getId();
		//Get products by supplier id
		ResponseEntity<Product[]> responseProducts = restTemplate.getForEntity("/suppliers/" + supplierId + "/products", Product[].class);
		String productCode=new ArrayList<Product>(Arrays.asList(responseProducts.getBody())).stream().filter(p -> p.getHasAvailability()==Boolean.TRUE).collect(Collectors.toList()).get(0).getId();
		//Get availability of product
		ResponseEntity<Availability> responseAvailabiltys = restTemplate.getForEntity("/products/{productid}/availability", Availability.class, productCode);
		String eventId=new ArrayList<Availability>(Arrays.asList(responseAvailabiltys.getBody())).stream().collect(Collectors.toList()).get(0).getEvents().stream().collect(Collectors.toList()).get(0).getId();
		//Create basket
		ResponseEntity<Basket> responseBasket = restTemplate.postForEntity("/baskets", null, Basket.class);
		String basketId = responseBasket.getBody().getId();
		//Add first item to basket
		BasketItem bi = new BasketItem(basketId, null);
		bi.setProductId(productCode);
		bi.setEventId(eventId);
		bi.setQuantity(firstQuantity);
		ResponseEntity<BasketItem> responseBusketItem = restTemplate.postForEntity("/baskets/" + basketId + "/items", bi, BasketItem.class);
		//Add guest to basket
		ResponseEntity<Guest> responseGuest = restTemplate.postForEntity("/baskets/" + basketId + "/guests", addGuest(true), Guest.class);		//Get suppliers
		//Add second item to basket
		BasketItem secondBi = new BasketItem(basketId, null);
		bi.setProductId(productCode);
		bi.setEventId(eventId);
		bi.setQuantity(secondQuantity);
		//Add item to basket
		ResponseEntity<BasketItem> responseSecondItem = restTemplate.postForEntity("/baskets/" + basketId + "/items", bi, BasketItem.class);
		//Add guest one to basket
		ResponseEntity<Guest> responseGuestOne = restTemplate.postForEntity("/baskets/" + basketId + "/guests", addGuest(false), Guest.class);
		//Add guest two to basket
		ResponseEntity<Guest> responseGuestTwo = restTemplate.postForEntity("/baskets/" + basketId + "/guests", addGuest(false), Guest.class);
		//check out
		ResponseEntity<Basket> resCheckout = restTemplate.postForEntity("/baskets/" + basketId + "/checkout", null, Basket.class);
		assertEquals(HttpStatus.OK, rs.getStatusCode());
		Basket basket = resCheckout.getBody();
		System.out.println(resCheckout);
		System.out.println(basket);
		assertNotNull("Checkout not done, missing something", rs);
	}
	
	
	/*
	 * Booking Item for a General Admission Product
	 * Quantity:1
	 * Has Guest(s) Associated: Yes
	 * Lead Guest Associated: Yes
	 */
	@Test
	public void testBookingSingleQuantityGeneralAdmission() {
		final int quantity = 1;
		//Get suppliers
		ResponseEntity<Supplier[]> rs = restTemplate.getForEntity("/suppliers", Supplier[].class);
		String supplierId=new ArrayList<Supplier>(Arrays.asList(rs.getBody())).stream().collect(Collectors.toList()).get(1).getId();
		//Get products by supplier id
		ResponseEntity<Product[]> responseProducts = restTemplate.getForEntity("/suppliers/" + supplierId + "/products", Product[].class);
		String productCode=new ArrayList<Product>(Arrays.asList(responseProducts.getBody())).stream().filter(p -> p.getHasAvailability()==Boolean.FALSE).collect(Collectors.toList()).get(0).getId();
		//Create basket
		ResponseEntity<Basket> responseBasket = restTemplate.postForEntity("/baskets", null, Basket.class);
		String basketId = responseBasket.getBody().getId();
		
		BasketItem bi = new BasketItem(basketId, null);
		bi.setProductId(productCode);
		bi.setQuantity(quantity);
		//Add item to basket
		ResponseEntity<BasketItem> responseBusketItem = restTemplate.postForEntity("/baskets/" + basketId + "/items", bi, BasketItem.class);
		//Add guest to basket
		ResponseEntity<Guest> responseGuest = restTemplate.postForEntity("/baskets/" + basketId + "/guests", addGuest(true), Guest.class);
		//check out
		ResponseEntity<Basket> resCheckout = restTemplate.postForEntity("/baskets/" + basketId + "/checkout", null, Basket.class);
		assertEquals(HttpStatus.OK, rs.getStatusCode());
		Basket basket = resCheckout.getBody();
		System.out.println(resCheckout);
		System.out.println(basket);
		assertNotNull("Checkout not done, missing something", rs);
	
	}
	
	
	private Guest addGuest(Boolean lead) {
		Guest guestTwo = new Guest();
		guestTwo.setTitle("Mr");
		guestTwo.setFirstName("Minhajul");
		guestTwo.setLastName("Sarkar");
		guestTwo.setMiddleName("Minhaj");
		guestTwo.setEmail("minhajul.sarkar@metafour.com");
		guestTwo.setPhone("01717208211");
		guestTwo.setAddress(newAddress());
		guestTwo.setLead(lead);
		return guestTwo;
	}
	
	private Address newAddress() {
		Address address=new Address();
		address.setCity("Dhaka");
		address.setCountry("Bangladesh");
		address.setLine1("Dhaka");
		address.setLine2("Dinajpur");
		return address;
	}

}
