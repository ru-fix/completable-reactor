{
  "version" : "v1_0_21",
  "payload" : {
    "payloadName" : "SmsCptPrepaidPayload",
    "payloadClass" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidPayload",
    "payloadDoc" : [ "Отправка SMS контент провайдеру от пользователя с prepaid тарификацией" ]
  },
  "startPoint" : {
    "coordinates" : {
      "x" : 146,
      "y" : -237
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 220
    },
    "builderPayloadSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 195
    },
    "processingItems" : [ {
      "type" : "PROCESSOR",
      "className" : "BrtProxyHandler",
      "name" : "initialRequestProcessor"
    } ]
  },
  "processors" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxNoMoneyContent"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 539
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxPrepaidFailed"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 539
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BrtProxyHandler",
      "name" : "initialRequestProcessor"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "processorDoc" : [ "Вызывает методы для работы с биллингом", "Резервирование средств, прерывание или успешная тарификация, например" ],
    "handlerTitle" : "initNewSession",
    "handlerDoc" : [ "Резервирует средства в биллинге оператора, вызывая инициализацию BRT-сессии" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 239
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SystemAttributeProcessor",
      "name" : "systemAttributeMaxPushTimeoutProcessor"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "processorDoc" : [ "Works with ServiceAttribute entity" ],
    "handlerTitle" : "findByNameOrThrow",
    "handlerDoc" : [ "Return SystemAttribute or throws exception if it's not found" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 408
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogPrepaidFailed"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "processorDoc" : null,
    "handlerTitle" : "writeSmsBuy",
    "handlerDoc" : [ "Запись информации о SMS-покупке в MSISDN_TX" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 456
    }
  } ],
  "subgraphs" : [ ],
  "mergePoints" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BrtProxyHandler",
      "name" : "initialRequestProcessor"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 240
    },
    "mergerTitle" : null,
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ "OK" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "SystemAttributeProcessor",
        "name" : "systemAttributeMaxPushTimeoutProcessor"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "OK",
        "docs" : [ "Выполнен успешный резерв средств" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "OK" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 200
        }
      },
      "mergeStatusSources" : {
        "OK" : {
          "className" : "ru.fix.cpapsm.subscription.common.graph.model.BrtPrepaidResult",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "BRT_TARIFF_ERROR" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "BillingJournal",
        "name" : "billingTxPrepaidFailed"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "BRT_TARIFF_ERROR",
        "docs" : [ "Ошибка BRT при запросе Initial Request. Возможные причины" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "BRT_TARIFF_ERROR" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 201
        }
      },
      "mergeStatusSources" : {
        "BRT_TARIFF_ERROR" : {
          "className" : "ru.fix.cpapsm.subscription.common.graph.model.BrtPrepaidResult",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "NO_MONEY_CONTENT" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "BillingJournal",
        "name" : "billingTxNoMoneyContent"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "NO_MONEY_CONTENT",
        "docs" : [ "Ошибка недостатка средств в ответе на запрос к BRT на контентном счете" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "NO_MONEY_CONTENT" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 202
        }
      },
      "mergeStatusSources" : {
        "NO_MONEY_CONTENT" : {
          "className" : "ru.fix.cpapsm.subscription.common.graph.model.BrtPrepaidResult",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SystemAttributeProcessor",
      "name" : "systemAttributeMaxPushTimeoutProcessor"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 409
    },
    "mergerTitle" : null,
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : true,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 206
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 206
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : true,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxPrepaidFailed"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 540
    },
    "mergerTitle" : null,
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "UserSmppBuyOperationsLogger",
        "name" : "userLogPrepaidFailed"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 209
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxNoMoneyContent"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 540
    },
    "mergerTitle" : null,
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : true,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 213
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 213
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : true,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogPrepaidFailed"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 457
    },
    "mergerTitle" : null,
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : true,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 216
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 216
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : true,
      "onAny" : true
    } ]
  } ],
  "serializationPointSource" : {
    "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfigIT",
    "fileName" : "SmsCptPrepaidGraphConfigIT.java",
    "fileNameLine" : 155
  },
  "coordinatesSource" : {
    "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
    "fileName" : "SmsCptPrepaidGraphConfig.java",
    "fileNameLine" : 219
  },
  "buildGraphSource" : {
    "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
    "fileName" : "SmsCptPrepaidGraphConfig.java",
    "fileNameLine" : 221
  }
}