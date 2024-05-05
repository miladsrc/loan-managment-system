package domain;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SoftDelete(columnName = "isDelete")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankCard extends BaseEntity<Long> {

    @NotNull
    @Enumerated
    Bank bank;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank-card-list")
    Student student;

    @NotNull
    @Pattern(regexp = "\\d{16}")
    @Column(length = 16)
    String cardNumber;

    @NotNull
    @Min(1000)
    @Max(9999)
    @Column
    int cvv2;

    @NotNull
    @Column(columnDefinition = "DATE")
    LocalDate expireDate;

    @NotNull
    @Min(0)
    @Column
    long balance;

    @Builder
    public BankCard(Long aLong, Bank bank, Student student, String cardNumber, int cvv2, LocalDate expireDate, long balance) {
        super ( aLong );
        this.bank = bank;
        this.student = student;
        this.cardNumber = cardNumber;
        this.cvv2 = cvv2;
        this.expireDate = expireDate;
        this.balance = balance;
    }

}
