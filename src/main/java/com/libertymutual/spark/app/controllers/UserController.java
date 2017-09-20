package com.libertymutual.spark.app.controllers;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.spark.app.models.User;
import com.libertymutual.spark.app.utilities.AutoCloseableDB;
import com.libertymutual.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {
	
	public static final Route newForm = (Request req, Response res) -> {
		return MustacheRenderer.getInstance().render("home/signup.html", null);
	};
	
	public static final Route create = (Request req, Response res) -> {
		String encryptedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
		User user = new User (
		req.queryParams("firstname"),
		req.queryParams("lastname"),
		req.queryParams("email"),
		encryptedPassword
		);
		try (AutoCloseableDB db = new AutoCloseableDB()) {
		user.saveIt();
		req.session().attribute("currentUser", user);
		res.redirect("/login");
		return "";
		}
		
	};


}
