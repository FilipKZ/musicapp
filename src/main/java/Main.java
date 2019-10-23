import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.musicapp.SpotifyConnect;
import org.dizitart.no2.*;
import org.dizitart.no2.filters.Filters;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        SpotifyConnect sc = new SpotifyConnect();
        List<String> artists = sc.getArtistsNamesList("Metallica");
        List<String> tracks = sc.getTracksList("Passenger");
        System.out.println(artists);
        System.out.println(tracks);

//        Nitrite db = Nitrite.builder()
//                .compressed()
//                .filePath("/tmp/test.db")
//                .openOrCreate("user", "password");
//        NitriteCollection collection = db.getCollection("test");
//        Document doc = Document.createDocument("firstName", "John")
//                .put("lastName", "Doe")
//                .put("note", "jakas notatka");
//
//        Document doc2 = Document.createDocument("firstName", "John")
//                .put("lastName", "Ford")
//                .put("note", "bla bla");
//
//        collection.insert(doc, doc2);
//
//        NitriteCollection collection1 = db.getCollection("test");
//
//        Cursor cursor = collection1.find(Filters.eq("firstName", "John"));
//        for (Document document: cursor) {
//            Object note = document.get("note");
//            String s = note.toString();
//            System.out.println(s);
//        }
//
//        collection1.drop();

    }
}
