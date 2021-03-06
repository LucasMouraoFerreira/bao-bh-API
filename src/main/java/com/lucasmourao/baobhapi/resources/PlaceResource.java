package com.lucasmourao.baobhapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasmourao.baobhapi.dto.CompletePlaceDTO;
import com.lucasmourao.baobhapi.dto.SimplePlaceDTO;
import com.lucasmourao.baobhapi.dto.SimplePlaceWithDistanceDTO;
import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.entities.enums.Region;
import com.lucasmourao.baobhapi.geocodingAPI.GeocodingResult;
import com.lucasmourao.baobhapi.resources.exceptions.FindNearbyPlacesException;
import com.lucasmourao.baobhapi.resources.exceptions.GeocodingApiCallException;
import com.lucasmourao.baobhapi.resources.util.URL;
import com.lucasmourao.baobhapi.services.PlaceService;;

@RestController
@RequestMapping(value = "/places")
public class PlaceResource {

	@Value("${geoapi.key}")
	private String apiKey;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public ResponseEntity<Page<SimplePlaceDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {

		Pageable pageable = PageRequest.of(page, limit, Sort.by("avgRating").descending());

		Page<SimplePlaceDTO> list = placeService.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CompletePlaceDTO> findById(@PathVariable Long id) {
		CompletePlaceDTO place = new CompletePlaceDTO(placeService.findById(id));
		return ResponseEntity.ok().body(place);
	}

	@PostMapping
	public ResponseEntity<Place> insert(@RequestBody Place place) {
		place = placeService.insert(place);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(place.getId()).toUri();
		return ResponseEntity.created(uri).body(place);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		placeService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Place> update(@PathVariable Long id, @RequestBody Place place) {
		place = placeService.update(id, place);
		return ResponseEntity.ok().body(place);
	}

	@GetMapping(value = "/fullsearch")
	public ResponseEntity<Page<SimplePlaceDTO>> searchByRatingName(
			@RequestParam(value = "rating", defaultValue = "0.0") Double rating,
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "region", defaultValue = "") Integer region,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {

		text = URL.decodeParam(text);

		Pageable pageable = PageRequest.of(page, limit, Sort.by("avgRating").descending());

		Page<SimplePlaceDTO> list;

		if (region != null && Region.validRegion(region)) {
			list = placeService.searchByRatingTextRegion(rating, text, region, pageable);
		} else {
			list = placeService.searchByRatingText(rating, text, pageable);
		}

		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/findnearbyplaces")
	public ResponseEntity<List<SimplePlaceWithDistanceDTO>> findNearbyPlaces(
			@RequestParam(value = "latitude", defaultValue = "") Double latitude,
			@RequestParam(value = "longitude", defaultValue = "") Double longitude,
			@RequestParam(value = "maxDistanceKm", defaultValue = "10.0") Double maxDistanceKm) {

		List<SimplePlaceWithDistanceDTO> list;
		if (latitude == null || longitude == null) {
			throw new FindNearbyPlacesException("Error : latitude and longitude must be informed");
		} else {
			list = placeService.findNearbyPlaces(latitude, longitude, maxDistanceKm);
		}

		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/findnearbyplaceswithgeocoding")
	public ResponseEntity<List<SimplePlaceWithDistanceDTO>> findNearbyPlacesWithGeocoding(
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "maxDistanceKm", defaultValue = "10.0") Double maxDistanceKm) {

		GeocodingResult geocodingResult = restTemplate
				.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address=" + address
						+ "&components=country:BR" + "&key=" + apiKey, GeocodingResult.class);

		if (!geocodingResult.getStatus().contains("OK")) {
			throw new GeocodingApiCallException("Error: " + geocodingResult.getStatus());
		}

		List<SimplePlaceWithDistanceDTO> list = placeService.findNearbyPlaces(
				geocodingResult.getResults().get(0).getGeometry().getLocation().getLat(),
				geocodingResult.getResults().get(0).getGeometry().getLocation().getLng(), maxDistanceKm);

		return ResponseEntity.ok().body(list);
	}

}
