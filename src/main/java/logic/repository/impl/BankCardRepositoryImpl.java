package logic.repository.impl;

import base.repository.BaseRepositoryImpl;
import base.service.BaseServiceImpl;
import domain.BankCard;
import logic.repository.BankCardRepository;
import org.hibernate.Session;

public class BankCardRepositoryImpl extends BaseRepositoryImpl<BankCard,Long>
        implements BankCardRepository {
    public BankCardRepositoryImpl(Session session) {
        super ( session );
    }

    @Override
    public Class<BankCard> getEntityClass() {
        return null;
    }
}
