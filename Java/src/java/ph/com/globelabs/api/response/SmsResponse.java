package ph.com.globelabs.api.response;

import java.util.List;

import org.apache.http.NameValuePair;

public class SmsResponse {

    private String commandLength;
    private String commandId;
    private String commandStatus;
    private String sequenceNumber;
    private String command;
    private String serviceType;
    private String sourceAddrTon;
    private String sourceAddrNpi;
    private String sourceAddr;
    private String destAddrTon;
    private String destAddrNpi;
    private String destinationAddr;
    private String esmClass;
    private String protocolId;
    private String priorityFlag;
    private String scheduleDeliveryTime;
    private String validityPeriod;
    private String registeredDelivery;
    private String replaceIfPresentFlag;
    private String dataCoding;
    private String smDefaultMsgId;
    private String shortMessage;
    private String sourceNetworkType;
    private String destNetworkType;
    private String message;

    public SmsResponse(List<NameValuePair> nameValuePairs) {
        for (NameValuePair nameValuePair : nameValuePairs) {
            if ("command_length".equals(nameValuePair.getName())) {
                this.commandLength = nameValuePair.getValue();
            } else if ("command_id".equals(nameValuePair.getName())) {
                this.commandId = nameValuePair.getValue();
            } else if ("command_status".equals(nameValuePair.getName())) {
                this.commandStatus = nameValuePair.getValue();
            } else if ("sequence_number".equals(nameValuePair.getName())) {
                this.sequenceNumber = nameValuePair.getValue();
            } else if ("command".equals(nameValuePair.getName())) {
                this.command = nameValuePair.getValue();
            } else if ("service_type".equals(nameValuePair.getName())) {
                this.serviceType = nameValuePair.getValue();
            } else if ("source_addr_ton".equals(nameValuePair.getName())) {
                this.sourceAddrTon = nameValuePair.getValue();
            } else if ("source_addr_npi".equals(nameValuePair.getName())) {
                this.sourceAddrNpi = nameValuePair.getValue();
            } else if ("source_addr".equals(nameValuePair.getName())) {
                this.sourceAddr = nameValuePair.getValue();
            } else if ("dest_addr_ton".equals(nameValuePair.getName())) {
                this.destAddrTon = nameValuePair.getValue();
            } else if ("dest_addr_npi".equals(nameValuePair.getName())) {
                this.destAddrNpi = nameValuePair.getValue();
            } else if ("destination_addr".equals(nameValuePair.getName())) {
                this.destinationAddr = nameValuePair.getValue();
            } else if ("esm_class".equals(nameValuePair.getName())) {
                this.esmClass = nameValuePair.getValue();
            } else if ("protocol_id".equals(nameValuePair.getName())) {
                this.protocolId = nameValuePair.getValue();
            } else if ("priority_flag".equals(nameValuePair.getName())) {
                this.priorityFlag = nameValuePair.getValue();
            } else if ("schedule_delivery_time".equals(nameValuePair.getName())) {
                this.scheduleDeliveryTime = nameValuePair.getValue();
            } else if ("validity_period".equals(nameValuePair.getName())) {
                this.validityPeriod = nameValuePair.getValue();
            } else if ("registered_delivery".equals(nameValuePair.getName())) {
                this.registeredDelivery = nameValuePair.getValue();
            } else if ("replace_if_present_flag"
                    .equals(nameValuePair.getName())) {
                this.replaceIfPresentFlag = nameValuePair.getValue();
            } else if ("data_coding".equals(nameValuePair.getName())) {
                this.dataCoding = nameValuePair.getValue();
            } else if ("sm_default_msg_id".equals(nameValuePair.getName())) {
                this.smDefaultMsgId = nameValuePair.getValue();
            } else if ("short_message[message]".equals(nameValuePair.getName())) {
                this.shortMessage = nameValuePair.getValue();
            } else if ("source_network_type".equals(nameValuePair.getName())) {
                this.sourceNetworkType = nameValuePair.getValue();
            } else if ("dest_network_type".equals(nameValuePair.getName())) {
                this.destNetworkType = nameValuePair.getValue();
            } else if ("message_payload[message]".equals(nameValuePair
                    .getName())) {
                this.message = nameValuePair.getValue();
            }
        }
    }

    public String getCommandLength() {
        return commandLength;
    }

    public String getCommandId() {
        return commandId;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public String getCommand() {
        return command;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getSourceAddrTon() {
        return sourceAddrTon;
    }

    public String getSourceAddrNpi() {
        return sourceAddrNpi;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public String getDestAddrTon() {
        return destAddrTon;
    }

    public String getDestAddrNpi() {
        return destAddrNpi;
    }

    public String getDestinationAddr() {
        return destinationAddr;
    }

    public String getEsmClass() {
        return esmClass;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public String getPriorityFlag() {
        return priorityFlag;
    }

    public String getScheduleDeliveryTime() {
        return scheduleDeliveryTime;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public String getRegisteredDelivery() {
        return registeredDelivery;
    }

    public String getReplaceIfPresentFlag() {
        return replaceIfPresentFlag;
    }

    public String getDataCoding() {
        return dataCoding;
    }

    public String getSmDefaultMsgId() {
        return smDefaultMsgId;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public String getSourceNetworkType() {
        return sourceNetworkType;
    }

    public String getDestNetworkType() {
        return destNetworkType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SmsResponse [sourceAddr=" + sourceAddr + ", destinationAddr="
                + destinationAddr + ", message=" + message + "]";
    }

}
