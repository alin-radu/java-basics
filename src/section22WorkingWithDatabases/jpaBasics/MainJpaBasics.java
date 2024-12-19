package section22WorkingWithDatabases.jpaBasics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainJpaBasics {
    public static void main(String[] args) {
        try (
                EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("dev.lpa.music");
                EntityManager entityManager = sessionFactory.createEntityManager()
        ) {

            var transaction = entityManager.getTransaction();
            transaction.begin();

            // add item the DB
//            entityManager.persist(new Artist("Muddy Water"));

            // read item from the DB
//            Artist artist = entityManager.find(Artist.class, 203);
//            System.out.printf("---> artist: " + artist);

            // remove item from the db
//            entityManager.remove(artist);

            // update item from the db, v1
//            Artist artist = entityManager.find(Artist.class, 202);
//            System.out.printf("---> artist: " + artist);
//            artist.setArtistName("Muddy Waters");

            // update item from the db, v2
//            Artist artist = new Artist(202, "Muddy Water");
//            entityManager.merge(artist);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
