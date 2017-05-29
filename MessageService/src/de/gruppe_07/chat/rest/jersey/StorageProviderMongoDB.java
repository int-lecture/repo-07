package de.gruppe_07.chat.rest.jersey;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//Import mongoDBURI gelöscht(MongoDB Server addresse einfügen)
//implements StorageProvider gelöscht

/**
 * Storage provider for a MongoDB.
 */
class StorageProviderMongoDB {

    /** URI to the MongoDB instance. */
    private static MongoClientURI connectionString =
            new MongoClientURI("mongodb://127.0.0.1:27017");

    /** Client to be used. */
    private static MongoClient mongoClient = new MongoClient(connectionString);

    /** Mongo database. */
    private static MongoDatabase database = mongoClient.getDatabase("chat");

    /**
     * @see var.chat.server.persistence.StorageProvider#retrieveAndUpdateSequence(java.lang.String)
     */
    public synchronized long retrieveAndUpdateSequence(String userId) {
        MongoCollection<Document> sequences = database.getCollection(
                "sequences");

        Document seqDoc = sequences.find(eq("username", userId)).first();
        long sequence = 1L;

        if (seqDoc != null) {
            sequence = seqDoc.getLong("sequence");
            sequence++;
            seqDoc.replace("sequence", sequence);
            sequences.updateOne(eq("username", seqDoc.get("username")),
                    new Document("$set", seqDoc));
        }
        else {
            sequences.insertOne(new Document("sequence", sequence)
                    .append("username", userId));
        }

        return sequence;
    }

    /**
     * @see var.chat.server.persistence.StorageProvider#storeMessage(var.chat.server.domain.Message)
     */
    public synchronized void storeMessage(Message message) {
        MongoCollection<Document> collection = database.getCollection("messages");

        Document doc = new Document("from", message.from)
                .append("to", message.to)
                .append("date", message.date)
                .append("sequence", message.sequence)
                .append("text", message.text);

        collection.insertOne(doc);
    }

    /**
     * @see var.chat.server.persistence.StorageProvider#retrieveMessages(java.lang.String, long, boolean)
     */
    public synchronized List<Message> retrieveMessages(String userId,
            long sequenceNumber, boolean deleteOldMessages) {

        MongoCollection<Document> collection = database.getCollection("messages");

        // Remove Messages with seq < provided seq no
        if (deleteOldMessages) {
            collection.deleteMany(and(lte("sequence", sequenceNumber), eq("to", userId)));
        }

        // Retreive remaining documents
        List<Document> documents = new ArrayList<>();
        collection.find(and(gt("sequence", sequenceNumber), eq("to", userId)))
                .forEach((Block<Document>) e -> documents.add(e));

        // No messages for user there
        if (documents.isEmpty()) {
            return null;
        }

        List<Message> messagesForUser = new ArrayList<>();
        Collections.sort(messagesForUser, (a,b) -> (int) (b.sequence - a.sequence));

        for (Document document : documents) {
            Message m = new Message();
            m.to = document.getString("to");
            m.from = document.getString("from");
            m.date = document.getString("date");
            m.sequence = document.getLong("sequence");
            m.text = document.getString("text");
            messagesForUser.add(m);
        }

        return messagesForUser;
    }

    /**
     * @see var.chat.server.persistence.StorageProvider#clearForTest()
     */
    public void clearForTest() {
        database.getCollection("messages").drop();
        database.getCollection("sequences").drop();
    }
}