package com.pizzeria.bff.requester;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.pizzeria.bff.dto.PedidoDTO;
import java.util.List;

@Service
public class PedidoRequesterImp implements PedidoRequester {

    @Value("${pathPedidos}")
    private String urlPedidos;

    private final RestTemplate restTemplate;

    public PedidoRequesterImp(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    private HttpHeaders headers(String auth) {
        HttpHeaders headers = new HttpHeaders();
        if (auth != null && !auth.isBlank()) {
            headers.set(HttpHeaders.AUTHORIZATION, auth);
        }
        return headers;
    }

    @Override
    public ResponseEntity<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) throws Exception {
        HttpEntity<PedidoDTO> entity = new HttpEntity<>(pedidoDTO);
        return restTemplate.exchange(urlPedidos + "/crear", HttpMethod.POST, entity, PedidoDTO.class);
    }

    @Override
    public ResponseEntity<List> listarPedidos(String auth) throws Exception {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers(auth));
            return restTemplate.exchange(urlPedidos + "/listar", HttpMethod.GET, entity, List.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @Override
    public ResponseEntity<PedidoDTO> actualizarEstado(Long id, String estado, String auth) throws Exception {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers(auth));
            return restTemplate.exchange(
                    urlPedidos + "/" + id + "/estado?estado=" + estado,
                    HttpMethod.PUT,
                    entity,
                    PedidoDTO.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @Override
    public ResponseEntity<Void> eliminarPedido(Long id, String auth) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers(auth));
            restTemplate.exchange(urlPedidos + "/" + id, HttpMethod.DELETE, entity, Void.class);
            return ResponseEntity.noContent().build();
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
public ResponseEntity<PedidoDTO> actualizarPedido(Long id, PedidoDTO pedidoDTO, String auth) throws Exception {
    try {
        HttpEntity<PedidoDTO> entity = new HttpEntity<>(pedidoDTO, headers(auth));
        return restTemplate.exchange(
                urlPedidos + "/" + id,
                HttpMethod.PUT,
                entity,
                PedidoDTO.class);
    } catch (HttpStatusCodeException e) {
        return ResponseEntity.status(e.getStatusCode()).build();
    }
}
}