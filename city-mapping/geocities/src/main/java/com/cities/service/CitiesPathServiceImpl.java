package com.cities.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.cities.util.graph.Graph;
import com.cities.util.graph.GraphSearchAlgorithm;
import com.cities.util.graph.Pair;

/**
 * 
 * @author Ikram Soomro
 *
 */
@Component
public class CitiesPathServiceImpl implements CitiesPathFinderService {

	private volatile Graph<String> graph;

	@Value("${cities.graph.algorithm}")
	private String algorithm;

	@Value("${cities.graph.file}")
	private String file;

	@Value("${cities.graph.search.realtime}")
	private String searchRealTime;

	/**
	 * initializes the graph
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws IOException {
		Set<String> cities = new HashSet<>();
		Set<Pair<String>> directCities = new HashSet<>();

		Resource resource = new ClassPathResource(file);
		InputStream resourceAsStream = resource.getInputStream();
		Scanner scanner = new Scanner(resourceAsStream);

		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] split = line.split(",");
			Pair<String> directlyConnectedCities = new Pair<>(split[0].trim(), split[1].trim());
			directCities.add(directlyConnectedCities);
			cities.add(directlyConnectedCities.getLeft());
			cities.add(directlyConnectedCities.getRight());
		}
		scanner.close();
		graph = new Graph<>(new ArrayList<>(cities), directCities, GraphSearchAlgorithm.valueOf(algorithm),
				Boolean.valueOf(searchRealTime));
	}

	/**
	 * 
	 * @see com.cities.service.ICitiesPathFinderService#isPathPresent(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean isPathPresent(String origin, String destination) {
		return graph.isPathPresent(origin, destination);
	}
}
