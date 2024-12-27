package kw.zeropick.product.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.repository.MemberJpaRepository;
import kw.zeropick.product.domain.Bookmark;
import kw.zeropick.product.domain.Compare;
import kw.zeropick.product.domain.Ingredient;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.dto.IngredientDto;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.repository.BookmarkJpaRepository;
import kw.zeropick.product.repository.CompareJpaRepository;
import kw.zeropick.product.repository.ProductJpaRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public ProductDto productDetail(Long productId) {
//      찜 여부와 비교를 사용하려면 회원이여야 하는데 그럼 상세 정보 기능은 회원과 비회원으로 나누어야 하는가?
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + productId));

        product.incrementViewCount();
        productJpaRepository.save(product);

        ProductDto productDto = toProductDto(product, null);

        return productDto;
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
    public List<ProductDto> bookmarkProductList(Long memberId, int page, int size) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

        // 페이징
        Pageable pageable = PageRequest.of(page, size);

        List<Bookmark> bookmarks = bookmarkJpaRepository.findAllByMember(member, pageable);

        List<ProductDto> productDtos = bookmarks.stream()
                .map(bookmark -> toProductDto(bookmark.getProduct(), member))
                .toList();

        return productDtos;
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

}
