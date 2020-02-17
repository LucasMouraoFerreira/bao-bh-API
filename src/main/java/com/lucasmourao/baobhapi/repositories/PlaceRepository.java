package com.lucasmourao.baobhapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.baobhapi.entities.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

	@Query("SELECT p FROM Place p where (p.avgRating >= :rating and (UPPER(p.description) like UPPER(concat('%', :text,'%')) or UPPER(p.name) like UPPER(concat('%', :text,'%'))))")
	Page<Place> searchByRatingText(@Param("rating") Double rating, @Param("text") String text, Pageable pageable);

	@Query("SELECT p FROM Place p where (p.avgRating >= :rating and (UPPER(p.description) like UPPER(concat('%', :text,'%')) or UPPER(p.name) like UPPER(concat('%', :text,'%'))) and p.region = :region)")
	Page<Place> searchByRatingTextRegion(@Param("rating") Double rating, @Param("text") String text,
			@Param("region") Integer region, Pageable pageable);

	Page<Place> findAll(Pageable pageable);

	List<Place> findAllByOrderByAvgRatingDesc();
}
