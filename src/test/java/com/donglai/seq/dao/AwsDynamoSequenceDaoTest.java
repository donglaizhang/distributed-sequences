package com.donglai.seq.dao;

import com.donglai.seq.core.Sequence;
import com.donglai.seq.utils.AwsDynamoDBLocalFixture;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AwsDynamoSequenceDaoTest {
    private AwsDynamoSequenceDao dao =null;
    AwsDynamoDBLocalFixture d=null;

    @Test
    public void test() {
        Sequence seq = new Sequence();
        seq.setId("testid-create");
        long[][] ranges = new long[][]{{100, 200}, {300, 400}};
        seq.setRanges(ranges);
        dao.createSequence(seq);
    }


//    @Before
    @BeforeClass
    public void setUp() throws Exception {


        AwsDynamoDBLocalFixture    lclClient = new AwsDynamoDBLocalFixture();
        lclClient.startLocalDynamoProcess();
        dylclClient.createLocalSeqTable("local_sequences");

        dao = new AwsDynamoSequenceDao();
    }

}
