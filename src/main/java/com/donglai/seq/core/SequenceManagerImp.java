package com.donglai.seq.core;
import com.donglai.seq.dao.SequenceDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

public class SequenceManagerImp implements SequenceManager{
    private SequenceDao sequenceDao;

    public SequenceManagerImp(SequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }

    @Override
    public void createSequence(Sequence seq) {
        ObjectUtils.anyNotNull(seq);
        StringUtils.isNotEmpty(seq.id);
        if(!isValidRange(seq.getRanges())) {
            throw new IllegalArgumentException("Ranges is invalid: Not null, ordered, not overlap");
        }
        seq.currentBlock = 0;
        seq.currentSeq = 0;
        sequenceDao.createSequence(seq);
    }

    private boolean isValidRange(long[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            return false;
        }
        long prevEnd = Long.MIN_VALUE;
        for (int i = 0 ; i < ranges.length; i++) {
            long[] range = ranges[i];
            if (range == null || range.length != 2) {
                return false;
            }
            long start = range[0];
            long end = range[1];
            if (end >= start) {
                return false;
            }
            if (prevEnd >= start) {
                return false;
            }
            prevEnd = end;
        }
        return true;
    }
}
