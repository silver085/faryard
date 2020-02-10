package com.faryard.api.services.impl.node;

import com.faryard.api.configurators.Env;
import com.faryard.api.configurators.StartupApplication;
import com.faryard.api.domain.node.AdsValues;
import com.faryard.api.domain.node.NodeHistory;
import com.faryard.api.domain.node.NodeSensors;
import com.faryard.api.domain.node.RelayValues;
import com.faryard.api.repositories.NodeHistoryRepository;
import com.faryard.api.utils.DatesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class NodeHistoryService {
    @Autowired
    NodeHistoryRepository historyRepository;
    @Autowired
    Env env;

    private static final Logger logger = LoggerFactory.getLogger(NodeHistoryService.class);


    public void registerSensorHistory(String nodeId, NodeSensors nodeSensors) {
        int minMinutesBetweenHistories = (int) env.getProperty("node.history.minutesbetween");
        Optional<NodeHistory> lastHistory = historyRepository.findTopByNodeId(nodeId);
        if(lastHistory.isPresent()){
            logger.info("The node {} has the last history date: {}",nodeId, lastHistory.get().getRegistrationDate());
            Date lastDate = lastHistory.get().getRegistrationDate();
            Date now = new Date();
            long diff = DatesUtils.differenceInMinutesBetweenDates(lastDate, now);
            logger.info("The difference is {}", diff);
            logger.info("Minimum minutes between is {}", minMinutesBetweenHistories);
            if(diff <= minMinutesBetweenHistories ){
                logger.info("Time is too short, skipping this");
                return;
            }
        } else {
            logger.info("The node {} has no sensors history", nodeId);
        }
        logger.info("Difference is ok, registering new history");
        NodeHistory nodeHistory = new NodeHistory();
        nodeHistory.setNodeId(nodeId);
        nodeHistory.setEnvironmentTemp(nodeSensors.getHygrometer().getTemperature());
        nodeHistory.setEnvironmentHum(nodeSensors.getHygrometer().getHumidity());

        //Setting relay statuses
        AtomicInteger index = new AtomicInteger();
        index.set(0);
        List<RelayValues> relayValues = nodeSensors.getRelayChannels().stream()
                .map(relay -> {
                        RelayValues current = new RelayValues();
                        current.setOn(relay.isOn());
                        current.setRelayChannel(index.get());
                        index.getAndIncrement();
                        return current;
        }).collect(Collectors.toList());

        nodeHistory.setRelaysList(relayValues);

        //Set the ADS Values

        List<AdsValues> adsValues = nodeSensors.getAdsChannels().stream()
                .map(ads -> {
                        AdsValues adsValue = new AdsValues();
                        adsValue.setAdsChannelName(ads.getName());
                        adsValue.setPercentage(ads.getPercentage());
                        adsValue.setVoltage(ads.getChannelVoltage());
                        adsValue.setEvaluation(ads.getEvaluation().getEvaluation());
                        adsValue.setEvaluationIndex(ads.getEvaluation().getEvaluationIndex());
                        return adsValue;
                }).collect(Collectors.toList());

        nodeHistory.setAdsList(adsValues);
        nodeHistory.setRegistrationDate(new Date());
        historyRepository.save(nodeHistory);
    }
}
