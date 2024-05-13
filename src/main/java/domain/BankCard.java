package domain;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SoftDelete(columnName = "isDelete")
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankCard extends BaseEntity<Long> {

    @NotNull
    @Enumerated
    Bank bank;

    @OneToOne(cascade = CascadeType.ALL)
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Override
    public String toString() {
        return "BankCard{" +
                "bank=" + bank +
                ", student=" + student.getId() +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv2=" + cvv2 +
                ", expireDate=" + expireDate +
                ", balance=" + balance +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankCard bankCard)) return false;
        return cvv2 == bankCard.cvv2 && balance == bankCard.balance && bank == bankCard.bank && Objects.equals(student, bankCard.student) && Objects.equals(cardNumber, bankCard.cardNumber) && Objects.equals(expireDate, bankCard.expireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bank, student, cardNumber, cvv2, expireDate, balance);
    }
}
