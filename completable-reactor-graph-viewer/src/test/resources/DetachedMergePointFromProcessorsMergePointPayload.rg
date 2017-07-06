{
  "version" : "v1_0_21",
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
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 690
    },
    "builderPayloadSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 675
    },
    "processingItems" : [ {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "name" : "idProcessor0"
    }, {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "name" : "idProcessor1"
    } ]
  },
  "processors" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "name" : "idProcessor0"
    },
    "coordinates" : {
      "x" : 164,
      "y" : 131
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 691
    },
    "processorDoc" : [ "IdProcessor return it's id" ],
    "handlerTitle" : "handle",
    "handlerDoc" : [ "Return id of this processor.", "Id is Long." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 65
    }
  }, {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "name" : "idProcessor1"
    },
    "coordinates" : {
      "x" : 330,
      "y" : 127
    },
    "coordinatesSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 692
    },
    "processorDoc" : [ "IdProcessor return it's id" ],
    "handlerTitle" : "handle",
    "handlerDoc" : [ "Return id of this processor.", "Id is Long." ],
    "withHandlerSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 65
    }
  } ],
  "subgraphs" : [ ],
  "mergePoints" : [ {
    "identity" : {
      "type" : "PROCESSOR",
      "className" : "IdProcessor",
      "name" : "idProcessor0"
    },
    "coordinates" : {
      "x" : 235,
      "y" : 224
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 66
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
        "name" : "idProcessor1"
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
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 681
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
      "name" : "idProcessor1"
    },
    "coordinates" : {
      "x" : 357,
      "y" : 241
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 66
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
        "name" : "mergePoint"
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
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 684
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
      "name" : "mergePoint"
    },
    "coordinates" : {
      "x" : 461,
      "y" : 289
    },
    "coordinatesSource" : null,
    "mergeSource" : {
      "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
      "fileName" : "CompletableReactorTest.java",
      "fileNameLine" : 662
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
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 696
      },
      "completeSource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 687
      },
      "transitionsDoc" : [ ],
      "transitionOnAnySource" : {
        "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
        "fileName" : "CompletableReactorTest.java",
        "fileNameLine" : 687
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
    "fileNameLine" : 74
  },
  "coordinatesSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 689
  },
  "buildGraphSource" : {
    "className" : "ru.fix.completable.reactor.runtime.tests.CompletableReactorTest$9Config",
    "fileName" : "CompletableReactorTest.java",
    "fileNameLine" : 698
  }
}