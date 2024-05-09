package domain;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SoftDelete
@FieldDefaults(level = AccessLevel.PRIVATE)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student extends BaseEntity<Long> {

    @Size(min = 2, max = 50)
    @Column(length = 50 )
    String firstName;

    @Size(min = 2, max = 50)
    @Column(length = 50)
    String lastName;

    @Size(min = 2, max = 50)
    @Column(length = 50)
    String fatheName;

    @Size(min = 2, max = 50)
    @Column(length = 50)
    String motherName;

//    @Pattern(regexp = "\\d{10}")
    @Column(length = 10)
    String nationalCode;

//    @Pattern(regexp = "\\d{11}")
    @Column(length = 11)
    String phoneNumber;

    @Column(length = 11)
    String studentNumber;


    @Enumerated(EnumType.ORDINAL)
    City city;

    @Column
    boolean isMarried;

    @OneToOne
    Student partner;

    @Temporal(TemporalType.DATE)
    @Column
    LocalDate EnteryDate;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(length = 100)
    String universityName;

    @NotNull
    @Enumerated
    TypeUniversity typeUniversity;

    @NotNull
    @Column(length = 50)
    Grade grade;

    @Column
    boolean dorm;

    @Column
    boolean isEducate;

    @Column
    int contractNum;

    @Column
    String password;

    @OneToOne(mappedBy ="student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    BankCard bankCard;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Loan> loanList=new ArrayList<> ();


    @Builder
    public Student(Long aLong, String firstName, String lastName, String fatheName, String motherName, String nationalCode, String phoneNumber, String studentNumber, City city, boolean isMarried, Student partner, LocalDate enteryDate, String universityName, TypeUniversity typeUniversity, Grade grade, boolean dorm, boolean isEducate, int contractNum, String password, BankCard bankCard, List<Loan> loanList) {
        super(aLong);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatheName = fatheName;
        this.motherName = motherName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.studentNumber = studentNumber;
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
        this.password = password;
        this.bankCard = bankCard;
        this.loanList = loanList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatheName='" + fatheName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", city=" + city +
                ", isMarried=" + isMarried +
                ", partner=" + partner +
                ", EnteryDate=" + EnteryDate +
                ", universityName='" + universityName + '\'' +
                ", typeUniversity=" + typeUniversity +
                ", grade=" + grade +
                ", dorm=" + dorm +
                ", isEducate=" + isEducate +
                ", contractNum=" + contractNum +
                ", password='" + password + '\'' +
                ", bankCard=" + bankCard +
                ", loanList=" + loanList +
                ", id=" + id +
                '}';
    }
}

