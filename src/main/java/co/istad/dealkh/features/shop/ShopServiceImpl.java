package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import co.istad.dealkh.mapper.ShopMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopTypeRepository shopTypeRepository;
    private final ShopMapper shopMapper;

    @Override
    public ShopResponse createShop(ShopRequest shopRequest) {
        if(shopRepository.existsByPhoneNumber(shopRequest.phoneNumber())){
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT, "Phone number already in use!"
            );
        }

        if(shopRepository.existsByEmail(shopRequest.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already in use!"
            );
        }

        ShopType shopType = shopTypeRepository.findById(shopRequest.shopTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop type not found!"));

        Shop newShop = shopMapper.toShop(shopRequest);
        newShop.setShopType(shopType);



        return null;
    }
}
