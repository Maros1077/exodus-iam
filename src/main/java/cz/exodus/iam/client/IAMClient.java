package cz.exodus.iam.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.exodus.iam.db.repository.IdentityTagRepository;
import cz.exodus.iam.rest.CreateIdentityRequest;
import cz.exodus.iam.rest.CreateIdentityResponse;
import cz.exodus.jsend.network.client.BaseJSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class IAMClient extends BaseJSendClient {

    private static final String SERVICE_NAME = "iam-service";

    @Autowired
    public IAMClient(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        super(webClientBuilder.baseUrl("http://localhost:8080").build(), SERVICE_NAME, objectMapper);
    }

    protected IAMClient(WebClient webClient, String serviceName, ObjectMapper objectMapper) {
        super(webClient, serviceName, objectMapper);
    }

    public Mono<CreateIdentityResponse> getLogin(CreateIdentityRequest request) {
        return executeRequest("/exodus/v1/login", request, CreateIdentityResponse.class);
    }
}
