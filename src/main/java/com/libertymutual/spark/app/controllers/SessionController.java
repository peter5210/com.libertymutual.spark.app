package com.libertymutual.spark.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.spark.app.models.Apartment;
import com.libertymutual.spark.app.models.User;
import com.libertymutual.spark.app.utilities.AutoCloseableDB;
import com.libertymutual.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class SessionController {

	public static final Route newForm = (Request req, Response res) -> {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("returnPath", req.queryParams("returnPath"));
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);
		return MustacheRenderer.getInstance().render("session/newForm.html", model);
	};

	 public static final Route create = (Request req, Response res) -> {
	 String email = req.queryParams("email");
	 String password = req.queryParams("password");
	
	 try (AutoCloseableDB db = new AutoCloseableDB()) {
	 User user = User.findFirst("email = ?", email);
	 if (user != null && BCrypt.checkpw(password, user.getPassword())) {
	 req.session().attribute("currentUser", user);
	 }
	 }
	 res.redirect(req.queryParamOrDefault("returnPath", "/"));
	 return "";
	 };

//	public static final Route create = (Request req, Response res) -> {
//		String email = req.queryParams("email");
//		String password = req.queryParams("password");
//
//		try (AutoCloseableDB db = new AutoCloseableDB()) {
//			User user = User.findFirst("email = ?", email);
//			if (user != null && BCrypt.checkpw(password, user.getPassword())) {
//				req.session().attribute("currentUser", user);
//			}
//			List<Apartment> apartments = Apartment.findAll();
//			Map<String, Object> model = new HashMap<String, Object>();
//			model.put("apartments", apartments);
//			model.put("currentUser", req.session().attribute("currentUser"));
//			model.put("noUser", req.session().attribute("currentUser") == null);
//
//			return MustacheRenderer.getInstance().render("home/index.html", model);
//		}
//	};

	public static final Route destroy = (Request req, Response res) -> {
		req.session().removeAttribute("currentUser");

		try (AutoCloseableDB db = new AutoCloseableDB()) {
			List<Apartment> apartments = Apartment.findAll();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("apartments", apartments);
			model.put("noUser", req.session().attribute("currentUser") == null);
			return MustacheRenderer.getInstance().render("home/index.html", model);
		}
	};

}
