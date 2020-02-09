package com.faryard.api.DTO.node;

public class NodeSensorStatusResponse extends NodeSimpleResponse {

        private SensorsStatus sensorsStatus;

        public SensorsStatus getSensorsStatus() {
            return sensorsStatus;
        }

        public void setSensorsStatus(SensorsStatus sensorsStatus) {
            this.sensorsStatus = sensorsStatus;
        }



}
