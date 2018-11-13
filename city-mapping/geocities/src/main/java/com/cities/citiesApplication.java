package com.cities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class citiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(citiesApplication.class, args);
	}
}


/*http://localhost:8089/connected?origin=Boston&destination=Newark

Should return yes

http://localhost:8089/connected?origin=Boston&destination=Philadelphia

Should return yes

http://localhost:8089/connected?origin=Philadelphia&destination=Albany

Should return no*/