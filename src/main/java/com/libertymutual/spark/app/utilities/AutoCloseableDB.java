package com.libertymutual.spark.app.utilities;

import java.io.Closeable;
import java.io.IOException;

import org.javalite.activejdbc.Base;

public class AutoCloseableDB implements Closeable, AutoCloseable {

	public AutoCloseableDB() {
		Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/rental", "rental", "rental");
	}
	
	
	@Override
	public void close() {
		Base.close();
		}

}
