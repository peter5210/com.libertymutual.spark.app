package com.libertymutual.spark.app;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.spark.app.controllers.ApartmentApiController;
import com.libertymutual.spark.app.controllers.ApartmentController;
import com.libertymutual.spark.app.controllers.HomeController;
import com.libertymutual.spark.app.controllers.SessionController;
import com.libertymutual.spark.app.controllers.UserApiController;
import com.libertymutual.spark.app.controllers.UserController;
import com.libertymutual.spark.app.filters.SecurityFilters;
import com.libertymutual.spark.app.models.Apartment;
import com.libertymutual.spark.app.models.User;
import com.libertymutual.spark.app.utilities.AutoCloseableDB;

public class Application {
	
	public static void main(String[] args) {
		
		String encryptedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
		
		try (com.libertymutual.spark.app.utilities.AutoCloseableDB db = new AutoCloseableDB()) {
			
		User.deleteAll();
		User peter = new User("abc@gmail.com", encryptedPassword, "peter", "boudsy");
		peter.saveIt();
				
				
		Apartment.deleteAll();
		Apartment apt1 = new Apartment(300, 1, 1, 350, "123 Main St", "Renton", "WA", "90000", false);
		peter.add(apt1);
		apt1.saveIt();
		
		
		Apartment apt2 = new Apartment(1999, 3, 2.5, 1000, "123 Main St", "Tukwila", "WA", "90000", false);
		peter.add(apt2);
		apt2.saveIt();
		
	}
		
		//apartment paths
		path("/apartments", () -> {
			before("", SecurityFilters.isAuthenticated);
			get("/new", ApartmentController.newForm);
			
			//list all apts for a user
			before("/mine", SecurityFilters.isAuthenticated);
			get("/mine", ApartmentController.index);
			
			get("/:id", ApartmentController.details);
			
			//create a new apt listing
			before("/new", SecurityFilters.isAuthenticated);
			post("/new", ApartmentController.create);
			
			//like an apartment
			before("/new", SecurityFilters.isAuthenticated);
			post("/:id/like", ApartmentController.like);
			
		});
		
		path("/api", () -> {
			get("/apartments/:id", ApartmentApiController.details);
			post("/apartments", ApartmentApiController.create);
		});
		
		//Home Page
		get("/", HomeController.index);
		get("/login", SessionController.newForm);
		post("/login", SessionController.create);
		
		//Sign up page
		get("/users/new", UserController.newForm);
		post("/users", UserController.create);
		
		path("/api", () -> {
			post("/signup", UserApiController.create);
		});
		
		//logout page
		post("/logout", SessionController.destroy);

	}
}
