var _data = [{
    "payload": {"payloadClass": "PurchasePayload", "payloadDoc": ["Represent purchase request"]},
    "processors": {
        "BankProcessor@0": {
            "coordinates": "410.440",
            "processorDoc": ["Withdraw money from Bank account ", "for given user."],
            "requestDoc": ["Withdraw money from account and print it to stdout", "If there is no money on account then withdrawing would be applied anyway", "and account balance became negative"],
            "handlerName": "withdrawMoneyWithMinus",
            "isCopyHandler": true,
        },
        "NotificationProcessor@0": {
            "coordinates": "880.430",
            "processorDoc": ["Send notifications to external system", "Notification about events like purchase.", "We do not need to wait result of notification action for building correct response.", "So we attach notification completable future to async task manager and continue without waiting for its result"],
            "requestDoc": ["Send notification about purchase event"],
            "handlerName": "sendPurchaseNotification",
            "isCopyHandler": true,
        },
        "ServiceInfoProcessor@0": {
            "coordinates": "480.120",
            "processorDoc": ["Service information processor load data for given service from database.", "Service could be not active. In that case purchase request will be denied."],
            "requestDoc": ["Load service information from database."],
            "handlerName": "loadServiceInformation",
            "isCopyHandler": true,
        },
        "TransactionLogProcessor@0": {
            "coordinates": "420.650",
            "processorDoc": ["Описание процессора №4", "Тоже многострочное", "Тоже довольно подробное", "которое выглядит, например, как tooltip"],
            "requestDoc": null,
            "handlerName": "logTransactioin",
            "isCopyHandler": true,
        },
        "UserLogProcessor@0": {
            "coordinates": "680.820",
            "processorDoc": ["Описание процессора №5", "Тоже многострочное", "Тоже довольно подробное", "которое выглядит, например, как tooltip"],
            "requestDoc": null,
            "handlerName": "logAction",
            "isCopyHandler": false,
        },
        "UserProfileProcessor@0": {
            "coordinates": "770.120",
            "processorDoc": ["UserProfileProcessor load information about user", "from remote database.", "User profile contains information whether user is blocked or not."],
            "requestDoc": ["Load user profile from database by user id  - lambda."],
            "handlerName": "loadUserProfileById",
            "isCopyHandler": true,
        }
    },
    "startPoint": {"coordinates": "680.60", "processors": ["ServiceInfoProcessor@0", "UserProfileProcessor@0"]},
    "mergeGroups": [{
        "mergePoints": [{
            "processor": "UserProfileProcessor@0",
            "coordinates": "830.250",
            "transitions": [{
                "mergeStatuses": ["STOP"],
                "isOnAny": false,
                "isComplete": true,
                "mergeProcessor": null,
                "handleByProcessor": null,
                "completeCoordinates": "920.300"
            }, {
                "mergeStatuses": ["CONTINUE"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": "ServiceInfoProcessor@0",
                "handleByProcessor": null,
                "completeCoordinates": "100.100"
            }]
        }, {
            "processor": "ServiceInfoProcessor@0",
            "coordinates": "640.280",
            "transitions": [{
                "mergeStatuses": ["WITHDRAWAL"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "BankProcessor@0",
                "completeCoordinates": "100.100"
            }, {
                "mergeStatuses": ["NO_WITHDRAWAL"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "UserLogProcessor@0",
                "completeCoordinates": "100.100"
            }, {
                "mergeStatuses": ["NO_WITHDRAWAL"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "NotificationProcessor@0",
                "completeCoordinates": "100.100"
            }, {
                "mergeStatuses": ["STOP"],
                "isOnAny": false,
                "isComplete": true,
                "mergeProcessor": null,
                "handleByProcessor": null,
                "completeCoordinates": "480.310"
            }]
        }]
    }, {
        "mergePoints": [{
            "processor": "BankProcessor@0",
            "coordinates": "480.550",
            "transitions": [{
                "mergeStatuses": [],
                "isOnAny": true,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "TransactionLogProcessor@0",
                "completeCoordinates": "100.100"
            }]
        }]
    }, {
        "mergePoints": [{
            "processor": "TransactionLogProcessor@0",
            "coordinates": "530.770",
            "transitions": [{
                "mergeStatuses": [],
                "isOnAny": true,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "UserLogProcessor@0",
                "completeCoordinates": "100.100"
            }]
        }]
    }, {
        "mergePoints": [{
            "processor": "UserLogProcessor@0",
            "coordinates": "760.930",
            "transitions": [{
                "mergeStatuses": [],
                "isOnAny": true,
                "isComplete": true,
                "mergeProcessor": null,
                "handleByProcessor": null,
                "completeCoordinates": "740.1020"
            }]
        }]
    }]
}, {
    "payload": {"payloadClass": "SubscribePayload", "payloadDoc": null},
    "processors": {
        "BankProcessor@0": {
            "coordinates": "410.440",
            "processorDoc": ["Withdraw money from Bank account ", "for given user."],
            "requestDoc": ["Withdraw money from account and print it to stdout", "If there is no money on account no withdrawing would be applied", "so account balance will always stay positive."],
            "handlerName": "withdrawMoney"
        },
        "NotificationProcessor@0": {
            "coordinates": "880.430",
            "processorDoc": ["Send notifications to external system", "Notification about events like purchase.", "We do not need to wait result of notification action for building correct response.", "So we attach notification completable future to async task manager and continue without waiting for its result"],
            "requestDoc": ["Send notification about purchase event"],
            "handlerName": "sendPurchaseNotification"
        },
        "ServiceInfoProcessor@0": {
            "coordinates": "480.120",
            "processorDoc": ["Service information processor load data for given service from database.", "Service could be not active. In that case purchase request will be denied."],
            "requestDoc": ["Load service information from database."],
            "handlerName": "loadServiceInformation"
        },
        "TransactionLogProcessor@0": {
            "coordinates": "420.650",
            "processorDoc": ["Описание процессора №4", "Тоже многострочное", "Тоже довольно подробное", "которое выглядит, например, как tooltip"],
            "requestDoc": null,
            "handlerName": "logTransactioin"
        },
        "UserLogProcessor@0": {
            "coordinates": "680.820",
            "processorDoc": ["Описание процессора №5", "Тоже многострочное", "Тоже довольно подробное", "которое выглядит, например, как tooltip"],
            "requestDoc": null,
            "handlerName": "logAction"
        },
        "UserProfileProcessor@0": {
            "coordinates": "770.120",
            "processorDoc": ["UserProfileProcessor load information about user", "from remote database.", "User profile contains information whether user is blocked or not."],
            "requestDoc": ["Load user profile from database by user id  - lambda."],
            "handlerName": "loadUserProfileById"
        }
    },
    "startPoint": {"coordinates": "680.60", "processors": ["ServiceInfoProcessor@0", "UserProfileProcessor@0"]},
    "mergeGroups": [{
        "mergePoints": [{
            "processor": "UserProfileProcessor@0",
            "coordinates": "830.250",
            "transitions": [{
                "mergeStatuses": ["STOP"],
                "isOnAny": false,
                "isComplete": true,
                "mergeProcessor": null,
                "handleByProcessor": null,
                "completeCoordinates": "920.300"
            }, {
                "mergeStatuses": ["CONTINUE"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": "ServiceInfoProcessor@0",
                "handleByProcessor": null,
                "completeCoordinates": "100.100"
            }]
        }, {
            "processor": "ServiceInfoProcessor@0",
            "coordinates": "640.280",
            "transitions": [{
                "mergeStatuses": ["WITHDRAWAL"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "BankProcessor@0",
                "completeCoordinates": "100.100"
            }, {
                "mergeStatuses": ["NO_WITHDRAWAL"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "UserLogProcessor@0",
                "completeCoordinates": "100.100"
            }, {
                "mergeStatuses": ["NO_WITHDRAWAL"],
                "isOnAny": false,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "NotificationProcessor@0",
                "completeCoordinates": "100.100"
            }, {
                "mergeStatuses": ["STOP"],
                "isOnAny": false,
                "isComplete": true,
                "mergeProcessor": null,
                "handleByProcessor": null,
                "completeCoordinates": "480.310"
            }]
        }]
    }, {
        "mergePoints": [{
            "processor": "BankProcessor@0",
            "coordinates": "480.550",
            "transitions": [{
                "mergeStatuses": [],
                "isOnAny": true,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "TransactionLogProcessor@0",
                "completeCoordinates": "100.100"
            }]
        }]
    }, {
        "mergePoints": [{
            "processor": "TransactionLogProcessor@0",
            "coordinates": "530.770",
            "transitions": [{
                "mergeStatuses": [],
                "isOnAny": true,
                "isComplete": false,
                "mergeProcessor": null,
                "handleByProcessor": "UserLogProcessor@0",
                "completeCoordinates": "100.100"
            }]
        }]
    }, {
        "mergePoints": [{
            "processor": "UserLogProcessor@0",
            "coordinates": "760.930",
            "transitions": [{
                "mergeStatuses": [],
                "isOnAny": true,
                "isComplete": true,
                "mergeProcessor": null,
                "handleByProcessor": null,
                "completeCoordinates": "740.1020"
            }]
        }]
    }]
},

    {
        "payload": {
            "payloadClass": "UserInfoPayload",
            "payloadDoc": ["Получение информации по msisdn.", "Данные получаются из xMNP-ml, HBase и PostgreSQL.", "Если никаких сведений не найдено, то вернется пустой результат."]
        },
        "processors": {
            "HBaseClient@0": {
                "processorType": "PLAIN",
                "coordinates": "560.690",
                "processorDoc": ["Процессор для взаимодействия с HBase"],
                "requestDoc": ["Сохранение сущности в HBase"],
                "handlerName": "put"
            },
            "MsisdnInfoActualizer@0": {
                "processorType": "PLAIN",
                "coordinates": "390.130",
                "processorDoc": ["Сервис для получения и актуализации сведений по msisdn, лежащих в HBase."],
                "requestDoc": ["Получить из Hbase данные по msisdn. Реультат так же содержит сведения об устаревши AoC, запретах и т.п."],
                "handlerName": "getActualMsisdnInfo"
            },
            "MsisdnInfoActualizer@1": {
                "processorType": "PLAIN",
                "coordinates": "230.690",
                "processorDoc": ["Сервис для получения и актуализации сведений по msisdn, лежащих в HBase."],
                "requestDoc": ["Акутализировать данные по msisdn, хранящиеся в HBase. Актуализация подразумевает под собой", "удаление устаревших данных. Например, просроченные AoC, устаревшие запреты и т.п."],
                "handlerName": "actualizeMsisdnInfo"
            },
            "UserProfileProcessor@0": {
                "processorType": "PLAIN",
                "coordinates": "80.350",
                "processorDoc": ["Процессор для получения данных по msisdn из PostgreSQL."],
                "requestDoc": ["Получить данные по msisdn из PostgreSQL, из таблицы operator_number_range."],
                "handlerName": "loadMsisdnInfoFromOperatorNumberRange"
            },
            "UserProfileProcessor@1": {
                "processorType": "PLAIN",
                "coordinates": "380.350",
                "processorDoc": ["Процессор для получения данных по msisdn из PostgreSQL."],
                "requestDoc": ["Получить из PostgreSQL, из таблицы subsystem_mapping, regionCode по filialId.", "и обновить поле billingCodeRegion объекта MsisdnInfo"],
                "handlerName": "loadBillingCode"
            },
            "XMNPMLProcessor@0": {
                "processorType": "PLAIN",
                "coordinates": "90.120",
                "processorDoc": ["Процессор, работающий с сервисом xMNP-ML.", "Нужен для получения и акутализации сведений о принадлежности msisdn ", "к конкретному оператору, филиалу Мегафона, субъекту РФ."],
                "requestDoc": ["Получить данные по msisdn через xMNP-ML. В случае ошибки сервиса, вернет null."],
                "handlerName": "getMsisdnInfo"
            }
        },
        "startPoint": {"coordinates": "290.20", "processors": ["MsisdnInfoActualizer@0", "XMNPMLProcessor@0"]},
        "mergeGroups": [{
            "mergePoints": [{
                "processor": "MsisdnInfoActualizer@0",
                "coordinates": "430.250",
                "transitions": [{
                    "mergeStatuses": [],
                    "isOnAny": true,
                    "isComplete": false,
                    "mergeProcessor": "XMNPMLProcessor@0",
                    "handleByProcessor": null,
                    "completeCoordinates": "100.100",
                    "transitionsDoc": []
                }]
            }, {
                "processor": "XMNPMLProcessor@0",
                "coordinates": "300.260",
                "transitions": [{
                    "mergeStatuses": ["FOUND"],
                    "isOnAny": false,
                    "isComplete": false,
                    "mergeProcessor": null,
                    "handleByProcessor": "UserProfileProcessor@1",
                    "completeCoordinates": "100.100",
                    "transitionsDoc": [{"mergeStatus": "FOUND", "docs": ["Информация по msisdn найдена"]}]
                }, {
                    "mergeStatuses": ["NOT_FOUND"],
                    "isOnAny": false,
                    "isComplete": false,
                    "mergeProcessor": null,
                    "handleByProcessor": "UserProfileProcessor@0",
                    "completeCoordinates": "100.100",
                    "transitionsDoc": [{"mergeStatus": "NOT_FOUND", "docs": ["Информация по msisdn не найдена"]}]
                }]
            }]
        }, {
            "mergePoints": [{
                "processor": "UserProfileProcessor@0",
                "coordinates": "160.460",
                "transitions": [{
                    "mergeStatuses": [],
                    "isOnAny": true,
                    "isComplete": true,
                    "mergeProcessor": null,
                    "handleByProcessor": null,
                    "completeCoordinates": "130.570",
                    "transitionsDoc": []
                }]
            }]
        }, {
            "mergePoints": [{
                "processor": "UserProfileProcessor@1",
                "coordinates": "450.470",
                "transitions": [{
                    "mergeStatuses": ["UPDATE", "UPDATE_ACTUALIZE"],
                    "isOnAny": false,
                    "isComplete": false,
                    "mergeProcessor": null,
                    "handleByProcessor": "HBaseClient@0",
                    "completeCoordinates": "100.100",
                    "transitionsDoc": [{
                        "mergeStatus": "UPDATE",
                        "docs": ["Необходимо обновить в HBase базовые данные профиля"]
                    }, {
                        "mergeStatus": "UPDATE_ACTUALIZE",
                        "docs": ["Необходимо обновить в HBase базовые данные профиля и", "удалить устаревшие данные (старые AoC, запреты и т.п.)"]
                    }]
                }, {
                    "mergeStatuses": ["ACTUALIZE", "UPDATE_ACTUALIZE"],
                    "isOnAny": false,
                    "isComplete": false,
                    "mergeProcessor": null,
                    "handleByProcessor": "MsisdnInfoActualizer@1",
                    "completeCoordinates": "100.100",
                    "transitionsDoc": [{
                        "mergeStatus": "ACTUALIZE",
                        "docs": ["Необходимо удалить в HBase устаревшие данные (старые AoC, запреты и т.п.)"]
                    }, {
                        "mergeStatus": "UPDATE_ACTUALIZE",
                        "docs": ["Необходимо обновить в HBase базовые данные профиля и", "удалить устаревшие данные (старые AoC, запреты и т.п.)"]
                    }]
                }, {
                    "mergeStatuses": ["NO_ACTION"],
                    "isOnAny": false,
                    "isComplete": true,
                    "mergeProcessor": null,
                    "handleByProcessor": null,
                    "completeCoordinates": "660.500",
                    "transitionsDoc": [{"mergeStatus": "NO_ACTION", "docs": ["Обновление профиля не требуется"]}]
                }]
            }]
        }, {
            "mergePoints": [{
                "processor": "HBaseClient@0",
                "coordinates": "590.820",
                "transitions": [{
                    "mergeStatuses": ["UPDATE"],
                    "isOnAny": false,
                    "isComplete": true,
                    "mergeProcessor": null,
                    "handleByProcessor": null,
                    "completeCoordinates": "590.970",
                    "transitionsDoc": [{
                        "mergeStatus": "UPDATE",
                        "docs": ["Необходимо обновить в HBase базовые данные профиля"]
                    }]
                }, {
                    "mergeStatuses": ["UPDATE_ACTUALIZE"],
                    "isOnAny": false,
                    "isComplete": false,
                    "mergeProcessor": "MsisdnInfoActualizer@1",
                    "handleByProcessor": null,
                    "completeCoordinates": "100.100",
                    "transitionsDoc": [{
                        "mergeStatus": "UPDATE_ACTUALIZE",
                        "docs": ["Необходимо обновить в HBase базовые данные профиля и", "удалить устаревшие данные (старые AoC, запреты и т.п.)"]
                    }]
                }]
            }, {
                "processor": "MsisdnInfoActualizer@1",
                "coordinates": "370.850",
                "transitions": [{
                    "mergeStatuses": [],
                    "isOnAny": true,
                    "isComplete": true,
                    "mergeProcessor": null,
                    "handleByProcessor": null,
                    "completeCoordinates": "370.970",
                    "transitionsDoc": []
                }]
            }]
        }]
    }

]