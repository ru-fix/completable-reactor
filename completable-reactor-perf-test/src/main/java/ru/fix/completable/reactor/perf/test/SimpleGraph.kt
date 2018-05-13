package ru.fix.completable.reactor.perf.test

import ru.fix.completable.reactor.graph.kotlin.Graph
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.ThreadLocalRandom

class SimpleGraph : Graph<SimplePayload>() {

    private val poolHttp = Executors.newFixedThreadPool(10)
    private val poolPG = Executors.newFixedThreadPool(10)
    private val poolHBase = Executors.newFixedThreadPool(10)
    private val poolSmpp = Executors.newFixedThreadPool(10)


    private val xmnpmlProcessor = AsyncService(poolHttp, 50)

    private val userProfileProcessor = AsyncService(poolHBase, 22)

    private val serviceInfoProcessor = AsyncService(poolPG, 12)

    private val pSmppParametersFetch = AsyncService(poolPG, 12)

    private val restrictionProcessor = AsyncService(poolHBase, 14)

    private val systemAttributeProcessor = AsyncService(poolPG, 12)

    private val contentCountProcessor = AsyncService(poolHBase, 23)

    private val sendSmsProcessor = AsyncService(poolSmpp, 47)

    private val smsHistoryAndStatisticsProcessor = AsyncService(poolHBase, 17)

    private val hBaseClient = AsyncService(poolHBase, 11)


    private val readMsisdnInfo =
            handler {
                hBaseClient.asyncMethod(request.arg)

            }.withRoutingMerger { result ->
                if (result.isNotEmpty()) {
                    MsisdnInfoReadResult.NOT_FOUND
                } else {
                    MsisdnInfoReadResult.FOUND
                }
            }

    private val checkTransaction = router {

        if (request.arg.isNullOrBlank()) {
            TxCheckResult.EMPTY
        } else {
            if ((request.arg + 123).length > 3) {
                TxCheckResult.ACTIVE
            } else {
                TxCheckResult.NOT_ACTIVE
            }
        }
    }

    private val readSmppTransactId = handler {

        if (request.arg.isNotEmpty()) {
            CompletableFuture.completedFuture(request.arg)
        } else {
            CompletableFuture.completedFuture("")
        }
    }.withMerger { smppTransactId ->
        intermedium.smppTransactId = smppTransactId
    }

    private val createMsisdnInfoFromXmnpml = handler {
        xmnpmlProcessor.asyncMethod(request.arg)
    }.withRoutingMerger { result ->
        if (result.isEmpty()) {
            CreateMsisdnFromXmnpmlResult.NOT_CREATED
        } else {
            CreateMsisdnFromXmnpmlResult.CREATED
        }
    }

    private val createMsisdnInfoFromOpNumRange = handler {
        userProfileProcessor.asyncMethod(request.arg)
    }.withRoutingMerger { msisdnInfoResult ->
        if (msisdnInfoResult.length > 2) {
            CreateMsisdnFromOperationNumberResult.CREATED
        } else {
            CreateMsisdnFromOperationNumberResult.NOT_CREATED
        }
    }

    private val storeMsisdnInfo = handler {
        hBaseClient.asyncMethod(request.arg)
    }.withEmptyMerger()

    private val readServiceInfo = handler {
        serviceInfoProcessor.asyncMethod(request.arg.length.toString())
    }.withMerger { result -> intermedium.serviceInfo = result }

    private val findServiceInfoByTransaction = handler {
        serviceInfoProcessor.asyncMethod(request.arg)
    }.withRoutingMerger { result ->
        if (result.isNotEmpty()) {
            intermedium.serviceInfo = result
            YesNoResult.YES
        } else {
            YesNoResult.NO
        }
    }

    private val fetchSmppParametersForService = handler {
        pSmppParametersFetch.asyncMethod((request.arg.length / 3).toString())
    }.withMerger { result -> intermedium.smppParameters = result }


