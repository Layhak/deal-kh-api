package co.istad.dealkh.features.shoptype;

import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.features.shoptype.dto.ShopTypeCreateRequest;
import co.istad.dealkh.features.shoptype.dto.ShopTypeResponse;
import co.istad.dealkh.features.shoptype.dto.ShopTypeUpdateRequest;
import co.istad.dealkh.mapper.ShopTypeMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.specification.filter.PageFilter;
import co.istad.dealkh.specification.filter.ShopTypeFilter;
import co.istad.dealkh.specification.filter.ShopTypeSpecification;
import co.istad.dealkh.validator.category.NameFormatter;
import co.istad.dealkh.validator.category.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopTypeServiceImpl implements ShopTypeService {

    private final ShopTypeRepository shopTypeRepository;
    private final ShopTypeMapper shopTypeMapper;

    @Override
    public ShopTypeResponse createShopType(ShopTypeCreateRequest shopTypeCreateRequest) {

        if (shopTypeRepository.existsByName(shopTypeCreateRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Shop type name already exists");
        }


        String name = NameFormatter.formatName(shopTypeCreateRequest.name());
        String slug = SlugFormatter.formatSlug(shopTypeCreateRequest.name());

        if(shopTypeRepository.existsBySlug(slug)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Shop type name already exists");
        }

        ShopType newShopType = shopTypeMapper.mapShopTypeRequestToShopType(shopTypeCreateRequest);

        newShopType.setName(name);
        newShopType.setSlug(slug);

        return shopTypeMapper.mapShopTypeToShopTypeResponse(shopTypeRepository.save(newShopType));
    }

    @Override
    public Optional<ShopTypeResponse> getShopTypeByName(String name) {

        // Check format name request
        String nameRequest = NameFormatter.formatName(name);

        ShopType shopType = shopTypeRepository.findByName(nameRequest)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Shop type with name %s not found! ", nameRequest)));

        return Optional.of(shopTypeMapper.mapShopTypeToShopTypeResponse(shopType));
    }

    @Override
    public PageResponse<ShopTypeResponse> filterShopTypes(Map<String, String> params) {
        ShopTypeFilter shopTypeFilter = new ShopTypeFilter();
        int pageLimit = PageFilter.DEFAULT_PAGE_LIMIT;
        int pageNumber = PageFilter.DEFAULT_PAGE_NUMBER;

        if (params.containsKey("slug")) {
            String name = params.get("slug");
            shopTypeFilter.setName(name);
        }

        if (params.containsKey(PageFilter.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageFilter.PAGE_LIMIT));
        }

        if (params.containsKey(PageFilter.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageFilter.PAGE_NUMBER));
        }

        ShopTypeSpecification specification = new ShopTypeSpecification(shopTypeFilter);

        Pageable pageable = PageFilter.getPageable(pageNumber, pageLimit);

        Page<ShopTypeResponse> page = shopTypeRepository.findAll(specification, pageable)
                .map(shopTypeMapper::mapShopTypeToShopTypeResponse);

        return new PageResponse<>(page);
    }

    @Override
    public ShopTypeResponse updateShopTypeByName(String name, ShopTypeUpdateRequest shopTypeUpdateRequest) {

        // Check format name request
        String nameRequest = NameFormatter.formatName(name);

        ShopType shopType = shopTypeRepository.findByName(nameRequest)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Shop type with name %s not found! ", nameRequest)));

        shopTypeMapper.mapShopTypeUpdateRequest(shopType, shopTypeUpdateRequest);

        shopType = shopTypeRepository.save(shopType);

        return shopTypeMapper.mapShopTypeToShopTypeResponse(shopType);
    }

    @Override
    public void deleteShopTypeByName(String name) {

        // Check format name request
        String nameRequest = NameFormatter.formatName(name);

        ShopType shopType = shopTypeRepository.findByName(nameRequest)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Shop type with name %s not found! ", nameRequest)));

        shopTypeRepository.delete(shopType);
    }
}
