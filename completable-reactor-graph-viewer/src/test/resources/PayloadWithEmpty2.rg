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
      "fileNameLine" : 329
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
      "name" : "billingTxCpDisconnected"
    },
    "coordinates" : {
      "x" : 615,
      "y" : 842
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 330
    },
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 717
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxCpErrorStatus"
    },
    "coordinates" : {
      "x" : 118,
      "y" : 854
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 331
    },
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 717
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxCpResponseExpired"
    },
    "coordinates" : {
      "x" : 1143,
      "y" : 837
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 332
    },
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 717
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxNoMoneyContent"
    },
    "coordinates" : {
      "x" : -947,
      "y" : 258
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 333
    },
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 717
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxNoMoneyOrdinary"
    },
    "coordinates" : {
      "x" : -441,
      "y" : 257
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 334
    },
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 717
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxPrepaidFailed"
    },
    "coordinates" : {
      "x" : 632,
      "y" : 176
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 335
    },
    "processorDoc" : null,
    "handlerTitle" : "cpaLogFailedTarification",
    "handlerDoc" : [ "Логирование факта неуспешной тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 717
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BillingJournal",
      "name" : "billingTxSuccessCase"
    },
    "coordinates" : {
      "x" : -427,
      "y" : 797
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 336
    },
    "processorDoc" : null,
    "handlerTitle" : "Запись в таблицу BillingTx",
    "handlerDoc" : [ "Логирование факта тарификации" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 698
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BrtProxyHandler",
      "name" : "cancelReservationRequestProcessor"
    },
    "coordinates" : {
      "x" : 641,
      "y" : 1627
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 337
    },
    "processorDoc" : [ "Вызывает методы для работы с биллингом", "Резервирование средств, прерывание или успешная тарификация, например" ],
    "handlerTitle" : "cancelReservation",
    "handlerDoc" : [ "Завершает сессию для абонента, отменяя резервирования средств" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 487
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BrtProxyHandler",
      "name" : "initialRequestProcessor"
    },
    "coordinates" : {
      "x" : 73,
      "y" : -170
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 338
    },
    "processorDoc" : [ "Вызывает методы для работы с биллингом", "Резервирование средств, прерывание или успешная тарификация, например" ],
    "handlerTitle" : "initNewSession",
    "handlerDoc" : [ "Резервирует средства в биллинге оператора, вызывая инициализацию BRT-сессии" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 417
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BrtProxyHandler",
      "name" : "terminateRequestProcessor"
    },
    "coordinates" : {
      "x" : -426,
      "y" : 643
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 339
    },
    "processorDoc" : [ "Вызывает методы для работы с биллингом", "Резервирование средств, прерывание или успешная тарификация, например" ],
    "handlerTitle" : "terminateSession",
    "handlerDoc" : [ "Завершает сессию для абонента, списывая зарезервированные средства" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 462
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmppMessageTransferHandler",
      "name" : "deliverSmProcessor"
    },
    "coordinates" : {
      "x" : 102,
      "y" : 476
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 340
    },
    "processorDoc" : [ "Ответственен за посылку USSD/SMS сообщения контент провайдеру" ],
    "handlerTitle" : "sendSmsMessage",
    "handlerDoc" : [ "Посылает КП SMS сообщение" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 450
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageCpDisconnectedProcessor"
    },
    "coordinates" : {
      "x" : 593,
      "y" : 1511
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 341
    },
    "processorDoc" : [ "Используется для отправки СМС нотификаций пользователя" ],
    "handlerTitle" : "sendFailMessage",
    "handlerDoc" : [ "Отправка сообщения о неуспешной тарификации пользователя" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 768
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageCpErrorStatusProcessor"
    },
    "coordinates" : {
      "x" : 44,
      "y" : 1506
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 342
    },
    "processorDoc" : [ "Используется для отправки СМС нотификаций пользователя" ],
    "handlerTitle" : "sendFailMessage",
    "handlerDoc" : [ "Отправка сообщения о неуспешной тарификации пользователя" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 768
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageCpExpiredProcessor"
    },
    "coordinates" : {
      "x" : 1169,
      "y" : 1513
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 343
    },
    "processorDoc" : [ "Используется для отправки СМС нотификаций пользователя" ],
    "handlerTitle" : "sendFailMessage",
    "handlerDoc" : [ "Отправка сообщения о неуспешной тарификации пользователя" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 768
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageNoMoneyContentProcessor"
    },
    "coordinates" : {
      "x" : -925,
      "y" : 703
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 344
    },
    "processorDoc" : [ "Используется для отправки СМС нотификаций пользователя" ],
    "handlerTitle" : "sendFailMessage",
    "handlerDoc" : [ "Отправка сообщения о неуспешной тарификации пользователя" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 768
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageNoMoneyOrdinaryProcessor"
    },
    "coordinates" : {
      "x" : -979,
      "y" : 908
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 345
    },
    "processorDoc" : [ "Используется для отправки СМС нотификаций пользователя" ],
    "handlerTitle" : "sendFailMessage",
    "handlerDoc" : [ "Отправка сообщения о неуспешной тарификации пользователя" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 768
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessagePrepaidFailedProcessor"
    },
    "coordinates" : {
      "x" : 565,
      "y" : 489
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 346
    },
    "processorDoc" : [ "Используется для отправки СМС нотификаций пользователя" ],
    "handlerTitle" : "sendFailMessage",
    "handlerDoc" : [ "Отправка сообщения о неуспешной тарификации пользователя" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 768
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireCpDisconnectedActionProcessor"
    },
    "coordinates" : {
      "x" : 586,
      "y" : 1175
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 347
    },
    "processorDoc" : [ "Сервис для регистрации событий статистики" ],
    "handlerTitle" : "fireAction",
    "handlerDoc" : [ "Зарегистрировать событие статистики" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 508
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireCpResponseErrorActionProcessor"
    },
    "coordinates" : {
      "x" : 55,
      "y" : 1174
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 348
    },
    "processorDoc" : [ "Сервис для регистрации событий статистики" ],
    "handlerTitle" : "fireAction",
    "handlerDoc" : [ "Зарегистрировать событие статистики" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 508
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireCpResponseExpiredActionProcessor"
    },
    "coordinates" : {
      "x" : 1117,
      "y" : 1169
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 349
    },
    "processorDoc" : [ "Сервис для регистрации событий статистики" ],
    "handlerTitle" : "fireAction",
    "handlerDoc" : [ "Зарегистрировать событие статистики" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 508
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireSubscribeActionProcessor"
    },
    "coordinates" : {
      "x" : -465,
      "y" : 1099
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 350
    },
    "processorDoc" : [ "Сервис для регистрации событий статистики" ],
    "handlerTitle" : "fireAction",
    "handlerDoc" : [ "Зарегистрировать событие статистики" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 508
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "statBrtNoMoneyContent"
    },
    "coordinates" : {
      "x" : -881,
      "y" : 560
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 351
    },
    "processorDoc" : [ "Сервис для регистрации событий статистики" ],
    "handlerTitle" : "fireAction",
    "handlerDoc" : [ "Зарегистрировать событие статистики" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 508
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "statBrtNoMoneyOrdinary"
    },
    "coordinates" : {
      "x" : -363,
      "y" : 530
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 352
    },
    "processorDoc" : [ "Сервис для регистрации событий статистики" ],
    "handlerTitle" : "fireAction",
    "handlerDoc" : [ "Зарегистрировать событие статистики" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 508
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
      "fileNameLine" : 586
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "deletePurchaseTransactionDisconnectedProcessor"
    },
    "coordinates" : {
      "x" : 543,
      "y" : 1305
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 353
    },
    "processorDoc" : [ "Производит запись в профиль пользователя" ],
    "handlerTitle" : "deleteSmsPurchaseTransaction",
    "handlerDoc" : [ "Удаляет информацию о транзакции активной SMS покупки" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 611
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "deletePurchaseTransactionErrorResponseProcessor"
    },
    "coordinates" : {
      "x" : -26,
      "y" : 1356
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 354
    },
    "processorDoc" : [ "Производит запись в профиль пользователя" ],
    "handlerTitle" : "deleteSmsPurchaseTransaction",
    "handlerDoc" : [ "Удаляет информацию о транзакции активной SMS покупки" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 611
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "deletePurchaseTransactionResponseExpiredProcessor"
    },
    "coordinates" : {
      "x" : 1093,
      "y" : 1335
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 355
    },
    "processorDoc" : [ "Производит запись в профиль пользователя" ],
    "handlerTitle" : "deleteSmsPurchaseTransaction",
    "handlerDoc" : [ "Удаляет информацию о транзакции активной SMS покупки" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 611
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "userProfileWriteProcessor"
    },
    "coordinates" : {
      "x" : 60,
      "y" : 259
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 356
    },
    "processorDoc" : [ "Производит запись в профиль пользователя" ],
    "handlerTitle" : "Запись идентификатора транзакции для СМС сообщения",
    "handlerDoc" : [ "Записывает информацию о транзакции активной SMS покупки" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 601
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogCpDisconnect"
    },
    "coordinates" : {
      "x" : 603,
      "y" : 998
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 357
    },
    "processorDoc" : null,
    "handlerTitle" : "writeSmsBuy",
    "handlerDoc" : [ "Запись информации о SMS-покупке в MSISDN_TX" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 634
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogCpErrorStatus"
    },
    "coordinates" : {
      "x" : 87,
      "y" : 1000
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 358
    },
    "processorDoc" : null,
    "handlerTitle" : "writeSmsBuy",
    "handlerDoc" : [ "Запись информации о SMS-покупке в MSISDN_TX" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 634
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogCpResponseExpired"
    },
    "coordinates" : {
      "x" : 1123,
      "y" : 996
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 359
    },
    "processorDoc" : null,
    "handlerTitle" : "writeSmsBuy",
    "handlerDoc" : [ "Запись информации о SMS-покупке в MSISDN_TX" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 634
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogNoMoneyContent"
    },
    "coordinates" : {
      "x" : -968,
      "y" : 430
    },
    "coordinatesSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 360
    },
    "processorDoc" : null,
    "handlerTitle" : "writeSmsBuy",
    "handlerDoc" : [ "Запись информации о SMS-покупке в MSISDN_TX" ],
    "withHandlerSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 634
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogNoMoneyOrdinary"
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
      "fileNameLine" : 634
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
      "fileNameLine" : 634
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogSuccessCase"
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
      "fileNameLine" : 634
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
      "x" : 218,
      "y" : -41
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 418
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
          "fileNameLine" : 203
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
      "mergeStatuses" : [ "NO_MONEY_ORDINARY" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "BillingJournal",
        "name" : "billingTxNoMoneyOrdinary"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "NO_MONEY_ORDINARY",
        "docs" : [ "Ошибка недостатка средств в ответе на запрос к BRT на основном счете" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "NO_MONEY_ORDINARY" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 204
        }
      },
      "mergeStatusSources" : {
        "NO_MONEY_ORDINARY" : {
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
          "fileNameLine" : 205
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
      "className" : "BillingJournal",
      "name" : "billingTxPrepaidFailed"
    },
    "coordinates" : {
      "x" : 812,
      "y" : 254
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 718
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
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogPrepaidFailed"
    },
    "coordinates" : {
      "x" : 817,
      "y" : 413
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 635
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
        "className" : "SmsNotificationSender",
        "name" : "sendErrorMessagePrepaidFailedProcessor"
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
        "fileNameLine" : 213
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessagePrepaidFailedProcessor"
    },
    "coordinates" : {
      "x" : 801,
      "y" : 570
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 769
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
        "x" : 803,
        "y" : 633
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 396
      },
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
      "fileNameLine" : 587
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
        "className" : "UserInfoWriteHandler",
        "name" : "userProfileWriteProcessor"
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
        "fileNameLine" : 219
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "userProfileWriteProcessor"
    },
    "coordinates" : {
      "x" : 272,
      "y" : 381
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 603
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
        "className" : "SmppMessageTransferHandler",
        "name" : "deliverSmProcessor"
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
        "fileNameLine" : 222
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
      "name" : "billingTxNoMoneyOrdinary"
    },
    "coordinates" : {
      "x" : -240,
      "y" : 328
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 718
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
        "name" : "userLogNoMoneyOrdinary"
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
        "fileNameLine" : 225
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogNoMoneyOrdinary"
    },
    "coordinates" : {
      "x" : -246,
      "y" : 474
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 635
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
        "className" : "StatisticsCollectorService",
        "name" : "statBrtNoMoneyOrdinary"
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
        "fileNameLine" : 229
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "statBrtNoMoneyOrdinary"
    },
    "coordinates" : {
      "x" : -633,
      "y" : 832
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 509
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
        "className" : "SmsNotificationSender",
        "name" : "sendErrorMessageNoMoneyOrdinaryProcessor"
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
        "fileNameLine" : 232
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageNoMoneyOrdinaryProcessor"
    },
    "coordinates" : {
      "x" : -711,
      "y" : 991
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 769
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
        "x" : -689,
        "y" : 1058
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 395
      },
      "completeSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 235
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 235
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
      "name" : "billingTxNoMoneyContent"
    },
    "coordinates" : {
      "x" : -749,
      "y" : 335
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 718
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
        "name" : "userLogNoMoneyContent"
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
        "fileNameLine" : 239
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogNoMoneyContent"
    },
    "coordinates" : {
      "x" : -747,
      "y" : 506
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 635
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
        "className" : "StatisticsCollectorService",
        "name" : "statBrtNoMoneyContent"
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
        "fileNameLine" : 243
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "statBrtNoMoneyContent"
    },
    "coordinates" : {
      "x" : -691,
      "y" : 653
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 509
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
        "className" : "SmsNotificationSender",
        "name" : "sendErrorMessageNoMoneyContentProcessor"
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
        "fileNameLine" : 246
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageNoMoneyContentProcessor"
    },
    "coordinates" : {
      "x" : -772,
      "y" : 791
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 769
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
        "x" : -760,
        "y" : 845
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 394
      },
      "completeSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 250
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 250
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : true,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmppMessageTransferHandler",
      "name" : "deliverSmProcessor"
    },
    "coordinates" : {
      "x" : 493,
      "y" : 585
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 451
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
        "className" : "BrtProxyHandler",
        "name" : "terminateRequestProcessor"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "OK",
        "docs" : [ "Сообщение успешно отправлено" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "OK" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 255
        }
      },
      "mergeStatusSources" : {
        "OK" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.CpSendResult",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "DISCONNECT" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "BillingJournal",
        "name" : "billingTxCpDisconnected"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "DISCONNECT",
        "docs" : [ "Нет связи с КП для отправки запроса абонента" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "DISCONNECT" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 258
        }
      },
      "mergeStatusSources" : {
        "DISCONNECT" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.CpSendResult",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "ERROR_RESPONSE" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "BillingJournal",
        "name" : "billingTxCpErrorStatus"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "ERROR_RESPONSE",
        "docs" : [ "КП вернул ошибку на перенаправленный запрос абонента" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "ERROR_RESPONSE" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 259
        }
      },
      "mergeStatusSources" : {
        "ERROR_RESPONSE" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.CpSendResult",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "RESPONSE_EXPIRED" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "BillingJournal",
        "name" : "billingTxCpResponseExpired"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "RESPONSE_EXPIRED",
        "docs" : [ "Истек срок ожидания ответа от КП" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "RESPONSE_EXPIRED" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
          "fileName" : "SmsCptPrepaidGraphConfig.java",
          "fileNameLine" : 260
        }
      },
      "mergeStatusSources" : {
        "RESPONSE_EXPIRED" : {
          "className" : "ru.fix.cpapsm.smpp.server.graph.CpSendResult",
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
      "className" : "BrtProxyHandler",
      "name" : "terminateRequestProcessor"
    },
    "coordinates" : {
      "x" : -248,
      "y" : 721
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 463
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
        "className" : "BillingJournal",
        "name" : "billingTxSuccessCase"
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
        "fileNameLine" : 264
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
      "name" : "billingTxSuccessCase"
    },
    "coordinates" : {
      "x" : -248,
      "y" : 875
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 699
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
        "name" : "userLogSuccessCase"
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
        "fileNameLine" : 267
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogSuccessCase"
    },
    "coordinates" : {
      "x" : -248,
      "y" : 1035
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 635
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
        "className" : "StatisticsCollectorService",
        "name" : "fireSubscribeActionProcessor"
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
        "fileNameLine" : 270
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireSubscribeActionProcessor"
    },
    "coordinates" : {
      "x" : -245,
      "y" : 1197
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 509
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
        "x" : -254,
        "y" : 1263
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 397
      },
      "completeSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 273
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 273
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
      "name" : "billingTxCpDisconnected"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 718
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
        "name" : "userLogCpDisconnect"
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
        "fileNameLine" : 276
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogCpDisconnect"
    },
    "coordinates" : {
      "x" : 823,
      "y" : 1082
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 635
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
        "className" : "StatisticsCollectorService",
        "name" : "fireCpDisconnectedActionProcessor"
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
        "fileNameLine" : 279
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireCpDisconnectedActionProcessor"
    },
    "coordinates" : {
      "x" : 828,
      "y" : 1253
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 509
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
        "className" : "UserInfoWriteHandler",
        "name" : "deletePurchaseTransactionDisconnectedProcessor"
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
        "fileNameLine" : 282
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
      "name" : "billingTxCpErrorStatus"
    },
    "coordinates" : {
      "x" : 100,
      "y" : 100
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 718
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
        "name" : "userLogCpErrorStatus"
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
        "fileNameLine" : 285
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogCpErrorStatus"
    },
    "coordinates" : {
      "x" : 297,
      "y" : 1094
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 635
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
        "className" : "StatisticsCollectorService",
        "name" : "fireCpResponseErrorActionProcessor"
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
        "fileNameLine" : 288
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireCpResponseErrorActionProcessor"
    },
    "coordinates" : {
      "x" : 296,
      "y" : 1286
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 509
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
        "className" : "UserInfoWriteHandler",
        "name" : "deletePurchaseTransactionErrorResponseProcessor"
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
        "fileNameLine" : 291
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
      "name" : "billingTxCpResponseExpired"
    },
    "coordinates" : {
      "x" : 1354,
      "y" : 923
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 718
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
        "name" : "userLogCpResponseExpired"
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
        "fileNameLine" : 294
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserSmppBuyOperationsLogger",
      "name" : "userLogCpResponseExpired"
    },
    "coordinates" : {
      "x" : 1356,
      "y" : 1068
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 635
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
        "className" : "StatisticsCollectorService",
        "name" : "fireCpResponseExpiredActionProcessor"
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
        "fileNameLine" : 297
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "StatisticsCollectorService",
      "name" : "fireCpResponseExpiredActionProcessor"
    },
    "coordinates" : {
      "x" : 1373,
      "y" : 1264
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 509
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
        "className" : "UserInfoWriteHandler",
        "name" : "deletePurchaseTransactionResponseExpiredProcessor"
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
        "fileNameLine" : 300
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "deletePurchaseTransactionResponseExpiredProcessor"
    },
    "coordinates" : {
      "x" : 1395,
      "y" : 1432
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 612
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
        "className" : "SmsNotificationSender",
        "name" : "sendErrorMessageCpExpiredProcessor"
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
        "fileNameLine" : 304
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "deletePurchaseTransactionErrorResponseProcessor"
    },
    "coordinates" : {
      "x" : 301,
      "y" : 1431
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 612
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
        "className" : "SmsNotificationSender",
        "name" : "sendErrorMessageCpErrorStatusProcessor"
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
        "fileNameLine" : 307
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserInfoWriteHandler",
      "name" : "deletePurchaseTransactionDisconnectedProcessor"
    },
    "coordinates" : {
      "x" : 831,
      "y" : 1418
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 612
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
        "className" : "SmsNotificationSender",
        "name" : "sendErrorMessageCpDisconnectedProcessor"
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
        "fileNameLine" : 311
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageCpExpiredProcessor"
    },
    "coordinates" : {
      "x" : 1407,
      "y" : 1597
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 769
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
        "className" : "BrtProxyHandler",
        "name" : "cancelReservationRequestProcessor"
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
        "fileNameLine" : 315
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageCpErrorStatusProcessor"
    },
    "coordinates" : {
      "x" : 294,
      "y" : 1611
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 769
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
        "className" : "BrtProxyHandler",
        "name" : "cancelReservationRequestProcessor"
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
        "fileNameLine" : 319
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "SmsNotificationSender",
      "name" : "sendErrorMessageCpDisconnectedProcessor"
    },
    "coordinates" : {
      "x" : 862,
      "y" : 1556
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 769
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
        "className" : "BrtProxyHandler",
        "name" : "cancelReservationRequestProcessor"
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
        "fileNameLine" : 323
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BrtProxyHandler",
      "name" : "cancelReservationRequestProcessor"
    },
    "coordinates" : {
      "x" : 847,
      "y" : 1709
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
      "fileName" : "SmsCptPrepaidGraphConfig.java",
      "fileNameLine" : 488
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
        "x" : 857,
        "y" : 1781
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 393
      },
      "completeSource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 326
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
        "fileName" : "SmsCptPrepaidGraphConfig.java",
        "fileNameLine" : 326
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
    "fileNameLine" : 328
  },
  "buildGraphSource" : {
    "className" : "ru.fix.cpapsm.smpp.server.graph.SmsCptPrepaidGraphConfig",
    "fileName" : "SmsCptPrepaidGraphConfig.java",
    "fileNameLine" : 399
  }
}