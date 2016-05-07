package com.cisco.blogapp.infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ServicesFactory {
	
	
	private static ThreadLocal<Datastore> mongoTL = new ThreadLocal<Datastore>();
	
	/**
	 * Method to retrieve a mongo database client from the thread local storage
	 * @return
	 */
	public static Datastore getMongoDB(){
		
		Properties prop = new Properties();
		String ip,port;
		InputStream input = null;
		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println("sande: " +prop.getProperty("ip"));
			System.out.println("sande: " +prop.getProperty("port"));
			
			ip = prop.getProperty("ip");
			port = prop.getProperty("port");

		} catch (IOException ex) {
			ip = "localhost";
			port = "27017";
			
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(mongoTL.get()==null){
//			MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
			MongoClientURI connectionString = new MongoClientURI(new String("mongodb://"+ ip + ":" + port));
			MongoClient mongoClient = new MongoClient(connectionString);	
			Morphia morphia = new Morphia();
			morphia.mapPackage("com.cisco.blogapp.model");
			Datastore datastore = morphia.createDatastore(mongoClient, "test");
			datastore.ensureIndexes();
			mongoTL.set(datastore);
			return datastore;
		}
		return mongoTL.get();
	}
	
}