    private val checkUnlimPush = router {
        if (intermedium.smppParameters.isNotEmpty())
            YesNoResult.YES
        else
            YesNoResult.NO
    }

    private val generateSmppTransactId = router {
        intermedium.smppTransactId = ThreadLocalRandom.current().nextInt().toString()
        YesNoResult.YES
    }

    private val checkServiceIsActive = router {
        val serviceInfo = intermedium.serviceInfo

        if (serviceInfo.isNotEmpty())
            YesNoResult.YES
        else
            YesNoResult.NO
    }


    private val sendSMSContent =

            handler {
                sendSmsProcessor.asyncMethod(
                        intermedium.serviceInfo + intermedium.smppParameters + intermedium.smppTransactId)

            }.withRoutingMerger { response ->
                if (response.isNotEmpty()) {
                    intermedium.smsMsisdnHistory = response + "history"
                    Result.SUCCESS
                } else {
                    Result.FAIL
                }
            }

    private val writeMsisdnTx = handler {
        hBaseClient.asyncMethod(intermedium.serviceInfo + intermedium.smppTransactId)
    }.withEmptyMerger()

    private val checkRestriction = handler {
        restrictionProcessor.asyncMethod(
                intermedium.serviceInfo)
    }.withRoutingMerger { restrictionCheckResult ->
        if (restrictionCheckResult.isEmpty()) {
            RestrictionCheckResult.HAS_RESTRICTION
        } else {
            RestrictionCheckResult.NO_RESTRICTION
        }
    }

    private val incrementAndCheckContentCount = handler {
        contentCountProcessor.asyncMethod(intermedium.smppParameters + intermedium.smppTransactId)
    }.withRoutingMerger {
        if((it + it.length).length > 2) {

            ContentCountProcessorResult.NOT_EXCEEDS
        }
        else {
            ContentCountProcessorResult.EXCEEDS
        }
    }

    private val readMaxPushTimeoutSystemAttribute = handler {
        systemAttributeProcessor.asyncMethod("123")
    }.withMerger { restrictionCheckResult ->
        intermedium.maxPushTimeOut = restrictionCheckResult.trim()
    }


    private val registerInvalidDestinationAddress11 =
            handler {
                logSmsFireStatisticsAndSetErrorCode(this, VaspSmppConstants.INVALID_DEST_ADDRESS)
            }
                    .withMerger { _ -> response.result = VaspSmppConstants.INVALID_DEST_ADDRESS.toString() }


    private val registerInvalidTransaction1315 =
            handler {
                logSmsFireStatisticsAndSetErrorCode(this, VaspSmppConstants.INVALID_TRANSACTION)
            }
                    .withMerger { _ -> response.result = VaspSmppConstants.INVALID_TRANSACTION.toString() }


    private val registerMaxPushTimeOut1376 = handler { logSmsFireStatisticsAndSetErrorCode(this, VaspSmppConstants.MAX_PUSH_TIMEOUT) }.withMerger { _ -> response.result = VaspSmppConstants.MAX_PUSH_TIMEOUT.toString() }
    private val registerServiceNotActive1316 = handler { logSmsFireStatisticsAndSetErrorCode(this, VaspSmppConstants.SERVICE_IS_NOT_ACTIVE) }.withMerger { _ -> response.result = VaspSmppConstants.SERVICE_IS_NOT_ACTIVE.toString() }
    private val registerAbonentHasRestriction1580 = handler { logSmsFireStatisticsAndSetErrorCode(this, VaspSmppConstants.ABONENT_HAS_RESTRICTION) }.withMerger { _ -> response.result = VaspSmppConstants.ABONENT_HAS_RESTRICTION.toString() }
    private val registerCannotDeliver1560 = handler { logSmsFireStatisticsAndSetErrorCode(this, VaspSmppConstants.CANNOT_DELIVER) }.withMerger { _ -> response.result = VaspSmppConstants.CANNOT_DELIVER.toString() }
    private val registerMaxContentReplyExceeded1368 = handler { logSmsFireStatisticsAndSetErrorCode(this, VaspSmppConstants.MAX_REPLY) }.withMerger{ _ -> response.result = VaspSmppConstants.MAX_REPLY.toString() }

