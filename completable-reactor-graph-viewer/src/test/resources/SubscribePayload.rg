{
  "version" : "v1_0_20",
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
      "fileNameLine" : 300
    },
    "builderPayloadSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 270
    },
    "processingItems" : [ {
      "type" : "PROCESSOR",
      "className" : "ServiceInfoProcessor",
      "id" : 0
    }, {
      "type" : "PROCESSOR",
      "className" : "UserProfileService",
      "id" : 0
    } ]
  },
  "processors" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BankProcessor",
      "id" : 0
    },
    "coordinates" : {
      "x" : 410,
      "y" : 440
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 301
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
      "id" : 0
    },
    "coordinates" : {
      "x" : 889,
      "y" : 465
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 302
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
      "id" : 0
    },
    "coordinates" : {
      "x" : 480,
      "y" : 120
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 303
    },
    "processorDoc" : [ "Service information processor load data for given service from database.", "Service could be not active. In that case purchase request will be denied." ],
    "handlerTitle" : "loadServiceInformation",
    "handlerDoc" : [ "Load service information from database." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 230
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "TransactionLogProcessor",
      "id" : 0
    },
    "coordinates" : {
      "x" : 420,
      "y" : 650
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 304
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
      "id" : 0
    },
    "coordinates" : {
      "x" : 680,
      "y" : 820
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 305
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
      "id" : 0
    },
    "coordinates" : {
      "x" : 770,
      "y" : 120
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 306
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
      "id" : 0
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
    "mergerDocs" : null,
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
        "fileNameLine" : 315
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 275
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
          "fileNameLine" : 275
        }
      },
      "mergeStatusSources" : {
        "STOP" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "onAny" : false,
      "complete" : true
    }, {
      "mergeStatuses" : [ "CONTINUE" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "ServiceInfoProcessor",
        "id" : 0
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
      "onAny" : false,
      "complete" : false
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "ServiceInfoProcessor",
      "id" : 0
    },
    "coordinates" : {
      "x" : 640,
      "y" : 280
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 231
    },
    "mergerTitle" : "updateServiceInfo",
    "mergerDocs" : null,
    "transitions" : [ {
      "mergeStatuses" : [ "CONTINUE" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : {
        "type" : "MERGE_POINT",
        "className" : null,
        "id" : 0
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
          "fileNameLine" : 279
        }
      },
      "mergeStatusSources" : {
        "CONTINUE" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "onAny" : false,
      "complete" : false
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
        "fileNameLine" : 313
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 280
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
          "fileNameLine" : 280
        }
      },
      "mergeStatusSources" : {
        "STOP" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "onAny" : false,
      "complete" : true
    } ]
  }, {
    "identity" : {
      "type" : "MERGE_POINT",
      "className" : null,
      "id" : 0
    },
    "coordinates" : {
      "x" : 702,
      "y" : 363
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.example.Configuration",
      "fileName" : "Configuration.java",
      "fileNameLine" : 257
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
        "id" : 0
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
          "fileNameLine" : 283
        }
      },
      "mergeStatusSources" : {
        "WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "onAny" : false,
      "complete" : false
    }, {
      "mergeStatuses" : [ "NO_WITHDRAWAL" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "UserLogProcessor",
        "id" : 0
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
          "fileNameLine" : 284
        }
      },
      "mergeStatusSources" : {
        "NO_WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "onAny" : false,
      "complete" : false
    }, {
      "mergeStatuses" : [ "NO_WITHDRAWAL" ],
      "isOnAny" : false,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "NotificationProcessor",
        "id" : 0
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
          "fileNameLine" : 285
        }
      },
      "mergeStatusSources" : {
        "NO_WITHDRAWAL" : {
          "className" : "ru.fix.completable.reactor.example.MergeStatus",
          "fileName" : null,
          "fileNameLine" : null
        }
      },
      "onAny" : false,
      "complete" : false
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "BankProcessor",
      "id" : 0
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
    "mergerDocs" : null,
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "TransactionLogProcessor",
        "id" : 0
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
        "fileNameLine" : 288
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "onAny" : true,
      "complete" : false
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "TransactionLogProcessor",
      "id" : 0
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
    "mergerDocs" : null,
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "UserLogProcessor",
        "id" : 0
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
        "fileNameLine" : 292
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "onAny" : true,
      "complete" : false
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "UserLogProcessor",
      "id" : 0
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
    "mergerDocs" : null,
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
        "fileNameLine" : 314
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 297
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.example.Configuration",
        "fileName" : "Configuration.java",
        "fileNameLine" : 296
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "onAny" : true,
      "complete" : true
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
    "fileNameLine" : 299
  },
  "buildGraphSource" : {
    "className" : "ru.fix.completable.reactor.example.Configuration",
    "fileName" : "Configuration.java",
    "fileNameLine" : 317
  }
}