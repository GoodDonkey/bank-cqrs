package query.version;

import com.bankcqrs.events.HolderCreated;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

public class HolderCreatedV0_V1EventUpcaster extends SingleEventUpcaster {
    
    private static SimpleSerializedType targetType = new SimpleSerializedType(HolderCreated.class.getTypeName(), null);
    
    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.getType().equals(targetType);
    }
    
    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.upcastPayload(
                new SimpleSerializedType(targetType.getName(), "1.0"),
                JsonNode.class,
                event -> {
                    // 예전 이벤트에 company 라는 필드가 없으면 "N/A" 를 삽입한다.
                    ((ObjectNode) event).put("company", "N/A");
                    return event;
                });
    }
}
