package com.libertymutual.spark.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libertymutual.spark.app.models.Apartment;
import com.libertymutual.spark.app.utilities.AutoCloseableDB;
import com.libertymutual.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class HomeController {
	
	public static final Route index = (Request req, Response res) -> {
		try (AutoCloseableDB db = new AutoCloseableDB()) {
		List<Apartment> apartments = Apartment.findAll();	
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apartments", apartments);
		model.put("currentUser", req.session().attribute("currentUser"));
		model.put("noUser", req.session().attribute("currentUser") == null);
		return MustacheRenderer.getInstance().render("home/index.html", model);
		}

//		Velocity.init();
//		
//		Template template = Velocity.getTemplate("./src/main/resources/templates/home/index2.vm");
//		VelocityContext context = new VelocityContext();
//		StringWriter writer = new StringWriter();
//		context.put("apartments", apartments);
//		context.put("currentUser", req.session().attribute("currentUser"));
//		context.put("noUser", req.session().attribute("currentUser") == null);
//		template.merge(context, writer);
//		return writer;

	};

}
