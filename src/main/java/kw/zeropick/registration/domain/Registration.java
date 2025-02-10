package kw.zeropick.registration.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kw.zeropick.common.domain.BaseEntity;
import kw.zeropick.member.domain.Member;
import kw.zeropick.product.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Registration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//단방향 연결
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotNull
    private String brand;

    @NotNull
    private String productName;

    @NotNull
    private Category category;

    private String ingredient;

    private String imageUrl;

    private String additional;

    private RegistrationStatus registrationStatus; // 등록 요청 처리 상태

    private String rejectionReason; // 반려 사유

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
