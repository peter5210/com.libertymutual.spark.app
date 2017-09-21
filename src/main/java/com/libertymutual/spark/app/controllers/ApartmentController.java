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
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("returnPath", req.queryParams("returnPath"));
		model.put("noUser", req.session().attribute("currentUser") == null);
		return MustacheRenderer.getInstance().render("apartment/details.html", model);
		}
	};
	
	public static final Route newForm = (Request req, Response res) -> {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currentUser", req.session().attribute("currentUser"));
		return MustacheRenderer.getInstance().render("apartment/newForm.html", null);
		};

	public static final Route create = (Request req, Response res) -> {
		try (AutoCloseableDB db = new AutoCloseableDB()) {
		User user = req.session().attribute("currentUser");
		Apartment apartment = new Apartment (
		Integer.parseInt(req.queryParams("rent")),
		Integer.parseInt(req.queryParams("number_of_bedrooms")),
		Double.parseDouble(req.queryParams("number_of_bathrooms")),
		Integer.parseInt(req.queryParams("square_footage")),
		req.queryParams("street"),
		req.queryParams("city"),
		req.queryParams("state"),
		req.queryParams("zipCode"),
		Boolean.parseBoolean(req.queryParams("isActive"))
		);
		user.add(apartment);
		apartment.saveIt();
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
			model.put("currentUser", currentUser);
			return MustacheRenderer.getInstance().render("apartment/myindex.html", model);	
		}
	};
	
	
	public static final Route like = (Request req, Response res) -> {
		User currentUser = req.session().attribute("currentUser");
		long id = (long) currentUser.getId();
		try (AutoCloseableDB db = new AutoCloseableDB()) {
			List<Apartment> apartments = Apartment.where("user_id = ?", id);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartments", apartments);
			model.put("currentUser", currentUser);
			model.put("returnPath", req.queryParams("returnPath"));
			MustacheRenderer.getInstance().render("apartment/myindex.html", model);	
		}
		res.redirect(req.queryParamOrDefault("returnPath", "/"));
		return "";
	};
	
}
