package domain;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SoftDelete
public class Student extends BaseEntity<Long> {

    @Size(min = 2, max = 50)
    @Column(length = 50)
    String firstname;

    @Size(min = 2, max = 50)
    @Column(length = 50)
    String lastname;

    @Pattern(regexp = "\\d{10}")
    @Column(length = 10)
    String nationalCode;

    @Pattern(regexp = "\\d{11}")
    @Column(length = 11)
    String phoneNumber;

    @Enumerated(EnumType.ORDINAL)
    City city;

    @Column
    boolean isMarried;

    @OneToOne
    Student partner;

    @Temporal(TemporalType.DATE)
    @Column
    Date EnteryDate;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(length = 100)
    String universityName;

    @NotNull
    @Enumerated
    TypeUniversity typeUniversity;

    @NotNull
    @Column(length = 50)
    String grade;

    @Column
    boolean dorm;

    @Column
    boolean isEducate;

    @Column
    int contractNum;

    @OneToMany(mappedBy ="student", cascade = CascadeType.ALL )
    List<BankCard> bankCardList=new ArrayList<> ();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    List<Loan> loanList=new ArrayList<> ();


    @Builder
    public Student(Long aLong, String firstname, String lastname, String nationalCode, String phoneNumber, City city, boolean isMarried, Student partner, Date enteryDate, String universityName, TypeUniversity typeUniversity, String grade, boolean dorm, boolean isEducate, int contractNum, List<BankCard> bankCardList, List<Loan> loanList) {
        super ( aLong );
        this.firstname = firstname;
        this.lastname = lastname;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.isMarried = isMarried;
        this.partner = partner;
        EnteryDate = enteryDate;
        this.universityName = universityName;
        this.typeUniversity = typeUniversity;
        this.grade = grade;
        this.dorm = dorm;
        this.isEducate = isEducate;
        this.contractNum = contractNum;
        this.bankCardList = bankCardList;
        this.loanList = loanList;
    }
}

