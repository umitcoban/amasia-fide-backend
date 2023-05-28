package com.umityasincoban.amasia_fide.mapper;

import com.umityasincoban.amasia_fide.dto.CityDTO;
import com.umityasincoban.amasia_fide.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CityMapper {
    public static final CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);
    CityDTO toCityDTO(City city);
    List<CityDTO> toCityDTOs(List<City> cities);
    City toCity(CityDTO cityDTO);

}
