package com.example.corona.virus.tracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.corona.virus.tracker.models.LocationStats;

@Service
public class CoronaVirusDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoronaVirusDataService.class);

	public static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	private List<LocationStats> allStats = new ArrayList<>();

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusData() throws Exception, IOException, InterruptedException {
		try {
			List<LocationStats> newStats = new ArrayList<>();
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
			HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
			StringReader csvBodyReader = new StringReader(httpResponse.body());
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
			for (CSVRecord record : records) {
				LocationStats locationStat = new LocationStats();
				locationStat.setState(record.get("Province/State"));
				locationStat.setCountry(record.get("Country/Region"));
				int latestCases = Integer.parseInt(record.get(record.size() - 1));
				int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
				locationStat.setLatestTotalCases(latestCases);
				locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
				LOGGER.info("Service Response is " + httpResponse);
				LOGGER.info("Service Response locationStat is " + locationStat);
				newStats.add(locationStat);
			}
			this.allStats = newStats;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getLocalizedMessage());
			throw new Exception();
		}

	}
}