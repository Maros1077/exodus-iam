package cz.exodus.iam.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.exodus.iam.client.model.IssueRequest;
import cz.exodus.iam.client.model.IssueResponse;
import cz.exodus.jsend.network.client.BaseJSendClient;
import cz.exodus.jsend.network.exception.JSendClientException;
import cz.exodus.jsend.network.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class STSClient extends BaseJSendClient {

    private static String STS_URL = "http://localhost:8081";
    private static final String STS_ISSUE_ENDPOINT = "/sts/v1/int/issue";
    private static final String SERVICE_NAME = "sts-service";

    @Autowired
    public STSClient(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        super(webClientBuilder.baseUrl(STS_URL).build(), SERVICE_NAME, objectMapper);
    }

    protected STSClient(WebClient webClient, String serviceName, ObjectMapper objectMapper) {
        super(webClient, serviceName, objectMapper);
    }

    public Result<IssueResponse, JSendClientException> issueToken(IssueRequest request) {
        return executePostRequestSync(STS_ISSUE_ENDPOINT, request, IssueResponse.class);
    }
}
