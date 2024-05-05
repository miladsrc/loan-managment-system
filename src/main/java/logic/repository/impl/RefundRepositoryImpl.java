package logic.repository.impl;

import base.repository.BaseRepositoryImpl;
import domain.Refund;
import logic.repository.RefundRepository;
import org.hibernate.Session;

public class RefundRepositoryImpl extends BaseRepositoryImpl<Refund, Long> implements RefundRepository {
    public RefundRepositoryImpl(Session session) {
        super ( session );
    }

    @Override
    public Class<Refund> getEntityClass() {
        return null;
    }
}
