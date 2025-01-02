package kw.zeropick.review.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kw.zeropick.review.service.ReviewService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "review", description = "리뷰 관련 API")
@Builder
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
}
