package com.faryard.api.services.impl.node;

import com.faryard.api.DTO.GraphDataDTO;
import com.faryard.api.DTO.GraphicElementsDTO;
import com.faryard.api.configurators.Env;
import com.faryard.api.configurators.StartupApplication;
import com.faryard.api.domain.node.AdsValues;
import com.faryard.api.domain.node.NodeHistory;
import com.faryard.api.domain.node.NodeSensors;
import com.faryard.api.domain.node.RelayValues;
import com.faryard.api.repositories.NodeHistoryRepository;
import com.faryard.api.utils.DatesUtils;
import com.faryard.api.utils.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class NodeHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(NodeHistoryService.class);
    @Autowired
    NodeHistoryRepository historyRepository;
    @Autowired
    Env env;

    public void registerSensorHistory(String nodeId, NodeSensors nodeSensors) {
        String strMinMinutesBetweenHistories = (String) env.getProperty("node.history.minutesbetween");
        logger.info("App properties value for minutesbetween: {}", strMinMinutesBetweenHistories);
        int minMinutesBetweenHistories = Integer.parseInt(strMinMinutesBetweenHistories);
        Optional<NodeHistory> lastHistory = historyRepository.findFirstByNodeIdOrderByRegistrationDateDesc(nodeId);
        if (lastHistory.isPresent()) {
            logger.info("The node {} has the last history date: {}", nodeId, lastHistory.get().getRegistrationDate());
            Date lastDate = lastHistory.get().getRegistrationDate();
            Date now = new Date();
            long diff = DatesUtils.differenceInMinutesBetweenDates(lastDate, now);
            logger.info("The difference is {}", diff);
            logger.info("Minimum minutes between is {}", minMinutesBetweenHistories);
            if (diff <= minMinutesBetweenHistories) {
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
                    current.setLastUpdate(relay.getLastUpdate());
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

    public GraphicElementsDTO getNodeHistoryWithTimeSpan(String nodeId, String timespan, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz");

        LocalDateTime dateTimeStart = LocalDateTime.from(formatter.parse(startDate));
        LocalDateTime dateTimeEnd = LocalDateTime.from(formatter.parse(endDate));
        Date start = DatesUtils.asDate(dateTimeStart, ZoneId.of("Europe/Rome"));
        Date end = DatesUtils.asDate(dateTimeEnd, ZoneId.of("Europe/Rome"));

        List<NodeHistory> historyList = historyRepository.findAllByNodeIdAndRegistrationDateBetween(nodeId, start, end);

        List<NodeHistory> timespannedList = getTimespanOnly(historyList, timespan);
        GraphicElementsDTO elementsDTO = new GraphicElementsDTO();
        elementsDTO.setStatus("OK");

        List<GraphDataDTO> graphData = timespannedList.stream().map(Mappers::toGraphDataDTO).collect(Collectors.toList());
        elementsDTO.setData(graphData);
        return elementsDTO;
    }


    private List<NodeHistory> getTimespanOnly(List<NodeHistory> list, String timeSpan) {
        List<NodeHistory> outputList = new ArrayList<>();
        if (timeSpan.equals("hourly")) {
            List<Integer> hours = new ArrayList<>();

            for (NodeHistory n : list) {
                int hour = DatesUtils.getHourFromDate(n.getRegistrationDate());
                if(!hours.contains(hour)){
                    outputList.add(n);
                    hours.add(hour);
                }
            }
        }
        outputList.add(list.get(list.size() -1));
        return outputList;
    }

}
