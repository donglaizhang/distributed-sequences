package com.donglai.seq.dao;

import com.donglai.seq.core.Sequence;

public interface SequenceDao {
    long[] shiftSequence(String id);
    long[] shiftSequence(String id, int batchSize);
    void addRange(String id, long start, long end);
    void createSequence(Sequence seq);
    Sequence getSequence(String id);


}