    init {

        payload()
                .handleBy(readMsisdnInfo)

        readMsisdnInfo
                .on(MsisdnInfoReadResult.FOUND).handleBy(findServiceInfoByTransaction)
                .on(MsisdnInfoReadResult.NOT_FOUND).handleBy(createMsisdnInfoFromXmnpml)

        findServiceInfoByTransaction
                .on(YesNoResult.YES).handleBy(fetchSmppParametersForService)
                .on(YesNoResult.NO).handleBy(readServiceInfo)

        createMsisdnInfoFromXmnpml
                .on(CreateMsisdnFromXmnpmlResult.CREATED).handleBy(storeMsisdnInfo)
                .on(CreateMsisdnFromXmnpmlResult.NOT_CREATED).handleBy(createMsisdnInfoFromOpNumRange)

        createMsisdnInfoFromOpNumRange
                .on(CreateMsisdnFromOperationNumberResult.CREATED).handleBy(storeMsisdnInfo)
                .on(CreateMsisdnFromOperationNumberResult.NOT_CREATED).handleBy(registerInvalidDestinationAddress11)

        storeMsisdnInfo
                .onAny().handleBy(readServiceInfo);

        readServiceInfo
                .onAny().handleBy(fetchSmppParametersForService);

        fetchSmppParametersForService
                .onAny().handleBy(readMaxPushTimeoutSystemAttribute);

        readMaxPushTimeoutSystemAttribute
                .onAny().handleBy(readSmppTransactId);

        readSmppTransactId
                .onAny().handleBy(checkTransaction);

        checkTransaction
                // Запрос от КП содержит активную транзакцию
                .on(TxCheckResult.ACTIVE).handleBy(incrementAndCheckContentCount)
                // Запрос от КП содержит НЕ неактивную транзакцию
                .on(TxCheckResult.NOT_ACTIVE).handleBy(registerMaxPushTimeOut1376)
                // Запрос от КП НЕ содержит транзакцию
                .on(TxCheckResult.EMPTY).handleBy(checkUnlimPush);

        checkUnlimPush
                .on(YesNoResult.YES).handleBy(generateSmppTransactId)
                .on(YesNoResult.NO).handleBy(registerInvalidTransaction1315);

        generateSmppTransactId
                .onAny().handleBy(checkServiceIsActive);

        checkServiceIsActive
                .on(YesNoResult.YES).handleBy(checkRestriction)
                .on(YesNoResult.NO).handleBy(registerServiceNotActive1316);

        checkRestriction
                .on(RestrictionCheckResult.HAS_RESTRICTION).handleBy(registerAbonentHasRestriction1580)
                .on(RestrictionCheckResult.BLACK_LISTED).handleBy(registerAbonentHasRestriction1580)
                .on(RestrictionCheckResult.NO_RESTRICTION).handleBy(sendSMSContent);

        incrementAndCheckContentCount
                .on(ContentCountProcessorResult.EXCEEDS).handleBy(registerMaxContentReplyExceeded1368)
                .on(ContentCountProcessorResult.NOT_EXCEEDS).handleBy(sendSMSContent);

        sendSMSContent
                .on(Result.SUCCESS).handleBy(writeMsisdnTx)
                .on(Result.FAIL).handleBy(registerCannotDeliver1560)

        writeMsisdnTx
                .onAny().complete();

        registerInvalidDestinationAddress11.onAny().complete();
        registerInvalidTransaction1315.onAny().complete();
        registerServiceNotActive1316.onAny().complete();
        registerMaxPushTimeOut1376.onAny().complete();
        registerAbonentHasRestriction1580.onAny().complete();
        registerCannotDeliver1560.onAny().complete();
        registerMaxContentReplyExceeded1368.onAny().complete();

        coordinates()
                .pd(-29, -435)
                .vx(checkServiceIsActive, 395, 1018)
                .vx(checkTransaction, 73, 686)
                .vx(checkUnlimPush, 282, 778)
                .vx(generateSmppTransactId, 391, 893)
                .vx(checkRestriction, 283, 1143, 505, 1221)
                .vx(createMsisdnInfoFromOpNumRange, -729, -157, -521, -42)
                .vx(createMsisdnInfoFromXmnpml, -229, -236, -80, -149)
                .vx(fetchSmppParametersForService, -68, 264, 217, 331)
                .vx(findServiceInfoByTransaction, 74, -99, 246, 25)
                .vx(incrementAndCheckContentCount, -179, 908, 56, 1077)
                .vx(readMaxPushTimeoutSystemAttribute, 73, 404, 214, 456)
                .vx(readMsisdnInfo, 10, -383, 67, -314)
                .vx(readServiceInfo, -118, 135, 95, 181)
                .vx(readSmppTransactId, 59, 533, 206, 604)
                .vx(registerAbonentHasRestriction1580, 709, 1348, 845, 1458)
                .vx(registerCannotDeliver1560, 266, 1511, 369, 1599)
                .vx(registerInvalidDestinationAddress11, -669, 105, -552, 266)
                .vx(registerInvalidTransaction1315, 831, 772, 1126, 779)
                .vx(registerMaxContentReplyExceeded1368, -530, 1067, -578, 1079)
                .vx(registerMaxPushTimeOut1376, -343, 739, -545, 754)
                .vx(registerServiceNotActive1316, 787, 1016, 1145, 1019)
                .vx(sendSMSContent, 11, 1343, 106, 1442)
                .vx(storeMsisdnInfo, -87, -31, -10, 62)
                .vx(writeMsisdnTx, -331, 1528, -155, 1602)
                .ct(registerAbonentHasRestriction1580, 845, 1551)
                .ct(registerCannotDeliver1560, 384, 1697)
                .ct(registerInvalidDestinationAddress11, -566, 387)
                .ct(registerInvalidTransaction1315, 1242, 780)
                .ct(registerMaxContentReplyExceeded1368, -752, 1084)
                .ct(registerMaxPushTimeOut1376, -726, 762)
                .ct(registerServiceNotActive1316, 1310, 1018)
                .ct(writeMsisdnTx, -158, 1669);
    }

