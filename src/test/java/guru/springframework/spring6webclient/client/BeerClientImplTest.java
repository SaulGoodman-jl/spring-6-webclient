package guru.springframework.spring6webclient.client;

import guru.springframework.spring6webclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void listBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeer().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerMap() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerMap().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerJson() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerJson().subscribe(response -> {
            System.out.println(response.toPrettyString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerDto() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDto().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeerById() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDto()
                .flatMap(dto -> beerClient.getBeerById(dto.getId()))
                .subscribe(byId -> {
                    System.out.println(byId);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);

    }

    @Test
    void getBeerByBeerStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.getBeerByBeerStyle("Pale Ale").subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void createBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO beerDTO = BeerDTO.builder().beerName("111").beerStyle("style").upc("upc").price(BigDecimal.TEN).build();

        beerClient.createBeer(beerDTO).subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void updateBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDto()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName("New Name"))
                .flatMap(dto -> beerClient.updateBeer(dto))
                .subscribe(response -> {
                    System.out.println(response);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
}