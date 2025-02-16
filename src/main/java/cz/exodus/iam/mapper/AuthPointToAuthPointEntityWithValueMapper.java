package cz.exodus.iam.mapper;

import cz.exodus.iam.db.entity.AuthPointTypeEntity;
import cz.exodus.iam.db.repository.AuthPointTypeRepository;
import cz.exodus.iam.exception.AuthPointTypeDoesNotExistsException;
import cz.exodus.iam.exception.IAMException;
import cz.exodus.iam.model.AuthPoint;
import cz.exodus.iam.model.AuthPointEntityWithValue;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AuthPointToAuthPointEntityWithValueMapper {

    private final AuthPointTypeRepository authPointTypeRepository;

    public List<AuthPointEntityWithValue> mapToEntityWithValue(List<AuthPoint> authPoints) throws IAMException {
        List<AuthPointEntityWithValue> result = new ArrayList<>();
        if (authPoints == null) {
            return result;
        }
        List<AuthPointTypeEntity> authPointEntities = getAuthPointEntities(authPoints);

        for (int i = 0; i < authPoints.size(); i++) {
            AuthPoint authPoint = authPoints.get(i);
            AuthPointTypeEntity authPointTypeEntity = authPointEntities.get(i);
            AuthPointEntityWithValue entityWithValue = new AuthPointEntityWithValue(authPointTypeEntity, authPoint.getValue());
            result.add(entityWithValue);
        }
        return result;
    }

    private List<AuthPointTypeEntity> getAuthPointEntities(List<AuthPoint> authPoints) throws IAMException {
        List<AuthPointTypeEntity> list = new ArrayList<>();
        for (AuthPoint authPoint : authPoints) {
            Optional<AuthPointTypeEntity> entity = Optional.ofNullable(getAuthPointByType(authPoint.getType()));
            if (entity.isEmpty()) {
                throw new AuthPointTypeDoesNotExistsException(authPoint.getType());
            }
            list.add(entity.get());
        }
        return list;
    }

    private AuthPointTypeEntity getAuthPointByType(String authPointName) {
        return authPointTypeRepository.findByName(authPointName);
    }
}
