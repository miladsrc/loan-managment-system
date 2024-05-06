package domain;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SoftDelete
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Loan extends BaseEntity<Long> {


    @Enumerated(EnumType.ORDINAL)
    @NotNull
    LoanType loanType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    @NotNull
    Student student;

    @Column(name = "total_amount",updatable = false)
    @NotNull
    long amount;

    @Enumerated
    Grade grade;

    @NotNull
    @Column(columnDefinition = "DATE", name = "loan_date")
    LocalDate date;

    @Column(name = "loan_checked")
    @ColumnDefault ( "false" )
    boolean checkOut;

    @OneToMany(mappedBy="loan",fetch = FetchType.LAZY)
    List<Refund> refundList=new ArrayList<> ();


    //----------------------------------------------------------------


    /*  TODO this is a method helper to provide bidirectional mapping */
    public void addRefund(Refund refund){
        refundList.add(refund);
        refund.setLoan ( this );
    }


    @Builder
    public Loan(Long aLong, LoanType loanType, Student student, long amount, Grade grade, LocalDate date, boolean checkOut, List<Refund> refundList) {
        super ( aLong );
        this.loanType = loanType;
        this.student = student;
        this.amount = amount;
        this.grade = grade;
        this.date = date;
        this.checkOut = checkOut;
        this.refundList = refundList;
    }

}
