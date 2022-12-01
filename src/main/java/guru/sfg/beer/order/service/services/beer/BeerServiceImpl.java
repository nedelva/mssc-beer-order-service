package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class BeerServiceImpl implements BeerService {

    private final RestTemplate restTemplate;
    private static final String BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";
    private static final String BEER_BY_UPC_PATH = "/api/v1/beerUpc/{upc}";
    private String beerServiceHost;

    public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getById(UUID beerId) {
        log.debug("Calling Beer Service, by ID '{}'", beerId);

        ResponseEntity<BeerDto> responseEntity = restTemplate
                .exchange(beerServiceHost + BEER_BY_ID_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() { }, (Object) beerId);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Optional<BeerDto> getByUpc(String upc) {
        log.debug("Calling Beer Service, by UPC '{}'", upc);

        ResponseEntity<BeerDto> responseEntity = restTemplate
                .exchange(beerServiceHost + BEER_BY_UPC_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() { }, (Object) upc);
        return Optional.ofNullable(responseEntity.getBody());
    }

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }
}
