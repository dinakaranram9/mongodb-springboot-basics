package com.mongodb.sample.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.mongodb.sample.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.database}")
	String dbName;

	@Value("${spring.data.mongodb.uri}")
	String dbUri;

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Override
	protected String getDatabaseName() {
		return dbName;
	}

	@Override
	public MongoClient mongoClient() {
		final ConnectionString connectionString = new ConnectionString(dbUri);
		final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString).build();
		return MongoClients.create(mongoClientSettings);
	}
}