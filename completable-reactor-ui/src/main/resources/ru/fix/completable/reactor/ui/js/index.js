$(function () {

    let ZOOM = 1;

    _data.forEach((payload) => {
        payload.mergePoints = payload.mergeGroups.reduce((mergePoints, mergeGroup) => {
            return mergePoints.concat(mergeGroup.mergePoints);
        }, []);
    });

    // перести название процессора в массив переходов
    _data.forEach((payload) => {
        payload.mergePoints.forEach((mergePoint) => {
            mergePoint.transitions.forEach((transition) => {
                transition.processor = mergePoint.processor;
            });
        });
    });

    // клонировать переход для каждого handleByProcessor или mergeProcessor
    // для того чтобы из transition.mergeProcessors => [ transition.mergeProcessor, transition.mergeProcessor ]
    _data.forEach((payload) => {
        payload.mergePoints.forEach((mergePoint) => {
            mergePoint.transitions.forEach((transition) => {
                if (transition.handleByProcessors && transition.mergeProcessors) {
                    return alert('Error: transition.handleByProcessors && transition.mergeProcessors');
                }
                if (transition.handleByProcessors) {
                    if (transition.handleByProcessors.length > 1) {
                        transition.handleByProcessors.forEach((handleByProcessor, i) => {
                            if (i === 0) {
                                transition.handleByProcessor = handleByProcessor;
                            } else {
                                let newTransition = $.extend(true, {}, transition);
                                newTransition.handleByProcessor = handleByProcessor;
                                delete newTransition.handleByProcessors;
                                mergePoint.transitions.push(newTransition);
                            }
                        });
                    } else {
                        transition.handleByProcessor = transition.handleByProcessors[0];
                    }
                    delete transition.handleByProcessors;
                }
                if (transition.mergeProcessors) {
                    if (transition.mergeProcessors.length > 1) {
                        transition.mergeProcessors.forEach((mergeProcessor, i) => {
                            if (i === 0) {
                                transition.mergeProcessor = mergeProcessor;
                            } else {
                                let newTransition = $.extend(true, {}, transition);
                                newTransition.mergeProcessor = mergeProcessor;
                                delete newTransition.mergeProcessor;
                                mergePoint.transitions.push(newTransition);
                            }
                        });
                    } else {
                        transition.mergeProcessor = transition.mergeProcessors[0];
                    }
                    delete transition.mergeProcessors;
                }
            });
        });
    });


    _data.forEach((payload, i) => {

        let dataSource = Object.keys(payload.processors).map((processor) => {
            return {
                id: processor,
                name: processor,
                handlerName: payload.processors[processor].handlerName || '',
                _type: 'PROCESSOR',
                x: payload.processors[processor].coordinates.x * ZOOM,
                y: payload.processors[processor].coordinates.y * ZOOM,
            };
        }).concat(
            payload.mergePoints.map((mergePoint) => {
                return {
                    id: `FROM.${ mergePoint.processor }`,
                    name: '',
                    width: 50,
                    height: 50,
                    type: 'circle',
                    _type: 'MERGE',
                    x: mergePoint.coordinates.x * ZOOM,
                    y: mergePoint.coordinates.y * ZOOM,
                };
            })
        ).concat([
            {
                id: 'START',
                name: payload.payload.payloadName,
                _type: 'EDGE',
                x: payload.startPoint.coordinates.x * ZOOM,
                y: payload.startPoint.coordinates.y * ZOOM,
            }
        ]).concat(
            payload.mergePoints.reduce((transitionsToEnd, mergePoint) => {
                return transitionsToEnd.concat(
                    mergePoint.transitions.filter((transition) => {
                        return transition.isComplete;
                    }).map((transitionToEnd) => {
                        transitionToEnd.from = `FROM.${ mergePoint.processor }`;
                        return transitionToEnd;
                    })
                );
            }, []).map((transitionToEnd) => {
                return {
                    id: `END.${ transitionToEnd.from }`,
                    name: 'END',
                    _type: 'EDGE',
                    x: transitionToEnd.completeCoordinates.x * ZOOM,
                    y: transitionToEnd.completeCoordinates.y * ZOOM
                };
            })
        ).map((edge) => {
            edge.width = 100;
            edge.height = 40;
            edge.type = 'circle';
            return edge;
        });

        let connectionsDataSource = payload.startPoint.processors.map((processor) => {
            return {from: 'START', to: processor, label: ''};
        }).concat(
            payload.mergePoints.map((mergePoint) => {
                return {from: mergePoint.processor, to: `FROM.${ mergePoint.processor }`, label: ''}
            })
        ).concat(
            payload.mergePoints.reduce((transitions, mergePoint) => {
                return transitions.concat(mergePoint.transitions);
            }, []).map((transition) => {

                var transitionLabel = '';
                if (transition.isOnAny) {
                    transitionLabel = '&lt;Any&gt;';
                } else {
                    if(transition.mergeStatuses && transition.mergeStatuses.length > 0){
                        for (var i = 0; i < transition.mergeStatuses.length; i++) {
                            transitionLabel += ',' + transition.mergeStatuses[i];
                        }
                        transitionLabel = transitionLabel.substring(1);
                    }
                }

                if (transition.isComplete) {
                    return {
                        from: `FROM.${ transition.processor }`,
                        to: `END.FROM.${ transition.processor }`,
                        label: transitionLabel,
                        docs : transition.transitionsDoc
                    }
                } else {
                    if (transition.handleByProcessor) {
                        return {
                            from: `FROM.${ transition.processor }`,
                            to: transition.handleByProcessor,
                            label: transitionLabel,
                            docs : transition.transitionsDoc,
                            isCopyHandler: payload.processors[transition.handleByProcessor].isCopyHandler
                        }
                    }
                    if (transition.mergeProcessor) {
                        return {
                            from: `FROM.${ transition.processor }`,
                            to: `FROM.${ transition.mergeProcessor }`,
                            label: transitionLabel,
                            docs : transition.transitionsDoc
                        }
                    }
                }
            })
        );

        let isActiveClass = i === 0 ? 'active' : '';

        $('.nav.nav-tabs').append('<li role="presentation" class="' + isActiveClass + '"><a href="#tab' + i + '" aria-controls="home" role="tab" data-toggle="tab">' + payload.payload.payloadName + '</a></li>');
        $('.tab-content').append('<div role="tabpanel" class="tab-pane ' + isActiveClass + '" id="tab' + i + '"><div class="diagram" id="diagram' + i + '"></div><div class="docs"></div></div>');

        $("#diagram" + i).kendoDiagram({
            dataSource,
            connectionsDataSource,
            shapeDefaults: {
                content: { template: "#= name #" },
                fill: '#ddd',
                hover: { fill: "#ccc"  },
                visual: (options) => {
                    var group = new kendo.dataviz.diagram.Group({ autoSize: true });
                    if (options.dataItem) {
                        switch (options.dataItem._type) {
                            case 'PROCESSOR':
                                var shape = new kendo.dataviz.diagram.Rectangle({
                                    width: 200,
                                    height: 70,
                                    fill: '#ddd',
                                    hover: { fill: "#ccc" }
                                });
                                break;
                            case 'MERGE':
                            case 'EDGE':
                                var shape = new kendo.dataviz.diagram.Circle({
                                    width: 50,
                                    height: 50,
                                    fill: '#ddd',
                                    hover: { fill: "#ccc" }
                                });
                                break;
                        }
                        group.append(shape);
                        if (options.dataItem._type === 'PROCESSOR' && options.dataItem.handlerName) {
                            var layout = new kendo.dataviz.diagram.Layout(new kendo.dataviz.diagram.Rect(0, 20, 200, 70), {
                                alignContent: "center",
                                alignItems: "center",
                                justifyContent: "center",
                                orientation: "vertical"
                            });
                            group.append(layout);
                            var text = new kendo.dataviz.diagram.TextBlock({
                                text: '.' + options.dataItem.handlerName + '()'
                            });
                            layout.append(text);
                            layout.reflow();
                        }
                    } else {
                        if (options._type === 'MERGE_GROUP') {
                            let { height, width } = options;
                            let config = $.extend({}, { height, width }, {
                                // fill: '#ddd',
                                // hover: { fill: "#ccc" }
                            });
                            let shape = new kendo.dataviz.diagram.Rectangle(config);
                            group.append(shape);
                        }
                    }
                    return group;
                }
            },
            connectionDefaults: {
                stroke: {
                    color: "#979797",
                    width: 3
                },
                type: "polyline",
                startCap: "FilledCircle",
                endCap: "ArrowEnd",
                content: {
                    template: "#= label#"
                }
            },
            autoBind: true,

            click: (clickEvent) => {
                if (clickEvent.item instanceof kendo.dataviz.diagram.Connection) {

                    if(clickEvent.item.lastClickTimestamp) {
                        if(Date.now() - clickEvent.item.lastClickTimestamp < 300){
                            var target = clickEvent.item.to;
                            if(target) {
                                target.position(clickEvent.point);
                            }
                        }
                    }
                    clickEvent.item.lastClickTimestamp = Date.now();
                    return clickEvent.preventDefault();
                }
            },

            mouseEnter: (e) => {
                if (!e.item.dataItem) { return; }
                if (e.item instanceof kendo.dataviz.diagram.Connection) {
                    if(e.item.dataItem) {
                        if(e.item.dataItem.docs){
                            var docs = e.item.dataItem.docs;

                            var transitionDescriptionHtml = '';

                            docs.forEach((doc) => {
                                transitionDescriptionHtml += '' + doc.mergeStatus + '<br />';

                                if(doc.docs) {
                                    doc.docs.forEach((line) => {
                                        transitionDescriptionHtml += '' + line + '<br />';
                                    });
                                }

                                transitionDescriptionHtml += '<br />';
                        });

                        }
                    }

                    $('#tab' + i + ' .docs').html(transitionDescriptionHtml);
                    return e.preventDefault();
                }

                let html = '[' + e.item.dataItem.x + '.' + e.item.dataItem.y + ']<br />';

                if (e.item.dataItem._type === "PROCESSOR") {

                    var processorInfo = payload.processors[e.item.dataItem.name];

                    html += processorInfo.handlerName;
                    html += '<br />';
                    html += '* * * * *';
                    html += '<br />';
                    html += processorInfo.processorDoc ? processorInfo.processorDoc.join('<br />') : '<br />';
                    html += '<br />';
                    html += '* * * * *';
                    html += '<br />';
                    html += processorInfo.requestDoc ? processorInfo.requestDoc.join('<br />') : '';
                }

                if (e.item.dataItem.id === "START") {

                    html += payload.payload.payloadDoc ? payload.payload.payloadDoc.join('<br />') : '<br />';
                    html += '<br />';

                    html += '* * * * *';
                    html += '<br />';

                    html += $("#diagram" + i).getKendoDiagram().dataSource.view().reduce((graphPoints, shape) => {

                        switch (shape._type) {
                            case 'PROCESSOR':
                                graphPoints += `.proc("${ shape.id }", ${ shape.x }, ${ shape.y })<br />`;
                                break;
                            case 'MERGE':
                                graphPoints += `.merge("${ shape.id.substr(5) }", ${ shape.x }, ${ shape.y })<br />`;
                                break;
                            case 'EDGE':
                                graphPoints += shape.id === 'START'
                                    ? `.start(${ shape.x }, ${ shape.y })<br />`
                                    : `.complete("${ shape.id.substr(9) }", ${ shape.x }, ${ shape.y })<br />`;
                                break;
                        }

                        return graphPoints;

                    }, '');

                }

                $('#tab' + i + ' .docs').html(html);
            }
            // editable: false,
        }).data('kendoDiagram').zoom(ZOOM);

        $("#diagram" + i).getKendoDiagram().connections.map((connection) => {
            if (connection.dataItem.isCopyHandler) {
                connection.redraw({ stroke: { color: 'brown' } });
            }
        });

        payload.mergeGroups.filter((mergeGroup) => {
            return mergeGroup.mergePoints.length > 1;
        }).map((mergeGroup) => {
            // // let shape = new kendo.dataviz.diagram.Point(100,220);
            // shape._type = 'MERGE_GROUP';
            let coordinates = mergeGroup.mergePoints.map((mergePoint) => { return [mergePoint.coordinates.x, mergePoint.coordinates.y]; });
            let xList = coordinates.map((cordinate) => { return cordinate[0]; });
            let yList = coordinates.map((cordinate) => { return cordinate[1]; });
            let xMin = Math.min.apply(this, xList);
            let xMax = Math.max.apply(this, xList);
            let yMin = Math.min.apply(this, yList);
            let yMax = Math.max.apply(this, yList);
            let padding = 10;
            let mergePointLength = 50;
            let shapeConfig = {
                _type: 'MERGE_GROUP',
                x: xMin * ZOOM - padding,
                y: yMin * ZOOM - padding,
                width: (xMax - xMin) * ZOOM + mergePointLength + padding * 2,
                height: (yMax - yMin) * ZOOM + mergePointLength + padding * 2
            };
            // debugger;
            $("#diagram" + i).getKendoDiagram().addShape(shapeConfig);
        });

    });

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        kendo.resize($("body"));
    });


});