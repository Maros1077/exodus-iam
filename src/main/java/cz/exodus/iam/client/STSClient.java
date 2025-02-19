package cz.exodus.iam.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.exodus.iam.client.model.IssueRequest;
import cz.exodus.iam.client.model.IssueResponse;
import cz.exodus.iam.rest.CreateIdentityRequest;
import cz.exodus.iam.rest.CreateIdentityResponse;
import cz.exodus.jsend.network.client.BaseJSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class STSClient extends BaseJSendClient {

    @Value("${service.sts.url}")
    private static final String SERVICE_NAME = "sts-service";

    @Autowired
    public STSClient(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        super(webClientBuilder.baseUrl("http://localhost:8081").build(), SERVICE_NAME, objectMapper);
    }

    protected STSClient(WebClient webClient, String serviceName, ObjectMapper objectMapper) {
        super(webClient, serviceName, objectMapper);
    }

    public Mono<IssueResponse> issueToken(IssueRequest request) {
        return executeRequest("/sts/v1/int/issue", request, IssueResponse.class);
    }
}
