package com.pizzeria.bff.requester;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    @Override
    public ResponseEntity<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) throws Exception {
        HttpEntity<PedidoDTO> entity = new HttpEntity<>(pedidoDTO);
        return restTemplate.exchange(urlPedidos + "/crear", HttpMethod.POST, entity, PedidoDTO.class);
    }

    @Override
    public ResponseEntity<List> listarPedidos() throws Exception {
        return restTemplate.exchange(urlPedidos + "/listar", HttpMethod.GET, null, List.class);
    }

    @Override
    public ResponseEntity<PedidoDTO> actualizarEstado(Long id, String estado) throws Exception {
        return restTemplate.exchange(
                urlPedidos + "/actualizar/" + id + "?estado=" + estado,
                HttpMethod.PUT,
                null,
                PedidoDTO.class);
    }
    @Override
    public ResponseEntity<Void> eliminarPedido(Long id) {
        try {
            String url = urlPedidos + "/" + id; // idéntico al MS
            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
            return ResponseEntity.noContent().build(); // 204 si OK
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build(); // 404 si no existe
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 otro error
        }
    }
}
