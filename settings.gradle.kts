rootProject.name = "completable-reactor"
for (project in listOf(
        "completable-reactor-example",
        "completable-reactor-graph",
        "completable-reactor-graph-kotlin",
        "completable-reactor-graph-viewer",
        "completable-reactor-graph-viewer-app",
        "completable-reactor-model",
        "completable-reactor-parser",
        "completable-reactor-runtime",
        "completable-reactor-spring",
        "completable-reactor-test-utils")) {
    include(project)
}