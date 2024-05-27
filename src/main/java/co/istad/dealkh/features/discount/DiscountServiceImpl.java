package co.istad.dealkh.features.discount;


import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.features.discount.dto.DiscountCreateRequest;
import co.istad.dealkh.features.discount.dto.DiscountResponseDetail;
import co.istad.dealkh.features.discount.dto.DiscountUpdateRequest;
import co.istad.dealkh.mapper.DiscountMapper;
import co.istad.dealkh.specification.filter.DiscountFilter;
import co.istad.dealkh.specification.filter.DiscountSpecification;
import co.istad.dealkh.specification.filter.PageFilter;
import co.istad.dealkh.paging.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    @Override
    public DiscountResponseDetail createDiscount(DiscountCreateRequest discountCreateRequest) {

        if (discountRepository.existsByName(discountCreateRequest.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Discount name already exists");
        }

        if (discountRepository.existsByDiscountPercentage(discountCreateRequest.discountPercentage())){
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

        Discount discount = discountRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount name not found!"));

        DiscountResponseDetail discountResponseDetail = discountMapper.mapDiscountToResponseDetail(discount);
        return Optional.of(discountResponseDetail);
    }

    @Override
    public List<DiscountResponseDetail> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(discountMapper::mapDiscountToResponseDetail)
                .toList();
    }

    @Override
    public PageResponse<DiscountResponseDetail> filterDiscount(Map<String, String> params) {

        DiscountFilter discountFilter = new DiscountFilter();

        if (params.containsKey("name")){
            String name = params.get("name");
            discountFilter.setName(name);
        }

        if (params.containsKey("discountPercentage")){
            String discountPercentage = params.get("discountPercentage");
            discountFilter.setDiscountPercentage(Double.parseDouble(discountPercentage));
        }

        int pageLimit = PageFilter.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageFilter.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageFilter.PAGE_LIMIT));
        }

        int pageNumber = PageFilter.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageFilter.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageFilter.PAGE_NUMBER));
        }

        DiscountSpecification specification = new DiscountSpecification(discountFilter);
        Pageable pageable = PageFilter.getPageable(pageNumber, pageLimit);

        Page<DiscountResponseDetail> page = discountRepository.findAll(specification, pageable)
                .map(discountMapper::mapDiscountToResponseDetail);

        return new PageResponse<>(page);

    }

    @Override
    public DiscountResponseDetail updateDiscountById(Long id, DiscountUpdateRequest discountUpdateRequest) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount id not found!"));

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
