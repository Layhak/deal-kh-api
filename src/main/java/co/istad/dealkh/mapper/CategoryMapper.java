package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.features.category.dto.CategoryCreateRequest;
import co.istad.dealkh.features.category.dto.CategoryResponse;
import co.istad.dealkh.features.category.dto.CategoryUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse mapCategoryToCategoryResponse(Category category);
    Category mapCategoryRequestToCategory(CategoryCreateRequest categoryCreateRequest);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCategoryUpdateRequest(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);
}
