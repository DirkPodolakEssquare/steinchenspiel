package de.essquare.steinchenspiel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@Service
public class Service {

    public void postEmail(String email) {
        DynamoDbClient dbClient = DynamoDbClient.create();

        createItem(dbClient);

        Map<String, AttributeValue> keyToGet
                = Map.of("email", AttributeValue.builder().s(email).build());
        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName("User")
                .build();

        GetItemResponse response = dbClient.getItem(request);
        Collection<AttributeValue> values = response.item().values();
        //dbClient.getItem(request).join().item().values();
//        
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
//            .build();
//
//        DynamoDB dynamoDB = new DynamoDB(client);
//
//        Table table = dynamoDB.getTable("Movies");
//
//        int year = 2015;
//        String title = "The Big New Movie";
//
//        final Map<String, Object> infoMap = new HashMap<String, Object>();
//        infoMap.put("plot", "Nothing happens at all.");
//        infoMap.put("rating", 0);
//
//        try {
//            System.out.println("Adding a new item...");
//            PutItemOutcome outcome = table
//                .putItem(new Item().withPrimaryKey("year", year, "title", title).withMap("info", infoMap));
//
//            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
//
//        }
//        catch (Exception e) {
//            System.err.println("Unable to add item: " + year + " " + title);
//            System.err.println(e.getMessage());
//        }
    }

    private void createItem(DynamoDbClient dbClient) {
        User user = new User("1234", "a@b.com", "0123-4567", "Foo Bar", null, "abcxyz", null);
        
//        dbClient.putItem(putItemRequest);
        
//        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
//
//        // Add all content to the table
//        itemValues.put(key, AttributeValue.builder().s(keyVal).build());
//        itemValues.put(songTitle, AttributeValue.builder().s(songTitleVal).build());
//        itemValues.put(albumTitle, AttributeValue.builder().s(albumTitleValue).build());
//        itemValues.put(awards, AttributeValue.builder().s(awardVal).build());
//
//        PutItemRequest request = PutItemRequest.builder()
//                .tableName(tableName)
//                .item(itemValues)
//                .build();
//
//        try {
//            ddb.putItem(request);
//            System.out.println(tableName + " was successfully updated");
//
//        } catch (ResourceNotFoundException e) {
//            System.err.format("Error: The Amazon DynamoDB table \"%s\" can't be found.\n", tableName);
//            System.err.println("Be sure that it exists and that you've typed its name correctly!");
//            System.exit(1);
//        } catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
    }


    public static void queryTableFilter(DynamoDbEnhancedClient enhancedClient) {

        try{
            DynamoDbTable<EnhancedQueryRecords.Customer> mappedTable = enhancedClient.table("Customer", TableSchema.fromBean(EnhancedQueryRecords.Customer.class));

            AttributeValue att = AttributeValue.builder()
                    .s("sblue@noserver.com")
                    .build();

            Map<String, AttributeValue> expressionValues = new HashMap<>();
            expressionValues.put(":value", att);

            Expression expression = Expression.builder()
                    .expression("email = :value")
                    .expressionValues(expressionValues)
                    .build();

            // Create a QueryConditional object that is used in the query operation
            QueryConditional queryConditional = QueryConditional
                    .keyEqualTo(Key.builder().partitionValue("id103")
                            .build());

            // Get items in the Customer table and write out the ID value
            Iterator<EnhancedQueryRecords.Customer> results = mappedTable.query(r -> r.queryConditional(queryConditional).filterExpression(expression)).items().iterator();

            while (results.hasNext()) {

                EnhancedQueryRecords.Customer rec = results.next();
                System.out.println("The record id is "+rec.getId());
            }

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done");
}
