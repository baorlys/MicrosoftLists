package org.example.microsoftlists.view.dto;

import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.ModelMapper;

public class MapperUtil {
    private MapperUtil() {
        // Empty Constructor
    }
    public static final ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
