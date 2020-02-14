package com.lucasmourao.baobhapi.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucasmourao.baobhapi.entities.Comment;
import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.repositories.CommentRepository;
import com.lucasmourao.baobhapi.repositories.PlaceRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public void run(String... agrs) throws Exception{
		String businessHours = "Segunda: 8:00 às 17:00;Terça: 8:00 às 17:00;Quarta: 8:00 às 17:00;"
				+ "Quinta: 8:00 às 17:00;Sexta: 8:00 às 17:00;Sabádo: 8:00 às 17:00;Domingo: 8:00 às 17:00";
		
		Place place1 = new Place(null, "Zoologico", "Lugar para ver animais", "Rua Otacilio Negrão de Lima", businessHours, 4.5, 5L);
		Place place2 = new Place(null, "Parque Ecologico", "Contato com a natureza", "Rua Otacilio Negrão de Lima", businessHours, 4.7, 10L);
		
		Comment comment1 = new Comment(null, Instant.now(),"Adorei ver o filhote de gorila","Vanessa",place1);
		Comment comment2 = new Comment(null, Instant.now(),"Ótimo lugar para um piquenique","Roberson",place2);
		placeRepository.saveAll(Arrays.asList(place1,place2));
		commentRepository.saveAll(Arrays.asList(comment1,comment2));
	}
}
