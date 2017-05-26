{
  "version" : "v1_0_11",
  "payload" : {
    "payloadName" : "ParentGraphPayload",
    "payloadClass" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$ParentGraphPayload",
    "payloadDoc" : [ "Parent graph is a simple graph that calls subgraph during its flow.", "extends IdListPayload", "IdListPayload contains list of integer ids.", " When IdListPayload goes through processors chain each processor adds their id", " so at the end we can clarify by witch processor and in what order this payload was processed." ]
  },
  "startPoint" : {
    "coordinates" : {
      "x" : 460,
      "y" : 120
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 396
    },
    "builderPayloadSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 374
    },
    "processingItems" : [ {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 1
    } ]
  },
  "processors" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 1
    },
    "coordinates" : {
      "x" : 406,
      "y" : 228
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 388
    },
    "processorDoc" : [ "IdProcessor return it's id" ],
    "handlerTitle" : "handle",
    "handlerDoc" : [ "Return id of this processor.", "Id is Long." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 71
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 2
    },
    "coordinates" : {
      "x" : 626,
      "y" : 408
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 389
    },
    "processorDoc" : [ "IdProcessor return it's id" ],
    "handlerTitle" : "handle",
    "handlerDoc" : [ "Return id of this processor.", "Id is Long." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 71
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 3
    },
    "coordinates" : {
      "x" : 415,
      "y" : 730
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 390
    },
    "processorDoc" : [ "IdProcessor return it's id" ],
    "handlerTitle" : "handle",
    "handlerDoc" : [ "Return id of this processor.", "Id is Long." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 71
    }
  } ],
  "subgraphs" : [ {
    "identity" : {
      "type" : "SUBGRAPH",
      "className" : "SubgraphPayload",
      "id" : 0
    },
    "coordinates" : {
      "x" : 195,
      "y" : 418
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 391
    },
    "buildSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 366
    },
    "subgraphTitle" : "SubgraphPayload",
    "subgraphDoc" : [ "Subgraph behave the same way as plain processor.", "The only difference is that instead of simple async operation CompletableReactor launches subgraph execution" ]
  } ],
  "mergePoints" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 1
    },
    "coordinates" : {
      "x" : 475,
      "y" : 342
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 72
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
        "className" : "IdProcessor",
        "id" : 2
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 379
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    }, {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : false,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : {
        "type" : "SUBGRAPH",
        "className" : "SubgraphPayload",
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
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 380
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "SUBGRAPH",
      "className" : "SubgraphPayload",
      "id" : 0
    },
    "coordinates" : {
      "x" : 341,
      "y" : 565
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 368
    },
    "mergerTitle" : null,
    "mergerDocs" : null,
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : false,
      "mergeProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "IdProcessor",
        "id" : 2
      },
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 382
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 2
    },
    "coordinates" : {
      "x" : 488,
      "y" : 620
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 72
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
        "className" : "IdProcessor",
        "id" : 3
      },
      "completeCoordinates" : {
        "x" : 100,
        "y" : 100
      },
      "completeCoordinatesSource" : null,
      "completeSource" : null,
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 383
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : false,
      "onAny" : true
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 3
    },
    "coordinates" : {
      "x" : 490,
      "y" : 840
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 72
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
        "x" : 460,
        "y" : 930
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 397
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 385
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 385
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "complete" : true,
      "onAny" : true
    } ]
  } ],
  "implicitMergeGroups" : [ {
    "mergePoints" : [ {
      "type" : "SUBGRAPH",
      "className" : "SubgraphPayload",
      "id" : 0
    }, {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 2
    } ],
    "includesStartPoint" : false
  } ],
  "serializationPointSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 80
  },
  "coordinatesSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 386
  },
  "buildGraphSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 399
  }
}