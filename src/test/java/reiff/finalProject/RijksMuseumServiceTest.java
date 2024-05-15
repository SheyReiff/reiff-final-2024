package reiff.finalProject;
import com.andrewoid.ApiKey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RijksMuseumServiceTest {

    @Test
    public void artObjects(){

        //given
        ApiKey key = new ApiKey();
        RijksMuseumService service = new RijksMuseumServiceFactory().getService();

        //when
        ArtObjects objects = service.artObjectsPageNumber(
                key.get(),
                1
                ).blockingGet();

        //then
        ArtObject artObjects = objects.artObjects[0];
        assertNotNull(artObjects.longTitle);
        assertNotNull(artObjects.principalOrFirstMaker);
        assertNotNull(artObjects.title);
        assertNotNull(artObjects.webImage);


    }

    @Test
    public void artObjectsSearch(){

        //given
        ApiKey key = new ApiKey();
        RijksMuseumService service = new RijksMuseumServiceFactory().getService();

        //when
        ArtObjects objects = service.artObjectsSearch(
                key.get(),
                "Manjushri",
                1
        ).blockingGet();

        //then
        ArtObject artObjects = objects.artObjects[0];
       assertEquals("De bodhisattva Manjushri", artObjects.title);

    }

    @Test
    public void artObjectsArtist(){

        //given
        ApiKey key = new ApiKey();
        RijksMuseumService service = new RijksMuseumServiceFactory().getService();

        //when
        ArtObjects objects = service.artObjectsArtist(
                key.get(),
                "Wenzel Jamnitzer",
                1
        ).blockingGet();

        //then
        ArtObject artObjects = objects.artObjects[0];
        assertEquals("Wenzel Jamnitzer", artObjects.principalOrFirstMaker);


    }

}
