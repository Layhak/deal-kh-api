package co.istad.dealkh.features.discounttype;

import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.DiscountType;
import co.istad.dealkh.features.discount.DiscountRepository;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeRequest;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeResponse;
import co.istad.dealkh.features.discounttype.dto.DiscountTypeUpdateRequest;
import co.istad.dealkh.mapper.DiscountTypeMapper;
import co.istad.dealkh.validator.formatter.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountTypeServiceImpl implements DiscountTypeService {

    private final DiscountTypeRepository discountTypeRepository;
    private final DiscountTypeMapper discountTypeMapper;
    private final DiscountRepository discountRepository;

    @Override
    public DiscountTypeResponse createDiscountType(DiscountTypeRequest discountTypeRequest) {

        if (discountTypeRepository.existsByName(discountTypeRequest.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Discount type with name %s already exists", discountTypeRequest.name()));
        }

        String slug = SlugFormatter.formatSlug(discountTypeRequest.name());

        DiscountType newDiscountType = discountTypeMapper.mapRequestToDiscountType(discountTypeRequest);

        newDiscountType.setSlug(slug);

        return discountTypeMapper.mapToDiscountTypeResponse(discountTypeRepository.save(newDiscountType));
    }

    @Override
    public List<DiscountTypeResponse> getAllDiscountType() {
        return discountTypeRepository.findAllByOrderBySortOrderAsc()
                .stream()
                .map(discountTypeMapper::mapToDiscountTypeResponse)
                .toList();
    }

    @Override
    public DiscountTypeResponse getDiscountTypeByName(String name) {
        DiscountType discountType = discountTypeRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Discount type with name %s not found", name)));

        return discountTypeMapper.mapToDiscountTypeResponse(discountType);
    }

    @Override
    public DiscountTypeResponse updateDiscountType(String name, DiscountTypeUpdateRequest discountTypeUpdateRequest) {

        DiscountType discountType = discountTypeRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Discount type with name %s not found", name)));

        discountTypeMapper.mapDiscountToUpdateRequest(discountType, discountTypeUpdateRequest);

        return discountTypeMapper.mapToDiscountTypeResponse(discountTypeRepository.save(discountType));
    }

    @Override
    public void deleteDiscountType(String name) {

        DiscountType discountType = discountTypeRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Discount type with name %s not found", name)));

        // Find all discounts referencing this discount type
        List<Discount> discounts = discountRepository.findByDiscountType(discountType);

        discountTypeRepository.delete(discountType);
    }
}
