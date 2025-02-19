package cz.exodus.iam.controller;

import cz.exodus.iam.exception.IAMException;
import cz.exodus.iam.rest.*;
import cz.exodus.iam.service.IAMService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cz.exodus.iam.controller.IAMEndpoints.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(IDENTITY_EXT_PATH)
public class IAMController {

    private final IAMService iamService;

    @PostMapping(CREATE_IDENTITY_PATH)
    public CreateIdentityResponse createIdentity(@RequestBody CreateIdentityRequest request, HttpServletResponse response) throws IAMException {
        log.debug("CREATE IDENTITY START");
        try {
            CreateIdentityResponse result = iamService.createIdentity(request.getTags(), request.getAuthPoints(), request.getApplication());
            log.info("CREATE IDENTITY SUCCESS with idid: " + result.getIdid());
            return result;
        } catch (IAMException e) {
            log.info("CREATE IDENTITY FAILED for " + request.getTags());
            throw e;
        }
    }

    @PostMapping(RETRIEVE_IDENTITY_PATH)
    public RetrieveIdentityResponse retrieveIdentity(@RequestBody RetrieveIdentityRequest request, HttpServletResponse response) throws IAMException {
        log.debug("RETRIEVE IDENTITY START");
        try {
            RetrieveIdentityResponse result = iamService.retrieveIdentity(request.getApplication(), request.getIdentificationTag(), request.isRetrieveTags(), request.isRetrieveAuthPoints());
            log.info("RETRIEVED IDENTITY for " + result.getIdid());
            return result;
        } catch (IAMException e) {
            log.info("RETRIEVE IDENTITY FAILED for " + request.getIdentificationTag().getValue());
            throw e;
        }
    }

    @PostMapping(UPDATE_IDENTITY_PATH)
    public UpdateIdentityResponse updateIdentity(@RequestBody UpdateIdentityRequest request, HttpServletResponse response) throws IAMException {
        log.debug("UPDATE IDENTITY START");
        try {
            UpdateIdentityResponse result = iamService.updateIdentity(request.getApplication(), request.getIdentificationTag(), request.getTags(), request.getAuthPoints());
            log.info("UPDATED IDENTITY for " + request.getIdentificationTag().getValue());
            return result;
        } catch (IAMException e) {
            log.info("UPDATE IDENTITY FAILED for " + request.getIdentificationTag().getValue());
            throw e;
        }
    }

    @PostMapping(AUTH_PATH)
    public AuthResponse auth(@RequestBody AuthRequest request, HttpServletResponse response) throws IAMException {
        log.debug("AUTH START");
        try {
            AuthResponse result = iamService.auth(request.getIdentificationTag(), request.getAuthPoint(), request.getApplication(), request.getClientId(), request.getGrantType(),  request.getScope());
            log.info("AUTH SUCCESSFUL for " + request.getIdentificationTag().getValue());
            return result;
        } catch (IAMException e) {
            log.info("AUTH FAILED for " + request.getIdentificationTag().getValue());
            throw e;
        }
    }
}
