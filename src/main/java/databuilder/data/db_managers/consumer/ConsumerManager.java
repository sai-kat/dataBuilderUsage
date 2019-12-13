package databuilder.data.db_managers.consumer;

import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.requests.CreateConsumerRequest;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ConsumerManager {

    <T> Optional <T> createConsumer(CreateConsumerRequest createConsumerRequest, Function<ConsumerDetailsData, T> handler);

    <T> Optional <T> getConsumer(String mobileNumber, Function<ConsumerDetailsData, T> handler);

    <T> List<T> getConsumers(Function<ConsumerDetailsData, T> handler);
}
