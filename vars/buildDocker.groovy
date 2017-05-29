def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        sh "docker build -t ${config.name}:${config.tag} ."
        sh "docker push ${config.name}:${config.tag}"
    }
}