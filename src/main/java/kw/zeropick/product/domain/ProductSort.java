package kw.zeropick.product.domain;

public enum ProductSort {
    POPULARITY("인기순"),
    NEWEST("신상품순"),
    MOST_REVIEWED("리뷰 많은 순"),
    HIGHEST_RATED("별점 높은 순"),
    LOWEST_RATED("별점 낮은 순");

    private final String description;

    ProductSort(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
