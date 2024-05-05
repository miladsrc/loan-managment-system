package util;

import logic.repository.BankCardRepository;
import logic.repository.LoanRepository;
import logic.repository.RefundRepository;
import logic.repository.StudentRepository;
import logic.repository.impl.BankCardRepositoryImpl;
import logic.repository.impl.LoanRepositoryImpl;
import logic.repository.impl.RefundRepositoryImpl;
import logic.repository.impl.StudentRepositoryImpl;
import logic.service.BankCardService;
import logic.service.LoanService;
import logic.service.RefundService;
import logic.service.StudentService;
import logic.service.impl.BankCardServiceImpl;
import logic.service.impl.LoanServiceImpl;
import logic.service.impl.RefundServiceImpl;
import logic.service.impl.StudentServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ApplicationContext {
    private ApplicationContext() {
    }

    //SESSION FACTORY
    private static final SessionFactory SESSION_FACTORY;
    private static final Session SESSION;

    //REPOSITORY
    private static final BankCardRepository BANK_CARD_REPOSITORY;
    private static final LoanRepository LOAN_REPOSITORY;
    private static final StudentRepository STUDENT_REPOSITORY;
    private static final RefundRepository REFUND_REPOSITORY;


    //SERVICE
    private static final BankCardService BANK_CARD_SERVICE;
    private static final StudentService STUDENT_SERVICE;
    private static final RefundService REFUND_SERVICE;
    private static final LoanService LOAN_SERVICE;


    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance ();
        SESSION = SESSION_FACTORY.openSession ();

        //REPOSITORY
        BANK_CARD_REPOSITORY = new BankCardRepositoryImpl ( SESSION );
        STUDENT_REPOSITORY = new StudentRepositoryImpl ( SESSION );
        LOAN_REPOSITORY = new LoanRepositoryImpl ( SESSION );
        REFUND_REPOSITORY = new RefundRepositoryImpl ( SESSION );

        //SERVICE
        REFUND_SERVICE = new RefundServiceImpl ( REFUND_REPOSITORY );
        STUDENT_SERVICE = new StudentServiceImpl ( STUDENT_REPOSITORY );
        LOAN_SERVICE = new LoanServiceImpl ( LOAN_REPOSITORY );
        BANK_CARD_SERVICE = new BankCardServiceImpl ( BANK_CARD_REPOSITORY );
    }

    //GETTER
    public static LoanService getLoanService() {
        return LOAN_SERVICE;
    }

    public static StudentService getStudentService() {
        return STUDENT_SERVICE;
    }

    public static RefundService getRefundService() {
        return REFUND_SERVICE;
    }

    public static BankCardService getBankCardService() {
        return BANK_CARD_SERVICE;
    }
}

