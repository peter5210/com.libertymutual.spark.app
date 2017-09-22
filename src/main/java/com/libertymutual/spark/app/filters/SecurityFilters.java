package com.libertymutual.spark.app.filters;

import static spark.Spark.halt;

import java.util.UUID;

import spark.Filter;
import spark.Request;
import spark.Response;

public class SecurityFilters {
	
	public static Filter isAuthenticated = (Request req, Response res) -> {
		if (req.session().attribute("currentUser") == null) {
		res.redirect("/login?returnPath=" + req.pathInfo());
		halt();
		}
	};
	
	public static Filter newSession = (Request req, Response res) -> {
		if (req.session().isNew()) {
		req.session(true);
		String token = UUID.randomUUID().toString();
		req.session().attribute("CSRF", token);
		}
	};

	public static Filter checkToken = (Request req, Response res) -> {
		if (req.session().attribute("CSRF") == null) {
			res.redirect("/");
			halt();
		}
	};
}
