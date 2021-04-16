package net.droth.strinder.core.mapper;

import net.droth.strinder.core.entity.UserPairEntity;
import net.droth.strinder.core.model.UserPair;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Service
public final class UserPairEntityMapper implements Mapper<UserPairEntity, UserPair> {

    private final TypeMap<UserPairEntity, UserPair> typeMap;

    public UserPairEntityMapper(final ModelMapper modelMapper) {
        this.typeMap = modelMapper.createTypeMap(UserPairEntity.class, UserPair.class);
    }

    @Override
    public UserPair map(final UserPairEntity source) {
        return typeMap.map(source);
    }
}
