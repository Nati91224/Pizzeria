package com.pizzeria.bff.requester;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.pizzeria.bff.dto.ProductoDTO;
import java.util.List;

@Service
public class ProductoRequesterImp implements ProductoRequester {

    @Value("${pathProductos}")
    private String urlProductos;

    @Value("${pathPedidos}")
    private String urlPedidos;

    private final RestTemplate restTemplate;

    public ProductoRequesterImp(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public ResponseEntity<ProductoDTO> crearProducto(ProductoDTO productoDTO) throws Exception {
        HttpEntity<ProductoDTO> entity = new HttpEntity<>(productoDTO);
        return restTemplate.exchange(urlProductos + "/crear", HttpMethod.POST, entity, ProductoDTO.class);
    }

    @Override
    public ResponseEntity<List> listarProductos() throws Exception {
        return restTemplate.exchange(urlProductos + "/listar", HttpMethod.GET, null, List.class);
    }

    @Override
    public ResponseEntity<Void> eliminarProducto(Long id, String auth) throws Exception {
        if (auth == null || auth.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, auth);
            restTemplate.exchange(urlPedidos + "/verificar-admin", HttpMethod.GET,
                    new HttpEntity<>(headers), Void.class);

            restTemplate.exchange(urlProductos + "/eliminar/" + id, HttpMethod.DELETE, null, Void.class);
            return ResponseEntity.noContent().build();
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}