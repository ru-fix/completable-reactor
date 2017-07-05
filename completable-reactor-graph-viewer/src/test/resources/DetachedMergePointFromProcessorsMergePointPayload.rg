{
  "version" : "v1_0_20",
  "payload" : {
    "payloadName" : "DetachedMergePointFromProcessorsMergePointPayload",
    "payloadClass" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$DetachedMergePointFromProcessorsMergePointPayload",
    "payloadDoc" : [ "Detached merge point works as an regular merge point ", "but there is no processor or subgraph or theirs result to merge.", "Merge point simply modify payload and send it through outgoing transitions.", "Expected result: {0, 1, 42}", "extends IdListPayload", "IdListPayload contains list of integer ids.", " When IdListPayload goes through processors chain each processor adds their id", " so at the end we can clarify by witch processor and in what order this payload was processed." ]
  },
  "startPoint" : {
    "coordinates" : {
      "x" : 95,
      "y" : 62
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 627
    },
    "builderPayloadSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 612
    },
    "processingItems" : [ {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 0
    }, {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 1
    } ]
  },
  "processors" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 0
    },
    "coordinates" : {
      "x" : 164,
      "y" : 131
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 628
    },
    "processorDoc" : [ "IdProcessor return it's id" ],
    "handlerTitle" : "handle",
    "handlerDoc" : [ "Return id of this processor.", "Id is Long." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 70
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 1
    },
    "coordinates" : {
      "x" : 330,
      "y" : 127
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 629
    },
    "processorDoc" : [ "IdProcessor return it's id" ],
    "handlerTitle" : "handle",
    "handlerDoc" : [ "Return id of this processor.", "Id is Long." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 70
    }
  } ],
  "subgraphs" : [ ],
  "mergePoints" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 0
    },
    "coordinates" : {
      "x" : 235,
      "y" : 224
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 71
    },
    "mergerTitle" : null,
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : false,
      "mergeProcessingItem" : {
        "type" : "PROCESSOR",
        "className" : "IdProcessor",
        "id" : 1
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
        "fileNameLine" : 618
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "onAny" : true,
      "complete" : false
    } ]
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "id" : 1
    },
    "coordinates" : {
      "x" : 357,
      "y" : 241
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 71
    },
    "mergerTitle" : null,
    "mergerDocs" : [ ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
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
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 621
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "onAny" : true,
      "complete" : false
    } ]
  }, {
    "identity" : {
      "type" : "MERGE_POINT",
      "className" : null,
      "id" : 0
    },
    "coordinates" : {
      "x" : 461,
      "y" : 289
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 599
    },
    "mergerTitle" : "addMergePointId",
    "mergerDocs" : [ "Adds merge point id", "to payload sequence" ],
    "transitions" : [ {
      "mergeStatuses" : [ ],
      "isOnAny" : true,
      "isComplete" : true,
      "mergeProcessingItem" : null,
      "handleByProcessingItem" : null,
      "completeCoordinates" : {
        "x" : 406,
        "y" : 369
      },
      "completeCoordinatesSource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 633
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 624
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 624
      },
      "transitionOnStatusSource" : { },
      "mergeStatusSources" : { },
      "onAny" : true,
      "complete" : true
    } ]
  } ],
  "serializationPointSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 79
  },
  "coordinatesSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 626
  },
  "buildGraphSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 635
  }
}