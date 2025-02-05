package kw.zeropick.product.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.repository.MemberJpaRepository;
import kw.zeropick.product.domain.Bookmark;
import kw.zeropick.product.domain.Compare;
import kw.zeropick.product.domain.Ingredient;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.dto.ArtificialSweetDto;
import kw.zeropick.product.dto.IngredientDto;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.dto.ReviewInfoDto;
import kw.zeropick.product.dto.ReviewTagDto;
import kw.zeropick.product.dto.request.ProductSearchRequest;
import kw.zeropick.product.repository.BookmarkJpaRepository;
import kw.zeropick.product.repository.CompareJpaRepository;
import kw.zeropick.product.repository.ProductJpaRepository;
import kw.zeropick.review.domain.ReviewTag;
import kw.zeropick.review.repository.ReviewJpaRepository;
import kw.zeropick.review.repository.ReviewTagJpaRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductJpaRepository productJpaRepository;
    private final CompareJpaRepository compareJpaRepository;
    private final BookmarkJpaRepository bookmarkJpaRepository;

    private final MemberJpaRepository memberJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
    private final ReviewTagJpaRepository reviewTagJpaRepository;

    @Override
    @Transactional
    public ProductDto productDetail(Long productId) {
//      찜 여부와 비교를 사용하기 위해 회원 적용시 수정 필요
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + productId));

        product.incrementViewCount();
        productJpaRepository.save(product);

        ProductDto productDto = toProductDto(product, null);
        List<ArtificialSweetDto> artificialSweetDtos = new ArrayList<>();
        if(product.getIngredient().getAllulose() != null && product.getIngredient().getAllulose() != 0){
            ArtificialSweetDto artificialSweetDto = ArtificialSweetDto.builder()
                    .sweetName("알룰로오스")
                    .sweetDetail("알룰로오스 설명")
                    .sweetPoint("알룰로오스 특징")
                    .sweetWarning("알룰로오스 경고")
                    .build();
            artificialSweetDtos.add(artificialSweetDto);
        }
        if(product.getIngredient().getErythritol() != null && product.getIngredient().getErythritol() != 0){
            ArtificialSweetDto artificialSweetDto = ArtificialSweetDto.builder()
                    .sweetName("에리트리톨")
                    .sweetDetail("에리트리톨 설명")
                    .sweetPoint("에리트리톨 특징")
                    .sweetWarning("에리트리톨 경고")
                    .build();
            artificialSweetDtos.add(artificialSweetDto);
        }
        productDto.setArtificialSweets(artificialSweetDtos);

        // 상품에 대한 리뷰 태그 정보
        List<ReviewTag> reviewTags = reviewTagJpaRepository.findAllByProductId(productId);
        List<ReviewTagDto> reviewTagDtos = reviewTags.stream()
                .map(rt -> ReviewTagDto.builder()
                        .tagCount(rt.getTagCount())
                        .positiveTagEnum(rt.getPositiveTagEnum())
                        .negativeTagEnum(rt.getNegativeTagEnum())
                        .positiveNegative(rt.getPositiveNegative())
                        .build())
                .toList();

        productDto.setReviewTags(reviewTagDtos);

        // 별점 정보
        ReviewInfoDto reviewInfoDto = ReviewInfoDto.builder()
                .oneStar(reviewJpaRepository.countByProductIdAndRating(productId, 1L).intValue())
                .twoStar(reviewJpaRepository.countByProductIdAndRating(productId, 2L).intValue())
                .threeStar(reviewJpaRepository.countByProductIdAndRating(productId, 3L).intValue())
                .fourStar(reviewJpaRepository.countByProductIdAndRating(productId, 4L).intValue())
                .fiveStar(reviewJpaRepository.countByProductIdAndRating(productId, 5L).intValue())
                .build();

        productDto.setReviewInfo(reviewInfoDto);

        return productDto;
    }

    @Override
    public Page<ProductDto> productSearch(Long memberId, int page, int size, ProductSearchRequest productSearchRequest) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        // 페이징
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = productJpaRepository.searchProducts(productSearchRequest, pageable);

        Page<ProductDto> productDtos = products.map(product -> toProductDto(product, member));

        return productDtos;
    }


    @Override
    @Transactional
    public void bookmark(Long productId, Long memberId) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + productId));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        if(bookmarkJpaRepository.existsByProductAndMember(product, member)){
            throw new RuntimeException("이미 좋아요한 상품입니다.");
        }

        Bookmark bookmark = new Bookmark(member, product);
        bookmarkJpaRepository.save(bookmark);

        product.incrementBookmarkCount();
        productJpaRepository.save(product);
    }

    @Override
    @Transactional
    public void undoBookmark(Long productId, Long memberId) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + productId));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        Bookmark bookmark = bookmarkJpaRepository.findByProductAndMember(product, member)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품 찜 기록이 존재하지 않습니다."));

        bookmarkJpaRepository.delete(bookmark);

        product.decrementBookmarkCount();
        productJpaRepository.save(product);
    }

    @Override
    public Page<ProductDto> bookmarkProductList(Long memberId, int page, int size) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        // 페이징
        Pageable pageable = PageRequest.of(page, size);

        // 페이징된 결과 받기
        Page<Bookmark> bookmarkPage = bookmarkJpaRepository.findAllByMember(member, pageable);

        // Bookmark -> ProductDto로 변환
        Page<ProductDto> productDtoPage = bookmarkPage.map(bookmark -> toProductDto(bookmark.getProduct(), member));

        return productDtoPage;
    }




    @Override
    @Transactional
    public void compare(Long productId, Long memberId) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + productId));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        List<ProductDto> productDtos = compareJpaRepository.findAllByMemberId(member.getId())
                .stream()
                .map(compare -> toProductDto(compare.getProduct(), member))
                .toList();

        if(productDtos.size()==3){
            throw new RuntimeException("비교 상품은 3개까지 넣을 수 있습니다.");
        }

        if(compareJpaRepository.existsByProductAndMember(product, member)){
            throw new RuntimeException("이미 비교에 넣은 상품입니다.");
        }



        Compare compare = new Compare(member, product);
        compareJpaRepository.save(compare);
    }

    @Override
    @Transactional
    public void undoCompare(Long productId, Long memberId) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + productId));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        Compare compare = compareJpaRepository.findByProductAndMember(product, member)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품은 비교 목록에 없습니다."));

        compareJpaRepository.delete(compare);
    }



    @Override
    public List<ProductDto> compareProductList(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        List<ProductDto> productDtos = compareJpaRepository.findAllByMemberId(member.getId())
                .stream()
                .map(compare -> toProductDto(compare.getProduct(), member))
                .toList();

        return productDtos;
    }

    // ProductDto 변환
    private ProductDto toProductDto(Product product, Member member) {
        boolean isBookmarked = member != null && bookmarkJpaRepository.existsByProductAndMember(product, member);
        boolean isCompared = member != null && compareJpaRepository.existsByProductAndMember(product, member);

        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .category(product.getCategory())
                .zeroSugar(product.getZeroSugar())
                .zeroKcal(product.getZeroKcal())
                .price(product.getPrice())
                .starRate(product.getStarRate())
                .viewCount(product.getViewCount())
                .imageUrl(product.getImageUrl())
                .productLink(product.getProductLink())
                .bookmarkCount(product.getBookmarkCount())
                .reviewCount(product.getReviewCount())
                .bookmarked(isBookmarked)
                .compared(isCompared)
                .ingredient(toIngredientDto(product.getIngredient()))
                .build();
    }


    // IngredientDto 변환
    private IngredientDto toIngredientDto(Ingredient ingredient) {
        return IngredientDto.builder()
                .kcal(ingredient.getKcal())
                .carb(ingredient.getCarb())
                .sweet(ingredient.getSweet())
                .protein(ingredient.getProtein())
                .fat(ingredient.getFat())
                .transFat(ingredient.getTransFat())
                .saturatedFat(ingredient.getSaturatedFat())
                .natrium(ingredient.getNatrium())
                .cholesterol(ingredient.getCholesterol())
                .allulose(ingredient.getAllulose())
                .erythritol(ingredient.getErythritol())
                .build();
    }

    @Override
    @Transactional
    public boolean isBookmarkedByUser(Long productId, Long memberId) {
        return bookmarkJpaRepository.existsByProductIdAndMemberId(productId, memberId);
    }

    @Override
    @Transactional
    public boolean isComparedByUser(Long productId, Long memberId) {
        return compareJpaRepository.existsByProductIdAndMemberId(productId, memberId);
    }

    @Override
    @Transactional
    public List<Product> findTopProductsByPopularity(int limit) {
        return productJpaRepository.findTopProductsByPopularity()
                .stream()
                .limit(limit)
                .toList();
    }

}
