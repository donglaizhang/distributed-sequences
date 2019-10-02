package com.donglai.seq.core;

import com.donglai.seq.dao.SequenceDao;
import com.google.inject.Inject;

import java.util.List;

public class SequeneceServiceImpl implements SequenceService{

    private final SequenceDao sequenceDao;
    @Inject
    public SequeneceServiceImpl(SequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }

    @Override
    public List<String> generateSequences(String id) {
        return null;
    }

    @Override
    public List<String> generateSequences(String id, int batchSize) {
        return null;
    }
}
