def call(String command= '') {
    ansiColor('xterm') {
        sh command
    }
}