    internal fun logSmsFireStatisticsAndSetErrorCode(payload: SimplePayload,
                                                     errorCode: VaspSmppConstants): CompletableFuture<String> {

        return smsHistoryAndStatisticsProcessor.asyncMethod(payload.request.arg + payload.intermedium.smppTransactId)
    }

    enum class Result { SUCCESS, FAIL }
    enum class MsisdnInfoReadResult { FOUND, NOT_FOUND }

    enum class TxCheckResult {
        ACTIVE,
        NOT_ACTIVE,
        EMPTY
    }

    enum class SuccessResult {
        SUCCESS
    }

    enum class YesNoResult {
        YES, NO
    }

    enum class RestrictionCheckResult {
        HAS_RESTRICTION,
        BLACK_LISTED,
        NO_RESTRICTION
    }

    enum class CreateMsisdnFromXmnpmlResult {
        CREATED,
        NOT_CREATED
    }

    enum class CreateMsisdnFromOperationNumberResult {
        CREATED,
        NOT_CREATED
    }

    enum class ContentCountProcessorResult {
        EXCEEDS,
        NOT_EXCEEDS
    }

    enum class VaspSmppConstants(val value: Int) {

        INVALID_DEST_ADDRESS(11),

        MAX_PUSH_TIMEOUT(1376),

        INVALID_TRANSACTION(1315),

        SERVICE_IS_NOT_ACTIVE(1316),

        ABONENT_HAS_RESTRICTION(1580),

        CANNOT_DELIVER(1560),

        MAX_REPLY(1368);
    }

}