package kw.zeropick.product.service;

import kw.zeropick.product.domain.Product;
import kw.zeropick.product.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService{
    private final ProductJpaRepository productRepository;
    private final RestTemplate restTemplate;

    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/shop.json?query=";
    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Override
    @Transactional
    public void updateAllProducts() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            try {
                updateProductInfo(product);
                Thread.sleep(200); // 0.2초 대기 (초당 5건 제한)
            } catch (Exception e) {
                System.err.println("Error updating product: " + product.getProductName());
                e.printStackTrace();
            }
        }

    }

    @Transactional
    public void updateProductInfo(Product product) {
        String query = product.getProductName();
        String apiUrl = NAVER_API_URL + query + "&sort=asc";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getBody() == null) {
            System.err.println("API 응답이 null입니다: " + product.getProductName());
            return;
        }

        JSONObject jsonResponse = new JSONObject(response.getBody());

        if (!jsonResponse.has("items")) {
            System.err.println("API 응답에 'items' 필드 없음: " + response.getBody());
            return;
        }

        JSONArray items = jsonResponse.getJSONArray("items");

        if (items.length() == 0) {
            System.err.println("검색된 상품 없음: " + product.getProductName());
            return;
        }

        JSONObject firstItem = items.getJSONObject(0); // 최저가 상품

        // 필수 값 검증
        if (!firstItem.has("link") || !firstItem.has("image") || !firstItem.has("lprice")) {
            System.err.println("상품 데이터에 필수 필드 없음: " + firstItem.toString());
            return;
        }

        product.setProductLink(firstItem.getString("link"));
        product.setImageUrl(firstItem.getString("image"));
        product.setPrice(Integer.parseInt(firstItem.getString("lprice"))); // 가격이 문자열일 경우 처리

        productRepository.save(product);
    }

}
