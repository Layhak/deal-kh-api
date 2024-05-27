package co.istad.dealkh.feature.shoptype;

import co.istad.dealkh.entity.ShopType;
import co.istad.dealkh.feature.shoptype.dto.ShopTypeRequest;
import co.istad.dealkh.feature.shoptype.dto.ShopTypeResponse;
import co.istad.dealkh.mapper.ShopTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ShopTypeServiceImpl implements ShopTypeService {
    private final ShopTypeRepository shopTypeRepository;
    private final ShopTypeMapper shopTypeMapper;

    @Override
    public List<ShopTypeResponse> getAllShopTypes() {
        List<ShopType> shopTypes = shopTypeRepository.findAll();
        return shopTypes.stream().map(shopTypeMapper::toShopTypeResponse).collect(Collectors.toList());
    }

    @Override
    public ShopTypeResponse createShopType(ShopTypeRequest shopTypeRequest) {
        ShopType shopType = shopTypeMapper.toShopType(shopTypeRequest);
        ShopType savedShopType = shopTypeRepository.save(shopType);
        return shopTypeMapper.toShopTypeResponse(savedShopType);
    }


    @Override
    public ShopTypeResponse updateShopType(Long id, ShopTypeRequest shopTypeRequest) {
        ShopType shopType = shopTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop Type not found"));
        shopType.setName(shopTypeRequest.name());
        shopType.setIcon(shopTypeRequest.icon());
        ShopType updatedShopType = shopTypeRepository.save(shopType);
        return shopTypeMapper.toShopTypeResponse(updatedShopType);
    }


    @Override
    public void deleteShopType(Long id) {
        ShopType shopType = shopTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop Type not found"));
        shopTypeRepository.delete(shopType);
    }

    @Override
    public ShopTypeResponse getShopTypeById(Long id) {
        ShopType shopType = shopTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop Type not found"));
        return shopTypeMapper.toShopTypeResponse(shopType);
    }

    @Override
    public ShopTypeResponse getShopTypeByName(String name) {
        ShopType shopType = shopTypeRepository.findByName(name).orElseThrow(() -> new RuntimeException("Shop Type not found"));
        return shopTypeMapper.toShopTypeResponse(shopType);
    }
}
