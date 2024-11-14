package kw.zeropick.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NegativeTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "negativeTag_id")
    private Long id;

    private String tagTitle;

    private Integer tagCount;

    @Enumerated(EnumType.STRING)
    private NegativeTagEnum tagNnum;
}
