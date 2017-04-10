package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;

/**
 *
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@Data
public class CRProcessor<PayloadType> implements Processor<PayloadType>, CRProcessingItem {

    final CRProcessorDescription<PayloadType> processorDescription;

    int id = 0;

    CRProcessor(CRProcessorDescription<PayloadType> description) {
        this.processorDescription = description;
    }

    @Override
    public CRProcessor<PayloadType> setId(int id){
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getProfilingName() {
        return processorDescription.processorType.getSimpleName();
    }

    @Override
    public String getDebugName() {
        return processorDescription.processorType.getName() + "@" + id;
    }

    @Override
    public ReactorGraphModel.Identity serializeIdentity() {
        return new ReactorGraphModel.Identity()
                .setType(ReactorGraphModel.Identity.Type.PROCESSOR)
                .setClassName(processorDescription.processorType.getSimpleName())
                .setId(id);
    }
}
