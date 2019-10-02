package com.donglai.seq.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.donglai.seq.core.SeqFormat;
import com.donglai.seq.core.Sequence;

import java.util.Map;

public class AWSDynamoSequenceDao implements SequenceDao{
    private final DynamoDB dynamoDB;
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1).build();
    private final String dynamoTable;

    public AWSDynamoSequenceDao(DynamoDB dynamoDB, String dynamoTable) {
        this.dynamoTable = dynamoTable;
        this.dynamoDB = new DynamoDB(client);
    }

    @Override
    public long[] shiftSequence(String id) {
        return new long[0];
    }

    @Override
    public long[] shiftSequence(String id, int batchSize) {
        return new long[0];
    }

    @Override
    public void addRange(String id, long start, long end) {

        Table table = dynamoDB.getTable(dynamoTable);
        PutItemSpec putspec = new PutItemSpec().withConditionExpression("");
        table.putItem(putspec);

    }

    @Override
    public void createSequence(Sequence seq) {
        Table table = dynamoDB.getTable(dynamoTable);

        int batchSize = seq.getBatchSize() > 0 ?seq.getBatchSize(): 1;
        Item item = new Item().withPrimaryKey("id", seq.getId())
                .with("batch_size", batchSize)
                .with("current_block", 0)
                .with("current_sequence", 0);
        if (seq.getRanges().length > 0) {
            item = item.withList("ranges",seq.getRanges());
        }
        PutItemSpec putspec = new PutItemSpec().withConditionExpression("").withItem(item);
        table.putItem(putspec);
    }

    @Override
    public Sequence getSequence(String id) {
        Table table = dynamoDB.getTable(dynamoTable);
        PrimaryKey pk = new PrimaryKey();
        pk.addComponent("id", id);
        Map<String, Object> dyResult = table.getItem(pk).asMap();
        return convertDyResultToObject(dyResult);
    }
    private Sequence convertDyResultToObject(Map<String, Object> rs) {
        if (rs.get("id") == null) {
            return null;
        }
        Sequence seq = new Sequence();
        seq.setId(rs.get("id").toString());
        seq.setBatchSize((Integer)rs.get("batch_size"));
        seq.setCurrentBlock((Integer)rs.get("current_block"));
        seq.setCurrentSeq((Integer)rs.get("current_sequence"));
        Map<String, Object>[] ranges = (Map<String, Object>[]) rs.get("ranges");
        long[][] arrRanges = new long[ranges.length][2];

        for(int i = 0 ; i < ranges.length ; i++){
            Map<String, Object> range = ranges[i];
            long start = (Integer) range.get("start");
            long end = (Integer)range.get("end");
            arrRanges[i][0] = start;
            arrRanges[i][1] = end;
        }
        seq.setRanges(arrRanges);
        String format = (String) rs.get("format");
        SeqFormat sfFormat = SeqFormat.valueOf(format);
        seq.setSf(sfFormat);
        return seq;
    }
}
