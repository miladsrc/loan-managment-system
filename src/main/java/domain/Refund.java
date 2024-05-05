package domain;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SoftDelete;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SoftDelete
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Refund extends BaseEntity<Long> {

    @NotNull
    @Min(1)
    int refundNum;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "refund_id")
    Loan loan;

    @NotNull
    @Min(0)
    Double price;

    @NotNull
    @Column(columnDefinition = "DATE")
    LocalDate date;

    boolean checkout;


    @Builder
    public Refund(Long aLong, int refundNum, Loan loan, Double price, LocalDate date, boolean checkout) {
        super ( aLong );
        this.refundNum = refundNum;
        this.loan = loan;
        this.price = price;
        this.date = date;
        this.checkout = checkout;
    }
}
