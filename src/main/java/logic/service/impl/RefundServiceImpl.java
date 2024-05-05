package logic.service.impl;

import base.service.BaseServiceImpl;
import domain.Refund;
import logic.repository.RefundRepository;
import logic.service.RefundService;

public class RefundServiceImpl extends BaseServiceImpl<Refund, Long, RefundRepository>
implements RefundService {
    public RefundServiceImpl(RefundRepository repository) {
        super ( repository );
    }
}
