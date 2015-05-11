package co.edureka;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PowerOfStreams {

	public static void main(String[] args) {
		BufferedReader breader=null;		
		try{		
		Path path = Paths.get("src/resources", "movies.csv");
        breader = Files.newBufferedReader(
            path, StandardCharsets.ISO_8859_1);
        
		}catch(IOException exception){
			System.out.println("Error occured while trying to read the file");
			System.exit(0);
		}
		
		List<String> lines=breader.lines()
				                  .collect(Collectors.toList());
		
		//To get the list of all movie names
		List<String> movies=lines.stream()
		     .skip(1)
		     .map(line -> Arrays.asList(line.split(";")))	
		     .map(list -> { String movie=list.get(0).trim(); return movie;})
		     .collect(Collectors.toList());		
		System.out.println(movies);
		
		//Refactored code to get the list of all movie names
		
		List<String> movieNames=lines.stream()
			     .skip(1)
			     .map(line -> Arrays.asList(line.split(";")).get(0).trim())	
				 .collect(Collectors.toList());		
		System.out.println(movieNames);

		//Red River Director
		
		lines.stream()
		     .skip(1)
		     .map(line -> Arrays.asList(line.split(";")))	
		     .filter(movie -> {String movieName=movie.get(0); 
	                           return movieName.trim().equalsIgnoreCase("Red River");})
		     .forEach(list -> {String director=list.get(2);
	                           System.out.println("Red River was directed by "+director);});

		//List of all documentary movie names
		
		List<String> docMovies=lines.stream()
				   .skip(1)
				   .map(line -> Arrays.asList(line.split(";")))	
				   .filter(list -> {String type=list.get(1); 
		                                return type.trim().equalsIgnoreCase("documentary");})
				   .map(movie -> {String movieName=movie.get(0); return movieName;})
				   .collect(Collectors.toList());
		System.out.println(docMovies);

	   //Total count of documentary movies
		long totalDocMovies=lines.stream()
				   .skip(1)
				   .map(line -> Arrays.asList(line.split(";")).get(1))	
				   .filter(movieType -> movieType.trim().equalsIgnoreCase("documentary"))
				   .count();
		System.out.println("Total Documentary Movies : "+totalDocMovies);

		
		//List of all documentary movies released in 2000
		List<String> doc2000List=lines.stream()
				   .skip(1)
				   .map(line -> Arrays.asList(line.split(";")))	
				   .filter(movie -> {String movieType=movie.get(1).trim(); 
		                             return (!movieType.equals("")&& movieType.equalsIgnoreCase("documentary"));})
				   .filter(list -> {String year=list.get(7).trim();
		                            return (!year.equals("")&& year.equals("2000"));})
				   .map(movie -> {String name=movie.get(0); return name;})
				   .collect(Collectors.toList());				   
		System.out.println(doc2000List);

		
		//Refactored code to list all documentary movies released in 2000
		List<String> doc2000=lines.stream()
		  .skip(1)
		  .map(line -> Arrays.asList(line.split(";")))	
		  .filter(movie -> {
		   String type=movie.get(1).trim();
		   String year=movie.get(7).trim();
		   return (!type.equals("")&& !year.equals("")&& type.equalsIgnoreCase("documentary")&& year.equals("2000"));})
		  .map(movie -> {String name=movie.get(0); return name;})
		  .collect(Collectors.toList());				   
        System.out.println(doc2000);
        
       //List of all documentary movies released in 2000 having IMDB rating greater than or equal to 7           
        List<String> doc2000IMDB7=lines.stream()
    			.skip(1)
    			.map(line -> Arrays.asList(line.split(";")))	
    			.filter(movie -> {String type=movie.get(1).trim();
    			                  return (!type.equals("")&& type.equalsIgnoreCase("documentary"));})
    			.filter(movie -> {String year=movie.get(7).trim();
    				              return (!year.equals("")&& year.equals("2000"));})
    			.filter(movie -> {String imdb=movie.get(4).trim();
    				              return (!imdb.equals("")&& Float.parseFloat(imdb)>= 7);})
    			.map(movie ->    {String movieName=movie.get(0).trim();
    				              return movieName;})
    			.collect(Collectors.toList());		
        System.out.println(doc2000IMDB7);


        // Minimum runtime among all the movies(Shortest Movie Duration)
        String minimumRuntime=lines.stream()
  			  .skip(1)
  			  .map(line -> {String runtime=line.split(";")[6]; 
                                      return runtime.trim();})	
  			  .filter(movieRuntime -> !movieRuntime.equals(""))
  			  .min(Comparator.comparing(time -> Float.parseFloat(time)))
  			  .get();  				      
  		
        System.out.println("Minimum Runtime "+minimumRuntime);

        
        // Maximum runtime among all the movies(Longest Movie Duration)
        String maximumRuntime=lines.stream()
			       .skip(1)
			       .map(line -> {String runtime=line.split(";")[6]; 
                                      return runtime.trim();})	
			       .filter(movieRuntime -> !movieRuntime.equals(""))
			       .max(Comparator.comparing(time -> Float.parseFloat(time)))
			       .get();
			      
	
       System.out.println("Maximum Runtime "+maximumRuntime);
       
       //Top 5 movies voted on IMDB
                        lines.stream()
    		            .skip(1)
    		            .map(line -> Arrays.asList(line.split(";")) )
    		            .filter(movie -> {String imdbVotes=movie.get(9).trim();
    		                              return !imdbVotes.equals("");})     		                              
    		            .sorted((movie1,movie2) -> {String m1Votes=movie1.get(9).trim();
    		                                        String m2Votes=movie2.get(9).trim();
    		                                        return Integer.valueOf(m2Votes).compareTo(Integer.valueOf(m1Votes));} )
    		            .limit(5)
                        .forEach(movie -> {System.out.println(movie.get(0)+" --- "+movie.get(9));});
        
        
        
	}

}
