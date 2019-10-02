package com.donglai.seq;

import com.donglai.seq.core.SequenceService;
import com.donglai.seq.core.SequeneceServiceImpl;
import com.donglai.seq.dao.AWSDynamoSequenceDao;
import com.donglai.seq.dao.SequenceDao;
import com.google.inject.AbstractModule;

public class SequenceModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(SequenceDao.class).to(AWSDynamoSequenceDao.class);
        bind(SequenceService.class).to(SequeneceServiceImpl.class);
    }
}
