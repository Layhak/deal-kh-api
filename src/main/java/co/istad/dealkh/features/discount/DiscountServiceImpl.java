package co.istad.dealkh.features.discount;


import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.features.discounttype.DiscountTypeRepository;
import co.istad.dealkh.mapper.DiscountMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.specification.filter.DiscountFilter;
import co.istad.dealkh.specification.filter.DiscountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final DiscountTypeRepository discountTypeRepository;

    @Override
    public DiscountResponseDetail createDiscount(DiscountCreateRequest discountCreateRequest) {

        if (discountRepository.existsByDiscountPercentage(discountCreateRequest.discountPercentage()) && discountTypeRepository.existsById(discountCreateRequest.discountTypeId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Discount percentage already exists");
        }

        Discount newDiscount = discountMapper.mapDiscountRequestToDiscount(discountCreateRequest);
        return discountMapper.mapDiscountToResponseDetail(discountRepository.save(newDiscount));
    }

    @Override
    public Optional<DiscountResponseDetail> getDiscountById(Long id) {

        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount id not found!"));

        DiscountResponseDetail discountResponseDetail = discountMapper.mapDiscountToResponseDetail(discount);

        return Optional.of(discountResponseDetail);
    }

    @Override
    public Optional<DiscountResponseDetail> getDiscountByName(String name) {

//        Discount discount = discountRepository.findByName(name)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount name not found!"));
//
//        DiscountResponseDetail discountResponseDetail = discountMapper.mapDiscountToResponseDetail(discount);
//        return Optional.of(discountResponseDetail);
        return null;
    }

    @Override
    public PageResponse<DiscountResponseDetail> getAllDiscounts(int pageNumber, int size, String field, String order, Map<String, String> params) {

        DiscountFilter discountFilter = new DiscountFilter();
        pageNumber = Pagination.page_number;
        size = Pagination.page_limit;

        if (params.containsKey("name")) {
            String name = params.get("name");
            discountFilter.setName(name);
        }

        if (params.containsKey("discountPercentage")) {
            String discountPercentage = params.get("discountPercentage");
            discountFilter.setDiscountPercentage(Double.parseDouble(discountPercentage));
        }
        List<String> validFields = Arrays.asList("name", "discountPercentage", "createdAt", "updatedAt");
        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be id, name, discountPercentage, or createdAt");
        }
        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
        DiscountSpecification specification = new DiscountSpecification(discountFilter);
        Pageable pageable = Pagination.getPageable(pageNumber, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<DiscountResponseDetail> page = discountRepository.findAll(specification, pageable)
                .map(discountMapper::mapDiscountToResponseDetail);

        return new PageResponse<>(page);

    }

    @Override
    public DiscountResponseDetail updateDiscountById(Long id, DiscountUpdateRequest discountUpdateRequest) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount id not found!"));

        discount.setUpdatedAt(LocalDateTime.now());

        discountMapper.mapDiscountToUpdateRequest(discount, discountUpdateRequest);

        discount = discountRepository.save(discount);

        return discountMapper.mapDiscountToResponseDetail(discount);
    }

    @Override
    public void deleteDiscountById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount id not found!"));

        discountRepository.delete(discount);
    }
}
