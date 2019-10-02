package com.donglai.seq.core;

import java.util.List;

public interface SequenceService {
    List<String> generateSequences(String id);
    List<String> generateSequences(String id, int batchSize);

}
