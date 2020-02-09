package com.faryard.api.utils;

import com.faryard.api.DTO.node.*;
import com.faryard.api.domain.node.*;

import java.util.List;
import java.util.stream.Collectors;

public class Mappers {
    public static NodeSensors mapSensorsFromDTO(SensorsStatus sensorsStatus) {
        NodeSensors sensors = new NodeSensors();

        //Mapping hygrometer
        Hygrometer hygrometer = new Hygrometer();
        hygrometer.setHumidity(sensorsStatus.getHygrometer().getHumidity());
        hygrometer.setTemperature(sensorsStatus.getHygrometer().getTemperature());
        sensors.setHygrometer(hygrometer);


        //Mapping ads
        List<ADSChannel> adsChannelList = sensorsStatus.getAds().stream().map(ads -> {
            ADSChannel channel = new ADSChannel();
            channel.setName(ads.getName());
            channel.setChannel(ads.getChannel());
            channel.setAdc(ads.getAdc());
            channel.setChannelValue(ads.getChannelValue());
            channel.setChannelVoltage(ads.getChannelVoltage());
            channel.setPercentage(ads.getPercentage());
            ADSChannelEvaluation adsChannelEvaluation = new ADSChannelEvaluation();
            adsChannelEvaluation.setEvaluationIndex(ads.getEvaluation().getEvaluationInteger());
            adsChannelEvaluation.setEvaluation(ads.getEvaluation().getEvaluation());
            channel.setEvaluation(adsChannelEvaluation);
            return channel;
        }).collect(Collectors.toList());

        sensors.setAdsChannels(adsChannelList);

        //mapping relays

        List<RelayChannel> relayChannels = sensorsStatus.getRelays().stream().map(relay ->{
            RelayChannel channel = new RelayChannel();
            channel.setLastUpdate(relay.getLastUpdate());
            channel.setOn(relay.isStatus());
            return channel;
        }).collect(Collectors.toList());

        sensors.setRelayChannels(relayChannels);

        return sensors;
    }

    public static SensorsStatus mapNodeToNodeStatusResponse(Node node) {


        NodeSensors sensors = node.getNodeSensors();
        SensorsStatus sensorsStatus = new SensorsStatus();
        sensorsStatus.setHygrometer(new HygrometerStatus());
        sensorsStatus.getHygrometer().setHumidity(sensors.getHygrometer().getHumidity());
        sensorsStatus.getHygrometer().setTemperature(sensors.getHygrometer().getTemperature());

        List<AdsStatus> adsStatuses = sensors.getAdsChannels().stream().map(channel -> {
            AdsStatus adsStatus = new AdsStatus();
            adsStatus.setName(channel.getName());
            adsStatus.setChannel(channel.getChannel());
            adsStatus.setAdc(channel.getAdc());
            adsStatus.setChannelValue(channel.getChannelValue());
            adsStatus.setChannelVoltage(channel.getChannelVoltage());
            adsStatus.setPercentage(channel.getPercentage());
            AdsEvaluation evaluation = new AdsEvaluation();
            evaluation.setEvaluationInteger(channel.getEvaluation().getEvaluationIndex());
            evaluation.setEvaluation(channel.getEvaluation().getEvaluation());
            adsStatus.setEvaluation(evaluation);
            return adsStatus;
        }).collect(Collectors.toList());

        sensorsStatus.setAds(adsStatuses);

        List<RelayStatus> relayStatuses = node.getNodeSensors().getRelayChannels().stream().map(relay -> {
            RelayStatus status = new RelayStatus();
            status.setLastUpdate(relay.getLastUpdate());
            status.setStatus(relay.isOn());
            return status;
        }).collect(Collectors.toList());
        sensorsStatus.setRelays(relayStatuses);
        return sensorsStatus;
    }
}
