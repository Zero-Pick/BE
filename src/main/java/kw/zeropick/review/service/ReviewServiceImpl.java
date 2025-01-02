package kw.zeropick.review.service;

import kw.zeropick.review.repository.ReviewJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewJPARepository reviewRepository;
}
