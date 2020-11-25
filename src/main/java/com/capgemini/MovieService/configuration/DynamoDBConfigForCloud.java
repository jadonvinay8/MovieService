package com.capgemini.MovieService.configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.capgemini.MovieService.entities.Movie;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.capgemini.MovieService")
public class DynamoDBConfigForCloud {

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
          .withRegion(Regions.US_EAST_1)
          .build();
    }

    @PostConstruct
    private void setupTables() {
        AmazonDynamoDB amazonDynamoDB = amazonDynamoDB();
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = mapper.generateCreateTableRequest(Movie.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
    }

}