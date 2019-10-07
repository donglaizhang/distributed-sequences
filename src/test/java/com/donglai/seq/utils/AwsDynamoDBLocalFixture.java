package com.donglai.seq.utils;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.commons.cli.ParseException;

/**
 *  refer from : https://github.com/aws-samples/aws-dynamodb-examples/blob/master/src/test/java/com/amazonaws/services/dynamodbv2/DynamoDBLocalFixture.java
 */
public class AwsDynamoDBLocalFixture {
    private AmazonDynamoDB localDynamoDBInstance;
    private DynamoDBProxyServer server = null;
    private int port = 8000;
    private String host = "localhost";

    public void initializeEnv() {
        System.setProperty("sqlite4java.library.path", "build/libs/");
    }

    public void createLocalSeqTable(String tableName) {
        KeySchemaElement keySch= new KeySchemaElement().withKeyType(KeyType.HASH).withAttributeName("id");
        AttributeDefinition attriDef = new AttributeDefinition().
                withAttributeType(ScalarAttributeType.S)
                .withAttributeName("id");
        CreateTableRequest req = new CreateTableRequest().withTableName(tableName).
                withKeySchema(keySch).
                withAttributeDefinitions(attriDef);

        req.withBillingMode(BillingMode.PAY_PER_REQUEST);
        localDynamoDBInstance.createTable(req);
//        listTables(localDynamoDBInstance.listTables(), "DynamoDB Local over HTTP");
    }
    public AmazonDynamoDB startLocalDynamoProcess () {
        initializeEnv();
//        localDynamoDBInstance = DynamoDBEmbedded.create().amazonDynamoDB();
        final String[] localArgs = { "-inMemory" };
        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();
            // we can use any region here
            AwsClientBuilder.EndpointConfiguration lclDyConfig = new AwsClientBuilder.EndpointConfiguration("http://" + host + ":" + port, "us-west-2");
            localDynamoDBInstance = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(lclDyConfig)
                    .build();
            // use the DynamoDB API over HTTP
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.localDynamoDBInstance;
    }
    public void shutdown() {
        if(server != null) {
            System.out.println("in stop" );
            try {
                server.stop();
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * You can use mvn to run DynamoDBLocalFixture, e.g.
     * <p>
     * $ mvn clean package
     * <p>
     * $ mvn exec:java -Dexec.mainClass="com.amazonaws.services.dynamodbv2.DynamoDBLocalFixture" \
     * -Dexec.classpathScope="test" \
     * -Dsqlite4java.library.path=target/dependencies
     * <p>
     * It's recommended to run "aws configure" one time before you run DynamoDBLocalFixture
     *
     * @param args - no args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        AwsDynamoDBLocalFixture dynamoServer = new AwsDynamoDBLocalFixture();
        dynamoServer.startLocalDynamoProcess();
        dynamoServer.createLocalSeqTable("testtable");
        dynamoServer.listTables();
        dynamoServer.shutdown();
    }
    public void listTables() {
        ListTablesResult result = localDynamoDBInstance.listTables();
        System.out.println("found " + Integer.toString(result.getTableNames().size()) + " tables with ");
        for(String table : result.getTableNames()) {
            System.out.println(table);
        }
    }
}
