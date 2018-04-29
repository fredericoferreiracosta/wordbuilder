import com.mongodb.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;

public class WordBuilder
{

    private DB database;
    private DBCollection dictionary;
    private List<String> alphabet = new ArrayList<String>();
    private int currentSize;
    private final String DICTIONARY = "dictionary";
    private final String WORD_KEY = "word";
    private final String SIZE_KEY = "size";
    private final int PAGINATION_LIMIT = 150000;

    public WordBuilder(DB databse)
    {
        this.database = databse;
        dictionary = this.database.getCollection(DICTIONARY);
        dictionary.drop();
        buildAlphabet(alphabet);
        currentSize = 0;
    }

    public WordBuilder withSize(int size)
    {
        currentSize = size;
        return this;
    }

    public WordBuilder build()
    {
        for (int w = 0; w < alphabet.size(); w++)
        {
            DBObject alphabetWord = new BasicDBObject()
                    .append(SIZE_KEY, 1)
                    .append(WORD_KEY, alphabet.get(w));
            dictionary.insert(alphabetWord);
            System.out.println(alphabetWord.get(WORD_KEY));
        }
        for (int q = 1; q < currentSize; q++)
        {
            System.out.println("New cycle -------------");
            run(q);
        }
        return this;
    }

    private void run(final int LAST_SIZE)
    {
        final int CURRENT_SIZE = LAST_SIZE + 1;
        DBObject query = new BasicDBObject().append(SIZE_KEY, LAST_SIZE);
        List<DBObject> currentSet = dictionary.find(query).addOption(Bytes.QUERYOPTION_NOTIMEOUT).limit(PAGINATION_LIMIT).toArray();
        for (DBObject currentWord : currentSet)
        {
            for (int j = 0; j < alphabet.size(); j++)
            {
                String newCombination = currentWord.get(WORD_KEY) + alphabet.get(j);
                DBObject newWord = new BasicDBObject()
                        .append(SIZE_KEY, CURRENT_SIZE)
                        .append(WORD_KEY, newCombination);
                dictionary.insert(newWord);
                System.out.println(newCombination);
            }
        }
    }

    private void buildAlphabet(List<String> alphabet) {

        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");
        alphabet.add("d");
        alphabet.add("e");
        alphabet.add("f");
        alphabet.add("g");
        alphabet.add("h");
        alphabet.add("i");
        alphabet.add("j");
        alphabet.add("k");
        alphabet.add("l");
        alphabet.add("m");
        alphabet.add("n");
        alphabet.add("o");
        alphabet.add("p");
        alphabet.add("q");
        alphabet.add("r");
        alphabet.add("s");
        alphabet.add("t");
        alphabet.add("u");
        alphabet.add("v");
        alphabet.add("w");
        alphabet.add("x");
        alphabet.add("y");
        alphabet.add("z");
    }
}
