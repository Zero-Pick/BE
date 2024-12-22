package kw.zeropick.product.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import kw.zeropick.member.domain.Member;
import kw.zeropick.member.repository.MemberJpaRepository;
import kw.zeropick.product.domain.Bookmark;
import kw.zeropick.product.domain.Compare;
import kw.zeropick.product.domain.Product;
import kw.zeropick.product.dto.ProductDto;
import kw.zeropick.product.repository.BookmarkJpaRepository;
import kw.zeropick.product.repository.CompareJpaRepository;
import kw.zeropick.product.repository.ProductJpaRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductJpaRepository productJpaRepository;
    private final CompareJpaRepository compareJpaRepository;
    private final BookmarkJpaRepository bookmarkJpaRepository;

    private final MemberJpaRepository memberJpaRepository;

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
    @Transactional
    public void compare(Long productId, Long memberId) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 상품 없음 id: " + productId));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id에 맞는 유저 없음 id: " + memberId));

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
    public List<ProductDto> bookmarkProductList(Long memberId) {
        return List.of();
    }

    @Override
    public List<ProductDto> compareProductList(Long memberId) {
        return List.of();
    }
}
