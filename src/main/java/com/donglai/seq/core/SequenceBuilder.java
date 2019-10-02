package com.donglai.seq.core;

public class SequenceBuilder {
    private Sequence sequence;

    public SequenceBuilder(Sequence sequence) {
        this.sequence = sequence;
    }
    public SequenceBuilder setId(String id) {
        sequence.setId(id);
        return this;
    }

    public Sequence getSequence() {
        return sequence;
    }
}
