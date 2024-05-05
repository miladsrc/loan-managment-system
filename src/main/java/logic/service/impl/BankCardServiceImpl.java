package logic.service.impl;

import base.service.BaseServiceImpl;
import domain.BankCard;
import logic.repository.BankCardRepository;
import logic.service.BankCardService;

public class BankCardServiceImpl extends BaseServiceImpl<BankCard, Long, BankCardRepository>
        implements BankCardService {
    public BankCardServiceImpl(BankCardRepository repository) {
        super ( repository );
    }
}
