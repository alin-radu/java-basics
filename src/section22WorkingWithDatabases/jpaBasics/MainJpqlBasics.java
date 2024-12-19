package section22WorkingWithDatabases.jpaBasics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import section22WorkingWithDatabases.jpaBasics.music.Artist;

import java.util.List;
import java.util.stream.Stream;

public class MainJpqlBasics {
    public static void main(String[] args) {

        try (
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("dev.lpa.music");
                EntityManager em = emf.createEntityManager();
        ) {

            var transaction = em.getTransaction();
            transaction.begin();

            // 1
//            List<Artist> artists = getArtistsJPQL(em, "%Stev%");
//            artists.forEach(System.out::println);

            // 2
//            Stream<Tuple> names = getArtistsNames(em, "%Stev%");
//            names
//                    .map(a -> new Artist(a.get("id", Integer.class), (String) a.get("name")))
//                    .forEach(System.out::println);

            // 3
            List<Artist> artists = getArtistsByAlbumsJPQL(em, "%Greatest Hits%");
            artists.forEach(System.out::println);

            long count = artists.size();

            System.out.println("---> count: " + count);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static List<Artist> getArtistsJPQL(EntityManager em, String matchedValue) {
        String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1";
        var query = em.createQuery(jpql, Artist.class);
        query.setParameter(1, matchedValue);

        return query.getResultList();
    }

    private static Stream<Tuple> getArtistsNames(EntityManager em, String matchedValue) {
        String jpql = "SELECT a.artistId id, a.artistName as name FROM Artist a WHERE a.artistName LIKE ?1";
        var query = em.createQuery(jpql, Tuple.class);
        query.setParameter(1, matchedValue);

        return query.getResultStream();
    }

    private static List<Artist> getArtistsByAlbumsJPQL(EntityManager em, String matchedValue) {
        String jpql = "SELECT a FROM Artist a JOIN albums as album" +
                " WHERE album.albumName LIKE ?1 OR album.albumName LIKE ?2";
        var query = em.createQuery(jpql, Artist.class);
        query.setParameter(1, matchedValue);
        query.setParameter(2, "%Best of%");

        return query.getResultList();
    }
}
