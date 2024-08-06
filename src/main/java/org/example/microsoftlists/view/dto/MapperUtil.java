package org.example.microsoftlists.view.dto;

import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

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

    public static <D> List<D> mapList(List<?> source, Class<D> destinationClass) {
        return source.stream()
                .map(element -> mapper.map(element, destinationClass))
                .collect(Collectors.toList());
    }
}
