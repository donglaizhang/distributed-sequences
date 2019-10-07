package com.donglai.seq.core;

import com.donglai.seq.dao.SequenceDao;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class SequeneceServiceImpl implements SequenceService{

    private final SequenceDao sequenceDao;
    @Inject
    public SequeneceServiceImpl(SequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }

    @Override
    public List<String> generateSequences(String id) {
        Sequence seq = sequenceDao.getSequence(id);
        SeqFormat outputFtm = seq.getSf();
        long[] seqRange = sequenceDao.shiftSequence(id);
        return generateSeqs(outputFtm, seqRange[0], seqRange[1]);
    }
    private List<String> generateSeqs(SeqFormat sf, long start, long end) {
        List<String> list = new ArrayList<>((int)(end - start));
        if (sf == SeqFormat.Base10) {
            while (start < end) {
                String sq = Long.toString(start++);
                list.add(sq);
            }
        } else if (sf == SeqFormat.Base36) {
            while (start < end) {
                String sq = Long.toString(start++, 36);
                list.add(sq);
            }
        } else if (sf == SeqFormat.Base62) {
            while (start < end) {
                String sq = Long.toString(start++, 62);
                list.add(sq);
            }
        }
        return list;
    }

    @Override
    public List<String> generateSequences(String id, int batchSize) {

        Sequence seq = sequenceDao.getSequence(id);
        SeqFormat outputFtm = seq.getSf();
        long[] seqRange = sequenceDao.shiftSequence(id, batchSize);
        return generateSeqs(outputFtm, seqRange[0], seqRange[1]);
    }
}
