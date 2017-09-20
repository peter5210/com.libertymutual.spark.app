package com.libertymutual.spark.app.controllers;

import java.util.Map;

import com.libertymutual.spark.app.models.User;
import com.libertymutual.spark.app.utilities.AutoCloseableDB;
import com.libertymutual.spark.app.utilities.JsonHelper;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserApiController {
	

	public static final Route create = (Request req, Response res) -> {
		String json = req.body();
		Map map = JsonHelper.toMap(json);
		User user = new User();
		user.fromMap(map);
		
		try (AutoCloseableDB db = new AutoCloseableDB()) {
		user.saveIt();
		res.status(201);
		return user.toJson(true);
		}
	};
}
