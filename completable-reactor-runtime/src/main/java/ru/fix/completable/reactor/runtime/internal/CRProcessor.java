package ru.fix.completable.reactor.runtime.internal;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.fix.completable.reactor.runtime.dsl.Processor;

/**
 *
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
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

    @Override
    public String getProfilingName() {
        return processorDescription.processorType.getSimpleName();
    }

    @Override
    public String getDebugName() {
        return processorDescription.processorType.getName() + "@" + id;
    }
}
