package reiff.finalproject; //it did not let me name my package reiff.final

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <a href="https://data.rijksmuseum.nl/object-metadata/api/#collection-api">...</a>
 */

public interface RijksMuseumService {
    @GET("/api/nl/collection")
    Single<ArtObjects> artObjectsPageNumber(
            @Query("key") String apiKey,
            @Query("p") int pageNumber);

    @GET("/api/nl/collection")
    Single<ArtObjects> artObjectsSearch(
            @Query("key") String apiKey,
            @Query("q") String search,
            @Query("p") int pageNumber);

    @GET("/api/nl/collection")
    Single<ArtObjects> artObjectsArtist(
            @Query("key") String apiKey,
            @Query("involvedMaker") String artist,
            @Query("p") int pageNumber);
}

