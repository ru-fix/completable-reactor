{
  "version" : "v1_0_21",
  "payload" : {
    "payloadName" : "SubscribePayload",
    "payloadClass" : "ru.fix.completable.reactor.example.chain.SubscribePayload",
    "payloadDoc" : [ ]
  },
  "startPoint" : {
    "coordinates" : {
      "x" : 680,
      "y" : 60
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 297
    },
    "builderPayloadSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 267
    },
    "processingItems" : [ {
      "type" : "PROCESSOR",
      "className" : "ServiceInfoProcessor",
      "name" : "gSubscribeServiceInfo"
    }, {
      "type" : "PROCESSOR",
      "className" : "UserProfileService",
      "name" : "gUserProfile"
    } ]
  },
  "processors" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BankProcessor",
      "name" : "gBankSubsribe"
    },
    "coordinates" : {
      "x" : 410,
      "y" : 440
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 298
    },
    "processorDoc" : [ "Withdraw money from Bank account ", "for given user." ],
    "handlerTitle" : "withdrawMoney",
    "handlerDoc" : [ "Withdraw money from account and print it to stdout", "If there is no money on account no withdrawing would be applied", "so account balance will always stay positive." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 126
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "NotificationProcessor",
      "name" : "gNotification"
    },
    "coordinates" : {
      "x" : 889,
      "y" : 465
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 299
    },
    "processorDoc" : [ "Send notifications to external system", "Notification about events like purchase.", "We do not need to wait result of notification action for building correct response.", "So we attach notification completable future to async task manager and continue without waiting for its result" ],
    "handlerTitle" : "sendPurchaseNotification",
    "handlerDoc" : [ "Send notification about purchase event" ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 87
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "ServiceInfoProcessor",
      "name" : "gSubscribeServiceInfo"
    },
    "coordinates" : {
      "x" : 480,
      "y" : 120
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 300
    },
    "processorDoc" : [ "Service information processor load data for given service from database.", "Service could be not active. In that case purchase request will be denied." ],
    "handlerTitle" : "loadServiceInformation",
    "handlerDoc" : [ "Load service information from database." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 227
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "TransactionLogProcessor",
      "name" : "gTxLog"
    },
    "coordinates" : {
      "x" : 420,
      "y" : 650
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 301
    },
    "processorDoc" : [ "Store information about transactions.", "Each transaction have user id and money amount" ],
    "handlerTitle" : "logTransactioin",
    "handlerDoc" : null,
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 70
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserLogProcessor",
      "name" : "gUserLog"
    },
    "coordinates" : {
      "x" : 680,
      "y" : 820
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 302
    },
    "processorDoc" : [ "Logs information about usres actions.", "Log contains user id" ],
    "handlerTitle" : "logAction",
    "handlerDoc" : null,
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 79
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserProfileService",
      "name" : "gUserProfile"
    },
    "coordinates" : {
      "x" : 770,
      "y" : 120
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 303
    },
    "processorDoc" : [ "UserProfileServices loads information about user from remote database.", "User profile contains information whether user is blocked or not." ],
    "handlerTitle" : "loadUserProfileById",
    "handlerDoc" : [ "Load user profile from database by user id." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 43
    }
  } ],
  "subgraphs" : [ ],
  "mergePoints" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserProfileService",
      "name" : "gUserProfile"
    },
    "coordinates" : {
      "x" : 830,
      "y" : 250
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 44
    },
    "mergerTitle" : "updateUserProfile",
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ "STOP" ],
      "isOnAny" : false,
      "isComplete" : true,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 920,
        "y" : 300
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 312
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 272
      },
      "transitionsDoc" : [ {
        "mergeStatus" : "STOP",
        "docs" : [ "Stop processing" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "STOP" : {
          "className" : "ru.fix.completable.reactor.example.Configuration",
          "fileName" : "Configuration.java",
          "fileNameLine" : 272
        }
      },
      "mergeStatusSources" : {
        "STOP" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : true,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "CONTINUE" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "ServiceInfoProcessor",
        "name" : "gSubscribeServiceInfo"
      },
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "CONTINUE",
        "docs" : [ "Continue processing" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "CONTINUE" : {
          "className" : "ru.fix.completable.reactor.example.Configuration",
          "fileName" : "Configuration.java",
          "fileNameLine" : 273
        }
      },
      "mergeStatusSources" : {
        "CONTINUE" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
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
      "className" : "ServiceInfoProcessor",
      "name" : "gSubscribeServiceInfo"
    },
    "coordinates" : {
      "x" : 640,
      "y" : 280
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 228
    },
    "mergerTitle" : "updateServiceInfo",
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ "CONTINUE" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : {
        "type" : "MERGE_POINT",
        "className" : null,
        "name" : "trialPeriodCheck"
      },
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "CONTINUE",
        "docs" : [ "Continue processing" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "CONTINUE" : {
          "className" : "ru.fix.completable.reactor.example.Configuration",
          "fileName" : "Configuration.java",
          "fileNameLine" : 276
        }
      },
      "mergeStatusSources" : {
        "CONTINUE" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "STOP" ],
      "isOnAny" : false,
      "isComplete" : true,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 480,
        "y" : 310
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 310
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 277
      },
      "transitionsDoc" : [ {
        "mergeStatus" : "STOP",
        "docs" : [ "Stop processing" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "STOP" : {
          "className" : "ru.fix.completable.reactor.example.Configuration",
          "fileName" : "Configuration.java",
          "fileNameLine" : 277
        }
      },
      "mergeStatusSources" : {
        "STOP" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : true,
      "onAny" : false
    } ]
  }, {
    "identity" : {
      "type" : "MERGE_POINT",
      "className" : null,
      "name" : "trialPeriodCheck"
    },
    "coordinates" : {
      "x" : 702,
      "y" : 363
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 253
    },
    "mergerTitle" : "checkTrialPeriod",
    "mergerDocs" : [ "Checks whether service supports trial period" ],
    "transitions" : [ {
      "mergeStatuses" : [ "WITHDRAWAL" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "BankProcessor",
        "name" : "gBankSubsribe"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "WITHDRAWAL",
        "docs" : [ "Withdraw money required" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.Configuration",
          "fileName" : "Configuration.java",
          "fileNameLine" : 280
        }
      },
      "mergeStatusSources" : {
        "WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "NO_WITHDRAWAL" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "UserLogProcessor",
        "name" : "gUserLog"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "NO_WITHDRAWAL",
        "docs" : [ "No withdrawal required" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "NO_WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.Configuration",
          "fileName" : "Configuration.java",
          "fileNameLine" : 281
        }
      },
      "mergeStatusSources" : {
        "NO_WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "complete" : false,
      "onAny" : false
    }, {
      "mergeStatuses" : [ "NO_WITHDRAWAL" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "NotificationProcessor",
        "name" : "gNotification"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ {
        "mergeStatus" : "NO_WITHDRAWAL",
        "docs" : [ "No withdrawal required" ]
      } ],
      "transitionOnAnySource" : null,
      "transitionOnStatusSource" : {
        "NO_WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.Configuration",
          "fileName" : "Configuration.java",
          "fileNameLine" : 282
        }
      },
      "mergeStatusSources" : {
        "NO_WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
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
      "className" : "BankProcessor",
      "name" : "gBankSubsribe"
    },
    "coordinates" : {
      "x" : 480,
      "y" : 550
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 127
    },
    "mergerTitle" : "updateNewAmount",
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "TransactionLogProcessor",
        "name" : "gTxLog"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
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
      "className" : "TransactionLogProcessor",
      "name" : "gTxLog"
    },
    "coordinates" : {
      "x" : 530,
      "y" : 770
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 71
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
        "className" : "UserLogProcessor",
        "name" : "gUserLog"
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 289
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserLogProcessor",
      "name" : "gUserLog"
    },
    "coordinates" : {
      "x" : 760,
      "y" : 930
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 80
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
        "x" : 740,
        "y" : 1020
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 311
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 294
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 293
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : true,
      "onAny" : true
    } ]
  } ],
  "serializationPointSource" : {
    "className" : "ru.fix.completable.reactor.example.ConfigurationTest",
    "fileName" : "ConfigurationTest.java",
    "fileNameLine" : 29
  },
  "coordinatesSource" : {
    "className" : "ru.fix.completable.reactor.example.Configuration",
    "fileName" : "Configuration.java",
    "fileNameLine" : 296
  },
  "buildGraphSource" : {
    "className" : "ru.fix.completable.reactor.example.Configuration",
    "fileName" : "Configuration.java",
    "fileNameLine" : 314
  }
}