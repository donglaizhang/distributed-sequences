package com.donglai.seq.core;

public class Sequence {
    String id;
    long[][] ranges;
    int batchSize = -1;

    int currentBlock = -1;
    int currentSeq = -1;

    boolean obf = false;
    SeqFormat sf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long[][] getRanges() {
        return ranges;
    }

    public void setRanges(long[][] ranges) {
        this.ranges = ranges;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(int currentBlock) {
        this.currentBlock = currentBlock;
    }

    public int getCurrentSeq() {
        return currentSeq;
    }

    public void setCurrentSeq(int currentSeq) {
        this.currentSeq = currentSeq;
    }

    public boolean isObf() {
        return obf;
    }

    public void setObf(boolean obf) {
        this.obf = obf;
    }

    public SeqFormat getSf() {
        return sf;
    }

    public void setSf(SeqFormat sf) {
        this.sf = sf;
    }
}
