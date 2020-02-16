package com.lucasmourao.baobhapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.baobhapi.entities.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

	@Query("SELECT p FROM Place p where (p.avgRating >= :rating and (UPPER(p.description) like UPPER(concat('%', :text,'%')) or UPPER(p.name) like UPPER(concat('%', :text,'%')))) order by p.avgRating desc")
	List<Place> searchByRatingText(@Param("rating") Double rating, @Param("text") String text);

	@Query("SELECT p FROM Place p where (p.avgRating >= :rating and (UPPER(p.description) like UPPER(concat('%', :text,'%')) or UPPER(p.name) like UPPER(concat('%', :text,'%'))) and p.region = :region) order by p.avgRating desc")
	List<Place> searchByRatingTextRegion(@Param("rating") Double rating, @Param("text") String text,
			@Param("region") Integer region);

	List<Place> findAllByOrderByAvgRatingDesc();

}
