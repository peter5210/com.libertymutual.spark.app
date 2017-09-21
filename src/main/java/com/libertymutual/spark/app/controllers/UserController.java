package com.libertymutual.spark.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.spark.app.models.User;
import com.libertymutual.spark.app.utilities.AutoCloseableDB;
import com.libertymutual.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {
	
	public static final Route newForm = (Request req, Response res) -> {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);
		return MustacheRenderer.getInstance().render("home/signup.html", model);
	};
	
	public static final Route create = (Request req, Response res) -> {
		String encryptedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
		User user = new User (
		req.queryParams("first_name"),
		req.queryParams("last_name"),
		req.queryParams("email"),
		encryptedPassword
		);
		try (AutoCloseableDB db = new AutoCloseableDB()) {
		user.saveIt();
		req.session().attribute("currentUser", user);
		res.redirect("/");
		return "";
		}
		
	};


}
