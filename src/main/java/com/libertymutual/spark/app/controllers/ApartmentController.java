package com.libertymutual.spark.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libertymutual.spark.app.models.Apartment;
import com.libertymutual.spark.app.models.User;
import com.libertymutual.spark.app.utilities.AutoCloseableDB;
import com.libertymutual.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentController {

	public static final Route details = (Request req, Response res) -> {
		try (AutoCloseableDB db = new AutoCloseableDB()) {
		Apartment apartment = Apartment.findById(Integer.parseInt(req.params(":id")));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apartment", apartment);
		return MustacheRenderer.getInstance().render("apartment/details.html", model);
		}
	};
	
	public static final Route newForm = (Request req, Response res) -> {
		return MustacheRenderer.getInstance().render("apartment/newForm.html", null);
			
		};

	public static final Route create = (Request req, Response res) -> {
		
		Apartment apartment = new Apartment (
		Integer.parseInt(req.queryParams("rent")),
		Integer.parseInt(req.queryParams("numberOfBedrooms")),
		Double.parseDouble(req.queryParams("numberOfBathrooms")),
		Integer.parseInt(req.queryParams("squareFootage")),
		req.queryParams("street"),
		req.queryParams("city"),
		req.queryParams("state"),
		req.queryParams("zipCode")
		);
		try (AutoCloseableDB db = new AutoCloseableDB()) {
		apartment.saveIt();
		User user = req.session().attribute("currentUser");
		user.add(apartment);
		res.redirect("/");
		return "";
		}
	};

	public static final Route index = (Request req, Response res) -> {
		User currentUser = req.session().attribute("currentUser");
		long id = (long) currentUser.getId();
		try (AutoCloseableDB db = new AutoCloseableDB()) {
			List<Apartment> apartments = Apartment.where("user_id = ?", id);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartments", apartments);
			return MustacheRenderer.getInstance().render("apartment/myindex.html", model);	
		}
	};
}
