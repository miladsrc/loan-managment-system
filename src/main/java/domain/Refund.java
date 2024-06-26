package domain;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SoftDelete
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Refund extends BaseEntity<Long> {

    @NotNull
    @Min(1)
    @Column(name = "refund_num")
    int refundNum;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "loan_id")
    Loan loan;

    @NotNull
    @Min(0)
    @Column(name = "refund_amount")
    Double price;

    @NotNull
    @Column(columnDefinition = "DATE", name = "refund_date")
    LocalDate date;

    @Column(name = "is_checked")
    boolean checkout;


    @Builder
    public Refund(Long aLong, int refundNum, Loan loan, Double price, LocalDate date, boolean checkout) {
        super(aLong);
        this.refundNum = refundNum;
        this.loan = loan;
        this.price = price;
        this.date = date;
        this.checkout = checkout;
    }
}
