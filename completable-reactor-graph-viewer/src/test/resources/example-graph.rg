{
  "payload" : {
    "payloadName" : "SmsRoutingPayload",
    "payloadClass" : "ru.fix.cpapsm.subscription.kafka.processing.chain.payload.sms.SmsRoutingPayload",
    "payloadDoc" : [ "Маршрутизация SMS сообщения, полученного от абонента", "(MO траффик)" ]
  },
  "processors" : {
    "MessageBodyAnalyzeProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : -30,
        "y" : 890
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 416
      },
      "processorDoc" : [ "Анализирует тело SMPP сообщения, принимая решение о дальнейшей маршрутизации" ],
      "requestDoc" : null,
      "handlerName" : "getSmsStopMessage",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 904
      }
    },
    "MessageBodyAnalyzeProcessor@1" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 1030,
        "y" : 780
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 417
      },
      "processorDoc" : [ "Анализирует тело SMPP сообщения, принимая решение о дальнейшей маршрутизации" ],
      "requestDoc" : null,
      "handlerName" : "getSmsStopMessage",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 122
      }
    },
    "RestrictSmsFetchProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 190,
        "y" : 320
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 418
      },
      "processorDoc" : [ "Получает данные по смс-запрету из postgresql, таблица cpapsm.restrict_sms." ],
      "requestDoc" : [ "Получает информацию о смс-запрете по тексту сообщения.", "Делает запрос в таблицу cpapsm.restrict_sms с условием sms_text = [текст пришедшего собщения]", "Вернет пустой результат, если тексту сообщения не соответствует ни один запрет." ],
      "handlerName" : "getSmsRestrictionMessage",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 222
      }
    },
    "ServiceFetchProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 320,
        "y" : 1140
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 426
      },
      "processorDoc" : [ "Получает данные по услуге из базы данных postgresql" ],
      "requestDoc" : [ "Получение услуг по короткому коду.", "Может вернуть пустой результат, если такой услуги нет в базе данных" ],
      "handlerName" : "fetchSubscriptionByShortCode",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 509
      }
    },
    "ServiceFetchProcessor@1" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 1180,
        "y" : 950
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 427
      },
      "processorDoc" : [ "Получает данные по услуге из базы данных postgresql" ],
      "requestDoc" : [ "Получение услуг по короткому коду.", "Может вернуть пустой результат, если такой услуги нет в базе данных" ],
      "handlerName" : "fetchSubscriptionByShortCode",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 509
      }
    },
    "ServiceFetchProcessor@2" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : -80,
        "y" : 1270
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 428
      },
      "processorDoc" : [ "Получает данные по услуге из базы данных postgresql" ],
      "requestDoc" : [ "Получение услуг по короткому коду.", "Может вернуть пустой результат, если такой услуги нет в базе данных" ],
      "handlerName" : "fetchSubscriptionByShortCode",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 509
      }
    },
    "ServiceFetchProcessor@3" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 1510,
        "y" : 960
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 429
      },
      "processorDoc" : [ "Получает данные по услуге из базы данных postgresql" ],
      "requestDoc" : [ "Получение услуг по короткому коду.", "Может вернуть пустой результат, если такой услуги нет в базе данных" ],
      "handlerName" : "fetchSubscriptionByShortCode",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 509
      }
    },
    "ServiceFetchProcessor@4" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 800,
        "y" : 490
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 430
      },
      "processorDoc" : [ "Получает данные по услуге из базы данных postgresql" ],
      "requestDoc" : [ "Получение всех услуг-покупок заведенных на короткий номер.", "Может вернуть 0, 1 или несколько покупок.", "Считаем, что количество покупок по короткому номеру невелико." ],
      "handlerName" : "fetchPurchasesByShortNumber",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 271
      }
    },
    "ServiceFetchProcessor@5" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 1130,
        "y" : 300
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 431
      },
      "processorDoc" : [ "Получает данные по услуге из базы данных postgresql" ],
      "requestDoc" : [ "Получение услуги по умолчанию для данного типа трафика.", "Результат не должен быть пустым" ],
      "handlerName" : "fetchDefaultService",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 792
      }
    },
    "ShortNumberCheckProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 490,
        "y" : -150
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 432
      },
      "processorDoc" : [ "Необходим для проверки короткого номера входящих smpp сообщений", "и применения дальнейшей маршрутизации запроса" ],
      "requestDoc" : [ "Возвращает информацию по короткому номеру по данному каналу (USSD/SMS)" ],
      "handlerName" : "getShortNumber",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 204
      }
    },
    "SmppAocProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : -490,
        "y" : 1250
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 433
      },
      "processorDoc" : [ "Проверка AoC-ов осуществляется путем сравнения информации, пришедшей", "в SMPP сообщении, с сохраненной информацией о действиях пользователя" ],
      "requestDoc" : null,
      "handlerName" : "checkAoc",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 139
      }
    },
    "SmppAocProcessor@1" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 740,
        "y" : 700
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 434
      },
      "processorDoc" : [ "Проверка AoC-ов осуществляется путем сравнения информации, пришедшей", "в SMPP сообщении, с сохраненной информацией о действиях пользователя" ],
      "requestDoc" : null,
      "handlerName" : "checkAoc",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 139
      }
    },
    "SmppMsisdnHistoryProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 780,
        "y" : -150
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 435
      },
      "processorDoc" : [ "Производит логирование параметров SMPP-сообщения в персистентное хранилище данных.", "Производится логирование всех SMPP-сообщений" ],
      "requestDoc" : [ "Производит логирование SMPP-трафика, записывая в таблицу HBase параметры запроса" ],
      "handlerName" : "logSmppRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 809
      }
    },
    "SmppNotificationProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : -560,
        "y" : 1650
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 436
      },
      "processorDoc" : [ "Производит нотификацию пользователя о событиях, возникших при роутинге SMPP трафика" ],
      "requestDoc" : [ "Отправляет СМС сообщение о некорректном ответе на AoC на указанный номер" ],
      "handlerName" : "notify",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 300
      }
    },
    "SmsRequestConstructProcessor@0" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : -40,
        "y" : 520
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 419
      },
      "processorDoc" : [ "Конструирует терминальные запросы по предварительно заполненным промежуточным данным" ],
      "requestDoc" : [ "Создает запрос для отправки в основной processor chain" ],
      "handlerName" : "createRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 184
      }
    },
    "SmsRequestConstructProcessor@1" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : -70,
        "y" : 1640
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 420
      },
      "processorDoc" : [ "Конструирует терминальные запросы по предварительно заполненным промежуточным данным" ],
      "requestDoc" : [ "Создает запрос для отправки в основной processor chain" ],
      "handlerName" : "createRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 184
      }
    },
    "SmsRequestConstructProcessor@2" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 590,
        "y" : 900
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 422
      },
      "processorDoc" : [ "Конструирует терминальные запросы по предварительно заполненным промежуточным данным" ],
      "requestDoc" : [ "Создает запрос для отправки в основной processor chain" ],
      "handlerName" : "createRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 184
      }
    },
    "SmsRequestConstructProcessor@3" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 1640,
        "y" : 1160
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 423
      },
      "processorDoc" : [ "Конструирует терминальные запросы по предварительно заполненным промежуточным данным" ],
      "requestDoc" : [ "Создает запрос для отправки в основной processor chain" ],
      "handlerName" : "createRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 184
      }
    },
    "SmsRequestConstructProcessor@4" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 910,
        "y" : 900
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 424
      },
      "processorDoc" : [ "Конструирует терминальные запросы по предварительно заполненным промежуточным данным" ],
      "requestDoc" : [ "Создает запрос для отправки в основной processor chain" ],
      "handlerName" : "createRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 184
      }
    },
    "SmsRequestConstructProcessor@5" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 1070,
        "y" : 1170
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 425
      },
      "processorDoc" : [ "Конструирует терминальные запросы по предварительно заполненным промежуточным данным" ],
      "requestDoc" : [ "Создает запрос для отправки в основной processor chain" ],
      "handlerName" : "createRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 184
      }
    },
    "SmsRequestConstructProcessor@6" : {
      "processorType" : "PLAIN",
      "coordinates" : {
        "x" : 1140,
        "y" : 490
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 421
      },
      "processorDoc" : [ "Конструирует терминальные запросы по предварительно заполненным промежуточным данным" ],
      "requestDoc" : [ "Создает запрос для отправки в основной processor chain" ],
      "handlerName" : "createRequest",
      "handleBySource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 184
      }
    },
    "UserInfoPayload@0" : {
      "processorType" : "SUBGRAPH",
      "coordinates" : {
        "x" : 330,
        "y" : 570
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 437
      },
      "processorDoc" : [ "Получение информации по msisdn.", "Данные получаются из xMNP-ml, HBase и PostgreSQL.", "Если никаких сведений не найдено, то вернется пустой результат." ],
      "requestDoc" : null,
      "handlerName" : null,
      "handleBySource" : null
    },
    "UserInfoPayload@1" : {
      "processorType" : "SUBGRAPH",
      "coordinates" : {
        "x" : 630,
        "y" : 260
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 438
      },
      "processorDoc" : [ "Получение информации по msisdn.", "Данные получаются из xMNP-ml, HBase и PostgreSQL.", "Если никаких сведений не найдено, то вернется пустой результат." ],
      "requestDoc" : null,
      "handlerName" : null,
      "handleBySource" : null
    }
  },
  "startPoint" : {
    "coordinates" : {
      "x" : 690,
      "y" : -240
    },
    "processors" : [ "ShortNumberCheckProcessor@0", "SmppMsisdnHistoryProcessor@0" ]
  },
  "mergeGroups" : [ {
    "mergePoints" : [ {
      "processor" : "SmppMsisdnHistoryProcessor@0",
      "coordinates" : {
        "x" : 850,
        "y" : -50
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 439
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 810
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : false,
        "mergeProcessor" : "ShortNumberCheckProcessor@0",
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ ]
      } ]
    }, {
      "processor" : "ShortNumberCheckProcessor@0",
      "coordinates" : {
        "x" : 590,
        "y" : -10
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 440
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 205
      },
      "transitions" : [ {
        "mergeStatuses" : [ "DEFAULT_SMS_PURCHASE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "ServiceFetchProcessor@5",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "DEFAULT_SMS_PURCHASE",
          "docs" : [ "Запрос пришёл на незарегистрированный КН по SMS" ]
        } ]
      }, {
        "mergeStatuses" : [ "PLATFORM_SHORT_NUMBER" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "RestrictSmsFetchProcessor@0",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "PLATFORM_SHORT_NUMBER",
          "docs" : [ "Запрос пришёл на служебный КН" ]
        } ]
      }, {
        "mergeStatuses" : [ "OTHER_SERVICE_SHORT_NUMBER" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "UserInfoPayload@1",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "OTHER_SERVICE_SHORT_NUMBER",
          "docs" : [ "Запрос пришёл на зарегистрированный КН" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "ServiceFetchProcessor@5",
      "coordinates" : {
        "x" : 1210,
        "y" : 400
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 441
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 793
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@6",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmsRequestConstructProcessor@6",
      "coordinates" : {
        "x" : 1390,
        "y" : 500
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 442
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 185
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 1500,
          "y" : 510
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 463
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "RestrictSmsFetchProcessor@0",
      "coordinates" : {
        "x" : 240,
        "y" : 420
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 443
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 223
      },
      "transitions" : [ {
        "mergeStatuses" : [ "RESTRICTION_MESSAGE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@0",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "RESTRICTION_MESSAGE",
          "docs" : [ "Текст сообщение равен запросу на установку запрета" ]
        } ]
      }, {
        "mergeStatuses" : [ "OTHER_MESSAGE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "UserInfoPayload@0",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "OTHER_MESSAGE",
          "docs" : [ "Иной текст сообщения" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmsRequestConstructProcessor@0",
      "coordinates" : {
        "x" : -10,
        "y" : 630
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 444
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 185
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : -120,
          "y" : 710
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 464
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "MessageBodyAnalyzeProcessor@0",
      "coordinates" : {
        "x" : 0,
        "y" : 1010
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 445
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 905
      },
      "transitions" : [ {
        "mergeStatuses" : [ "UNSUBSCRIBE_MESSAGE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "ServiceFetchProcessor@0",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "UNSUBSCRIBE_MESSAGE",
          "docs" : [ "Текст сообщения содержит префикс отписки" ]
        } ]
      }, {
        "mergeStatuses" : [ "AOC_MESSAGE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmppAocProcessor@0",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "AOC_MESSAGE",
          "docs" : [ "Ответ на AoC" ]
        } ]
      }, {
        "mergeStatuses" : [ "OTHER_MESSAGE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "ServiceFetchProcessor@2",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "OTHER_MESSAGE",
          "docs" : [ "Иной текст сообщения" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "ServiceFetchProcessor@2",
      "coordinates" : {
        "x" : -10,
        "y" : 1400
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 449
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 510
      },
      "transitions" : [ {
        "mergeStatuses" : [ "SINGLE_SERVICE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@1",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SINGLE_SERVICE",
          "docs" : [ "Найден один сервис" ]
        } ]
      }, {
        "mergeStatuses" : [ "SERVICE_NOT_FOUND" ],
        "isOnAny" : false,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : -130,
          "y" : 1460
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 467
        },
        "transitionsDoc" : [ {
          "mergeStatus" : "SERVICE_NOT_FOUND",
          "docs" : [ "Сервис не найден" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmppAocProcessor@0",
      "coordinates" : {
        "x" : -340,
        "y" : 1560
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 446
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 140
      },
      "transitions" : [ {
        "mergeStatuses" : [ "SMS_SUBSCRIPTION_AOC", "PAY_BY_CLICK_AOC" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@1",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SMS_SUBSCRIPTION_AOC",
          "docs" : [ "Корректный AoC. Продолжение SMS-подписки." ]
        }, {
          "mergeStatus" : "PAY_BY_CLICK_AOC",
          "docs" : [ "Корректный AoC. Продолжение PbC. (web-покупки)" ]
        } ]
      }, {
        "mergeStatuses" : [ "INCORRECT_AOC" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmppNotificationProcessor@0",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "INCORRECT_AOC",
          "docs" : [ "Некорректный AoC. У абонента есть активный аок, но присланный аок не совпадает с активным" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmppNotificationProcessor@0",
      "coordinates" : {
        "x" : -490,
        "y" : 1760
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 448
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 301
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : -540,
          "y" : 1850
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 466
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "UserInfoPayload@0",
      "coordinates" : {
        "x" : 260,
        "y" : 730
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 460
      },
      "mergeSource" : null,
      "transitions" : [ {
        "mergeStatuses" : [ "OK" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "MessageBodyAnalyzeProcessor@0",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "OK",
          "docs" : null
        } ]
      }, {
        "mergeStatuses" : [ "CANCEL" ],
        "isOnAny" : false,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 380,
          "y" : 840
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 476
        },
        "transitionsDoc" : [ {
          "mergeStatus" : "CANCEL",
          "docs" : null
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "ServiceFetchProcessor@0",
      "coordinates" : {
        "x" : 300,
        "y" : 1270
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 450
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 510
      },
      "transitions" : [ {
        "mergeStatuses" : [ "SINGLE_SERVICE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@1",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SINGLE_SERVICE",
          "docs" : [ "Найден один сервис" ]
        } ]
      }, {
        "mergeStatuses" : [ "SERVICE_NOT_FOUND" ],
        "isOnAny" : false,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 480,
          "y" : 1360
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 468
        },
        "transitionsDoc" : [ {
          "mergeStatus" : "SERVICE_NOT_FOUND",
          "docs" : [ "Сервис не найден" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmsRequestConstructProcessor@1",
      "coordinates" : {
        "x" : 10,
        "y" : 1760
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 447
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 185
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : -10,
          "y" : 1860
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 465
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "UserInfoPayload@1",
      "coordinates" : {
        "x" : 710,
        "y" : 390
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 461
      },
      "mergeSource" : null,
      "transitions" : [ {
        "mergeStatuses" : [ "OK" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "ServiceFetchProcessor@4",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "OK",
          "docs" : null
        } ]
      }, {
        "mergeStatuses" : [ "CANCEL" ],
        "isOnAny" : false,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 540,
          "y" : 430
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 477
        },
        "transitionsDoc" : [ {
          "mergeStatus" : "CANCEL",
          "docs" : null
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "ServiceFetchProcessor@4",
      "coordinates" : {
        "x" : 820,
        "y" : 600
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 451
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 272
      },
      "transitions" : [ {
        "mergeStatuses" : [ "SINGLE_SERVICE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmppAocProcessor@1",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SINGLE_SERVICE",
          "docs" : [ "Найден один сервис" ]
        } ]
      }, {
        "mergeStatuses" : [ "MORE_THAN_ONE_SERVICE" ],
        "isOnAny" : false,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 1200,
          "y" : 660
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 469
        },
        "transitionsDoc" : [ {
          "mergeStatus" : "MORE_THAN_ONE_SERVICE",
          "docs" : [ "Найдено больше одно сервиса" ]
        } ]
      }, {
        "mergeStatuses" : [ "SERVICE_NOT_FOUND" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "MessageBodyAnalyzeProcessor@1",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SERVICE_NOT_FOUND",
          "docs" : [ "Сервис не найден" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmppAocProcessor@1",
      "coordinates" : {
        "x" : 820,
        "y" : 810
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 452
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 140
      },
      "transitions" : [ {
        "mergeStatuses" : [ "NO_AOC" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@4",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "NO_AOC",
          "docs" : [ "AoC не найден.  У абонента нет активного аок." ]
        } ]
      }, {
        "mergeStatuses" : [ "SMS_PURCHASE_AOC" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@2",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SMS_PURCHASE_AOC",
          "docs" : [ "Корректный AoC. Продолжение SMS-покупки. Не реализовано" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmsRequestConstructProcessor@4",
      "coordinates" : {
        "x" : 980,
        "y" : 1000
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 453
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 185
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 960,
          "y" : 1080
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 470
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmsRequestConstructProcessor@2",
      "coordinates" : {
        "x" : 700,
        "y" : 1000
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 454
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 185
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 680,
          "y" : 1090
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 471
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "MessageBodyAnalyzeProcessor@1",
      "coordinates" : {
        "x" : 1350,
        "y" : 840
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 455
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 123
      },
      "transitions" : [ {
        "mergeStatuses" : [ "UNSUBSCRIBE_MESSAGE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "ServiceFetchProcessor@3",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "UNSUBSCRIBE_MESSAGE",
          "docs" : [ "Текст сообщения содержит префикс отписки" ]
        } ]
      }, {
        "mergeStatuses" : [ "OTHER_MESSAGE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "ServiceFetchProcessor@1",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "OTHER_MESSAGE",
          "docs" : [ "Иной текст сообщения" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "ServiceFetchProcessor@1",
      "coordinates" : {
        "x" : 1410,
        "y" : 1090
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 456
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 510
      },
      "transitions" : [ {
        "mergeStatuses" : [ "SINGLE_SERVICE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@5",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SINGLE_SERVICE",
          "docs" : [ "Найден один сервис" ]
        } ]
      }, {
        "mergeStatuses" : [ "SERVICE_NOT_FOUND" ],
        "isOnAny" : false,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 1390,
          "y" : 1260
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 472
        },
        "transitionsDoc" : [ {
          "mergeStatus" : "SERVICE_NOT_FOUND",
          "docs" : [ "Сервис не найден" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "ServiceFetchProcessor@3",
      "coordinates" : {
        "x" : 1840,
        "y" : 1060
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 457
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 510
      },
      "transitions" : [ {
        "mergeStatuses" : [ "SINGLE_SERVICE" ],
        "isOnAny" : false,
        "isComplete" : false,
        "mergeProcessor" : null,
        "handleByProcessor" : "SmsRequestConstructProcessor@3",
        "completeCoordinates" : {
          "x" : 100,
          "y" : 100
        },
        "completeCoordinatesSource" : null,
        "transitionsDoc" : [ {
          "mergeStatus" : "SINGLE_SERVICE",
          "docs" : [ "Найден один сервис" ]
        } ]
      }, {
        "mergeStatuses" : [ "SERVICE_NOT_FOUND" ],
        "isOnAny" : false,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 2000,
          "y" : 1110
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 473
        },
        "transitionsDoc" : [ {
          "mergeStatus" : "SERVICE_NOT_FOUND",
          "docs" : [ "Сервис не найден" ]
        } ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmsRequestConstructProcessor@5",
      "coordinates" : {
        "x" : 1150,
        "y" : 1280
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 458
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 185
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 1120,
          "y" : 1370
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 474
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  }, {
    "mergePoints" : [ {
      "processor" : "SmsRequestConstructProcessor@3",
      "coordinates" : {
        "x" : 1720,
        "y" : 1270
      },
      "coordinatesSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 459
      },
      "mergeSource" : {
        "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
        "fileName" : "SmppRoutingConfig.java",
        "fileNameLine" : 185
      },
      "transitions" : [ {
        "mergeStatuses" : [ ],
        "isOnAny" : true,
        "isComplete" : true,
        "mergeProcessor" : null,
        "handleByProcessor" : null,
        "completeCoordinates" : {
          "x" : 1680,
          "y" : 1370
        },
        "completeCoordinatesSource" : {
          "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfig",
          "fileName" : "SmppRoutingConfig.java",
          "fileNameLine" : 475
        },
        "transitionsDoc" : [ ]
      } ]
    } ]
  } ],
  "serializationPointSource" : {
    "className" : "ru.fix.cpapsm.subscription.kafka.processing.config.SmppRoutingConfigTest",
    "fileName" : "SmppRoutingConfigTest.java",
    "fileNameLine" : 129
  }
}