import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.Date;

public class Runner {

    public static void main(String args[])
    {
        final int WORD_SIZE = 7;
        final String DB_NAME = "wordbuilder";
        MongoClient mongoClient;
        Date startTime = new Date();

        try
        {
            mongoClient = new MongoClient();
            final DB database = mongoClient.getDB(DB_NAME);

            System.out.println("Running... Max size attempt: " + WORD_SIZE + ".");
            new WordBuilder(database)
                    .withSize(WORD_SIZE)
                    .build();
        } catch (UnknownHostException e)
        {
            System.out.println("Exception ocurred: " + e.getMessage());
        }

        System.out.println("Operation Time (minutes): " + ((new Date().getTime() - startTime.getTime()) / (60 * 1000) % 60));

        return;
    }
}
