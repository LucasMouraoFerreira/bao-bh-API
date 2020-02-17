package com.lucasmourao.baobhapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.baobhapi.dto.SimplePlaceDTO;
import com.lucasmourao.baobhapi.dto.SimplePlaceWithDistanceDTO;
import com.lucasmourao.baobhapi.entities.Comment;
import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.repositories.PlaceRepository;
import com.lucasmourao.baobhapi.services.exceptions.DatabaseException;
import com.lucasmourao.baobhapi.services.exceptions.ResourceNotFoundException;
import com.lucasmourao.baobhapi.services.exceptions.UpdateAvgRatingException;

@Service
public class PlaceService {

	@Autowired
	private PlaceRepository placeRepository;

	public Page<SimplePlaceDTO> findAll(Pageable pageable){
		return placeRepository.findAll(pageable).map(x-> new SimplePlaceDTO(x));
	}
	
	public Place findById(Long id) {
		Optional<Place> place = placeRepository.findById(id);
		return place.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Place insert(Place place) {
		return placeRepository.save(place);
	}

	public void delete(Long id) {
		try {
			placeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Place update(Long id, Place place) {
		Optional<Place> entity = placeRepository.findById(id);
		updateData(entity.orElseThrow(() -> new ResourceNotFoundException(id)), place);
		return placeRepository.save(entity.get());
	}

	private void updateData(Place entity, Place place) {
		if (place.getName() != null) {
			entity.setName(place.getName());
		}
		if (place.getDescription() != null) {
			entity.setDescription(place.getDescription());
		}
		if (place.getAddress() != null) {
			entity.setAddress(place.getAddress());
		}
		entity.setRegion(place.getRegion());
		if (place.getBusinessHours() != null) {
			entity.setBusinessHours(place.getBusinessHours());
		}
		if (place.getLatitude() != null) {
			entity.setLatitude(place.getLatitude());
		}
		if (place.getLongitude() != null) {
			entity.setLongitude(place.getLongitude());
		}
	}

	public void newAvgRating(Comment comment, Place place) {
		if (comment.getRating() != null) {
			if (comment.getRating() >= 0.0 && comment.getRating() <= 5.0) {
				place.setAvgRating((comment.getRating() + (place.getAvgRating() * place.getNumberOfRatings()))
						/ (place.getNumberOfRatings() + 1));
				place.setNumberOfRatings(place.getNumberOfRatings() + 1);
				placeRepository.save(place);
			} else {
				throw new UpdateAvgRatingException();
			}
		}
	}

	public void updateAvgRating(Comment comment, Comment entity, Place place) {
		if (comment.getRating() >= 0.0 && comment.getRating() <= 5.0) {
			Double previousAvgRating = ((place.getAvgRating() * place.getNumberOfRatings()) - entity.getRating())
					/ (place.getNumberOfRatings() - 1);
			place.setAvgRating((comment.getRating() + (previousAvgRating * (place.getNumberOfRatings() - 1)))
					/ (place.getNumberOfRatings()));
			placeRepository.save(place);
		} else {
			throw new UpdateAvgRatingException();
		}
	}

	public List<Place> searchByRatingText(Double rating, String text) {
		return placeRepository.searchByRatingText(rating, text);
	}

	public List<Place> searchByRatingTextRegion(Double rating, String text, Integer region) {
		return placeRepository.searchByRatingTextRegion(rating, text, region);
	}

	public List<SimplePlaceWithDistanceDTO> findNearbyPlaces(Double latitude, Double longitude, Double maxDistanceKm) {
		List<Place> places = placeRepository.findAllByOrderByAvgRatingDesc();
		return places.stream().filter(
				p -> getDistanceFromLatLonInKm(p.getLatitude(), p.getLongitude(), latitude, longitude) <= maxDistanceKm)
				.map(p -> new SimplePlaceWithDistanceDTO(p,
						getDistanceFromLatLonInKm(p.getLatitude(), p.getLongitude(), latitude, longitude)))
				.collect(Collectors.toList());
	}

	private double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {

		double R = 6371.0; // Radius of the earth in km
		double dLat = deg2rad(lat2 - lat1);
		double dLon = deg2rad(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c; // Distance in km
		return d;

	}

	private double deg2rad(double deg) {
		return deg * (Math.PI / 180);
	}
}
