package logic.service.impl;

import base.service.BaseServiceImpl;
import domain.Loan;
import logic.repository.LoanRepository;
import logic.service.LoanService;

public class LoanServiceImpl extends BaseServiceImpl<Loan, Long, LoanRepository>
implements LoanService {
    public LoanServiceImpl(LoanRepository repository) {
        super ( repository );
    }
}
