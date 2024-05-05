package logic.repository.impl;

import base.repository.BaseRepositoryImpl;
import domain.Loan;
import logic.repository.LoanRepository;
import org.hibernate.Session;

public class LoanRepositoryImpl extends BaseRepositoryImpl<Loan, Long> implements LoanRepository {
    public LoanRepositoryImpl(Session session) {
        super ( session );
    }

    @Override
    public Class<Loan> getEntityClass() {
        return Loan.class;
    }
}
